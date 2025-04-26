package ac.liverpool.forum.service.impl;

import ac.liverpool.forum.entity.dto.UserRegisterDTO;
import ac.liverpool.forum.entity.LoginInfo;
import ac.liverpool.forum.entity.Posts;
import ac.liverpool.forum.entity.VO.Result;
import ac.liverpool.forum.entity.Users;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.mapper.UsersMapper;
import ac.liverpool.forum.service.EmailService;
import ac.liverpool.forum.service.TokenBlacklistService;
import ac.liverpool.forum.service.UsersService;
import ac.liverpool.forum.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Slf4j
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private TokenBlacklistService tokenBlacklistService;
  @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public LoginInfo login(Users emp) {
        Users usersLogin = usersMapper.getUsernameAndPassword(emp);
        if (usersLogin != null) {
            // 1. generate JWT token
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", usersLogin.getUsername()); // add additional claims

            claims.put("id", usersLogin.getId());
            String jwt = JwtUtils.generateJwt(usersLogin.getId(), claims); // Passing userId and additional claims

            // 2. Return login information
            return new LoginInfo(Math.toIntExact(usersLogin.getId()), usersLogin.getUsername(), jwt);
        }
        return null;
    }
    @Override
    public Result logout(HttpServletRequest request) {
        // Retrieve token from request header
        String token = request.getHeader("token");

        if(StringUtils.hasLength(token)) {
            // Add the token to the blacklist
            tokenBlacklistService.addToBlacklist(token);
            log.info("User logged out, token added to blacklist");
        }

        return Result.success("Logout successful");
    }

    //Retrieve the current user ID and return it
    @Override
    public Result userInfo(Long userId) {
        // Query user information based on userId
        Users user = usersMapper.getUserById(userId);
        if (user == null) {
            return Result.error(AppHttpCodeEnum.valueOf("USER_NOT_EXIST"));
        }

        return Result.success(user);
    }

    @Override
    @Transactional
    public Result updateUserInfo(Users user) {
        // 1. Parameter verification
        if (user == null || user.getId() == null) {
            return Result.error(AppHttpCodeEnum.USER_ID_MISSING);
        }

        // 2. Check if the user exists
        Users existingUser = usersMapper.getUserById(user.getId());
        if (existingUser == null) {
            return Result.error(AppHttpCodeEnum.USER_NOT_EXIST);
        }

        // 3. Prepare to update data - only update fields that are allowed to be modified
        Users updateUser = new Users();
        updateUser.setId(user.getId());

        //  Special handling of usernames - if a new username is provided, uniqueness needs to be checked

            if (usersMapper.existsByUsername(user.getUsername(), user.getId())) {
                return Result.error(AppHttpCodeEnum.USERNAME_EXIST);
            }


        // 4. All fields are allowed to be null
        int updated = usersMapper.updateUserInfo(user);
        if (updated <= 0) {
            return Result.error(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        return Result.success("User information updated successfully");
    }

    @Override
    public Result getMyPosts(Long userId) {
        List<Posts> posts = usersMapper.getPostsByUserId(userId);
        if (posts == null || posts.isEmpty()) {
            return Result.error(AppHttpCodeEnum.NO_POSTS);
        }
        return Result.success(posts);
    }

    // Send verification code to email and create a temporary user record as part of the registration process
    @Override
    public void sendVerificationCode(String email) {
        // Check if the email has been registered
        Users existingUser = usersMapper.findByEmail(email);
        if (existingUser != null) {
            throw new RuntimeException("This email has already been registered");
        }

        // Generate a 6-digit random verification code
        String verificationCode = String.format("%06d", new Random().nextInt(1000000));

        //
        Users user = usersMapper.findByEmail(email);

        if (user == null) {
            // Create a new temporary user record
            user = new Users();
            user.setUsername(email); // use email as username temporarily
            user.setPassword(passwordEncoder.encode("tempPassword")); // temporary password
            user.setEmail(email);
            user.setVerificationCode(verificationCode);
            user.setVerificationCodeExpireTime(LocalDateTime.now().plusMinutes(5));
            user.setEmailVerified(false);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            usersMapper.insert(user);
        } else {
            // update the existing user record with a new verification code
            user.setVerificationCode(verificationCode);
            user.setVerificationCodeExpireTime(LocalDateTime.now().plusMinutes(5));
            user.setUpdateTime(LocalDateTime.now());
            usersMapper.updateVerificationCode(user);
        }

        //Send verification code email
        emailService.sendVerificationCode(email, verificationCode);
    }

    @Override
    @Transactional
    public void register(UserRegisterDTO registerDTO) {
        // Verify if the username already exists
        if (usersMapper.findByUsername(registerDTO.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }


        // Verify email verification code

        Users user = usersMapper.findByEmail(registerDTO.getEmail());

        if (user == null) {
            throw new RuntimeException("Please send the verification code first");
        }
        if ( !user.getVerificationCode().equals(registerDTO.getVerificationCode())) {
            throw new RuntimeException("Verification code is incorrect");
        }

        if (user.getVerificationCodeExpireTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification code has expired");
        }

        // Update user information
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());
        user.setEmail(registerDTO.getEmail());
        user.setEmailVerified(true);
        user.setUpdateTime(LocalDateTime.now());


        usersMapper.updateEmailVerified(user.getId());
        usersMapper.updateUserInfo(user);
    }

}
