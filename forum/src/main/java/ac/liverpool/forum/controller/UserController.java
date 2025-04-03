package ac.liverpool.forum.controller;


import ac.liverpool.forum.entity.Result;
import ac.liverpool.forum.entity.Users;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UsersService usersService;

    // 获取用户信息
    @GetMapping("/userInfo/{userId}")
    public Result userInfo(@PathVariable Long userId) {

        // 从请求属性中获取userId

        if (userId == null) {
            // 使用新添加的枚举常量
            return Result.error(AppHttpCodeEnum.USER_ID_MISSING);
        }
        return usersService.userInfo(userId);
    }

    // 更新用户信息
    @PutMapping("/userInfo/{userId}")
    public Result updateUserInfo(@RequestBody Users user) {
        log.info("更新用户信息: user={}", user);
        if (user.getId() == null) {
            return Result.error(AppHttpCodeEnum.USER_ID_MISSING);
        }
        return usersService.updateUserInfo(user);
    }

    //根据用户id查看我的帖子
    @GetMapping("/userInfo/{userId}/posts")
    public Result getMyPosts(@PathVariable Long userId) {
        log.info("获取用户帖子: userId={}", userId);
        if (userId == null) {
            return Result.error(AppHttpCodeEnum.USER_ID_MISSING);
        }
        // 调用 service 方法获取用户帖子
        return usersService.getMyPosts(userId);
    }

}
