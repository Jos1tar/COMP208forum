package ac.liverpool.forum.service.impl;

import ac.liverpool.forum.entity.LoginInfo;
import ac.liverpool.forum.entity.Posts;
import ac.liverpool.forum.entity.Result;
import ac.liverpool.forum.entity.Users;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.mapper.UsersMapper;
import ac.liverpool.forum.service.TokenBlacklistService;
import ac.liverpool.forum.service.UsersService;
import ac.liverpool.forum.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private TokenBlacklistService tokenBlacklistService;
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
}
