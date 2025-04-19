package ac.liverpool.forum.service;

import ac.liverpool.forum.dto.UserRegisterDTO;
import ac.liverpool.forum.entity.LoginInfo;
import ac.liverpool.forum.entity.Result;
import ac.liverpool.forum.entity.Users;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UsersService {
    LoginInfo login(Users user);

    Result logout(HttpServletRequest request);

    Result userInfo(Long userId);

    Result updateUserInfo(Users user);

    Result getMyPosts(Long userId);

    void sendVerificationCode(String email);

    void register(UserRegisterDTO registerDTO);
}
