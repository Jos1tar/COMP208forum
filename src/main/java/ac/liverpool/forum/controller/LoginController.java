package ac.liverpool.forum.controller;

import ac.liverpool.forum.entity.LoginInfo;
import ac.liverpool.forum.entity.Result;
import ac.liverpool.forum.entity.Users;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public Result login(@RequestBody Users user){
        log.info("用户登录 , {}", user);
        LoginInfo loginInfo = usersService.login(user);
        if(loginInfo != null){
            return Result.success(loginInfo);
        }
        return Result.error(AppHttpCodeEnum.valueOf("用户名或密码错误~"));
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        return usersService.logout(request);
    }

}