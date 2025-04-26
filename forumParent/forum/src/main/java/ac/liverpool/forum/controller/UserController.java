package ac.liverpool.forum.controller;


import ac.liverpool.forum.entity.dto.UserRegisterDTO;
import ac.liverpool.forum.entity.VO.Result;
import ac.liverpool.forum.entity.Users;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UsersService usersService;

    // get user information by userId
    @GetMapping("/userInfo/{userId}")
    public Result userInfo(@PathVariable Long userId) {

        if (userId == null) {
            return Result.error(AppHttpCodeEnum.USER_ID_MISSING);
        }
        return usersService.userInfo(userId);
    }

    // update user information
    @PutMapping("/userInfo/{userId}")
    public Result updateUserInfo(@RequestBody Users user) {
        log.info("update user info: user={}", user);
        if (user.getId() == null) {
            return Result.error(AppHttpCodeEnum.USER_ID_MISSING);
        }
        return usersService.updateUserInfo(user);
    }

    // get user posts by userId
    @GetMapping("/userInfo/{userId}/posts")
    public Result getMyPosts(@PathVariable Long userId) {
        log.info("retrive user posts: userId={}", userId);
        if (userId == null) {
            return Result.error(AppHttpCodeEnum.USER_ID_MISSING);
        }

        return usersService.getMyPosts(userId);
    }


    @PostMapping("/send-verification-code")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam String email) {
        usersService.sendVerificationCode(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserRegisterDTO registerDTO) {
        usersService.register(registerDTO);
        return ResponseEntity.ok().build();
    }
}
