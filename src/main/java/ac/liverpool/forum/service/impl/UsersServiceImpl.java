package ac.liverpool.forum.service.impl;

import ac.liverpool.forum.dto.UserRegisterDTO;
import ac.liverpool.forum.entity.LoginInfo;
import ac.liverpool.forum.entity.Posts;
import ac.liverpool.forum.entity.Result;
import ac.liverpool.forum.entity.Users;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.mapper.UsersMapper;
import ac.liverpool.forum.service.EmailService;
import ac.liverpool.forum.service.TokenBlacklistService;
import ac.liverpool.forum.service.UsersService;
import ac.liverpool.forum.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
            // 1. 生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", usersLogin.getUsername()); // 额外信息

            String jwt = JwtUtils.generateJwt(usersLogin.getId(), claims); // 传递 userId 和附加 claims

            // 2. 返回登录信息
            return new LoginInfo(Math.toIntExact(usersLogin.getId()), usersLogin.getUsername(), jwt);
        }
        return null;
    }
    @Override
    public Result logout(HttpServletRequest request) {
        // 从请求头获取token
        String token = request.getHeader("token");

        if(StringUtils.hasLength(token)) {
            // 将令牌加入黑名单
            tokenBlacklistService.addToBlacklist(token);
            log.info("用户登出，令牌已加入黑名单");
        }

        return Result.success("登出成功");
    }

    //获取当前userid并且返回
    @Override
    public Result userInfo(Long userId) {
        // 根据userId查询用户信息
        Users user = usersMapper.getUserById(userId);
        if (user == null) {
            return Result.error(AppHttpCodeEnum.valueOf("用户不存在"));
        }
        // 返回用户信息（注意敏感信息如密码不要返回）
        return Result.success(user);
    }

    @Override
    @Transactional
    public Result updateUserInfo(Users user) {
        // 1. 参数校验
        if (user == null || user.getId() == null) {
            return Result.error(AppHttpCodeEnum.USER_ID_MISSING);
        }

        // 2. 检查用户是否存在
        Users existingUser = usersMapper.getUserById(user.getId());
        if (existingUser == null) {
            return Result.error(AppHttpCodeEnum.USER_NOT_EXIST);
        }

        // 3. 准备更新数据 - 只更新允许修改的字段
        Users updateUser = new Users();
        updateUser.setId(user.getId());

        // 3. 用户名特殊处理 - 如果提供了新用户名需要检查唯一性

            if (usersMapper.existsByUsername(user.getUsername(), user.getId())) {
                return Result.error(AppHttpCodeEnum.USERNAME_EXIST);
            }


        // 4. 执行更新 - 所有字段都允许为null
        int updated = usersMapper.updateUserInfo(user);
        if (updated <= 0) {
            return Result.error(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        return Result.success("用户信息更新成功");
    }

    @Override
    public Result getMyPosts(Long userId) {
        List<Posts> posts = usersMapper.getPostsByUserId(userId);
        if (posts == null || posts.isEmpty()) {
            return Result.error(AppHttpCodeEnum.NO_POSTS);
        }
        return Result.success(posts);
    }

    //注册
    @Override
    public void sendVerificationCode(String email) {
        // 检查邮箱是否已被注册
        Users existingUser = usersMapper.findByEmail(email);
        if (existingUser != null) {
            throw new RuntimeException("该邮箱已被注册");
        }

        // 生成6位随机验证码
        String verificationCode = String.format("%06d", new Random().nextInt(1000000));

        // 查找是否已有临时用户记录
        Users user = usersMapper.findByEmail(email);

        if (user == null) {
            // 创建新的临时用户记录
            user = new Users();
            user.setUsername(email); // 使用邮箱作为用户名
            user.setPassword(passwordEncoder.encode("tempPassword")); // 临时密码
            user.setEmail(email);
            user.setVerificationCode(verificationCode);
            user.setVerificationCodeExpireTime(LocalDateTime.now().plusMinutes(5));
            user.setEmailVerified(false);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            usersMapper.insert(user);
        } else {
            // 更新现有记录的验证码
            user.setVerificationCode(verificationCode);
            user.setVerificationCodeExpireTime(LocalDateTime.now().plusMinutes(5));
            user.setUpdateTime(LocalDateTime.now());
            usersMapper.updateVerificationCode(user);
        }

        // 发送验证码邮件
        emailService.sendVerificationCode(email, verificationCode);
    }

    @Override
    @Transactional
    public void register(UserRegisterDTO registerDTO) {
        // 验证用户名是否已存在
        if (usersMapper.findByUsername(registerDTO.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }


        // 验证邮箱验证码

        Users user = usersMapper.findByEmail(registerDTO.getEmail());

        if (user == null) {
            throw new RuntimeException("请先获取验证码");
        }
        if ( !user.getVerificationCode().equals(registerDTO.getVerificationCode())) {
            throw new RuntimeException("验证码错误");
        }

        if (user.getVerificationCodeExpireTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("验证码已过期");
        }

        // 更新用户信息
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setEmailVerified(true);
        user.setUpdateTime(LocalDateTime.now());


        usersMapper.updateEmailVerified(user.getId());
        usersMapper.updateUserInfo(user);
    }

}
