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
        // 1. 获取请求头中的令牌（token）
        String jwt = request.getHeader("token");

        // 2. 判断令牌是否存在
        if (!StringUtils.hasLength(jwt)) {
            log.info("获取到jwt令牌为空, 返回未登录错误");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 3. 检查令牌是否在黑名单中（已注销）
        if (tokenBlacklistService.isBlacklisted(jwt)) {
            log.info("令牌已被注销, 返回未登录错误");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 4. 解析token，并提取userId
        // 在TokenInterceptor的preHandle方法中修改这部分
        try {
            Claims claims = JwtUtils.parseJWT(jwt);
            if (claims == null) {
                log.info("解析令牌失败, 返回未登录错误");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            // 修改这里 - 从正确的claims字段获取用户ID
            Long userId = claims.get("id", Long.class); // 假设用户ID存储在"id"字段
            // 或者: Long userId = Long.valueOf(claims.getSubject()); // 如果确实使用subject

            request.setAttribute("userId", userId);
        } catch (Exception e) {
            log.error("解析令牌失败: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 5. 放行
        log.info("令牌验证通过, 放行请求: {}", request.getRequestURI());
        return true;
    }

}
