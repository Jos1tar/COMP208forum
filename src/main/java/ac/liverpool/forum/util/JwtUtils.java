package ac.liverpool.forum.util;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final String SIGN_KEY = "Y29tcHV0ZXJzY2llbmNl"; // 签名密钥（Base64编码后的 "computerscience"）
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000L; // 12小时

    /**
     * 生成JWT令牌
     * @param userId 用户ID
     * @param claims 额外存储的数据（可选）
     * @return JWT令牌
     */
    public static String generateJwt(Long userId, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // 设置 userId 作为 JWT 主题
                .addClaims(claims) // 额外存储的 claims
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 过期时间
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY) // 签名
                .compact();
    }

    /**
     * 解析JWT令牌并提取userId
     * @param jwt JWT令牌
     * @return userId（如果解析失败则返回 null）
     */
    public static Claims parseJWT(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(SIGN_KEY)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token已过期: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Token签名无效: " + e.getMessage());
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println("Token格式错误: " + e.getMessage());
        }
        return null;
    }

}
