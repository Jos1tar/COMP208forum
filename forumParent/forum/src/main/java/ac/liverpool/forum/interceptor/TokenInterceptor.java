package ac.liverpool.forum.interceptor;

import ac.liverpool.forum.service.TokenBlacklistService;
import ac.liverpool.forum.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenBlacklistService tokenBlacklistService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. get the token from the request header

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true; // allow preflight requests of  OPTIONS
        }

        String authHeader = request.getHeader("Authorization");

        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.info("Authorization Missing header or formatting error");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String jwt = authHeader.substring(7); // 去掉 "Bearer "

        // 3. check if the token is blacklisted (expired or logged out)
        if (tokenBlacklistService.isBlacklisted(jwt)) {
            log.info("The head token has been logged out, returning a non login error");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 4. Parse the token and extract the userId

        try {
            Claims claims = JwtUtils.parseJWT(jwt);
            if (claims == null) {
                log.info("Authorization Missing header or formatting error");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            Long userId = claims.get("id", Long.class); //


            request.setAttribute("userId", userId);
        } catch (Exception e) {
            log.error("Failed to parse token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 5. 放行
        log.info("Token verification passed, release request: {}", request.getRequestURI());
        return true;
    }

}
