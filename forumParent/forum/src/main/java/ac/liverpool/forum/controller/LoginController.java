package ac.liverpool.forum.controller;

import ac.liverpool.forum.entity.LoginInfo;
import ac.liverpool.forum.entity.VO.Result;
import ac.liverpool.forum.entity.Users;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.service.TokenBlacklistService;
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
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/login")
    public Result login(@RequestBody Users user){
        log.info("User login , {}", user);
        LoginInfo loginInfo = usersService.login(user);
        if(loginInfo != null){
            return Result.success(loginInfo);
        }
        return Result.error(AppHttpCodeEnum.valueOf("Username or password is incorrect"));
    }

    @PostMapping("/logout")//get the token from the request header and add it to the blacklist
    public Result logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.addToBlacklist(token);
            return Result.success();
        }
        return Result.error(AppHttpCodeEnum.NEED_LOGIN);
    }

}