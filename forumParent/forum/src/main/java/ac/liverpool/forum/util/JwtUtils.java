package ac.liverpool.forum.util;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.security.Keys; // 注意要加这个import
import javax.crypto.SecretKey;          // 还有这个

public class JwtUtils {

    private static final String SIGN_KEY_STRING = "b1c3e5g7i9k1m3o5q7s9u1w3y5a7c9eb1c3e5g7i9k1m3o5q7s9u1w3y5a7c9e";
    private static final SecretKey SIGN_KEY = Keys.hmacShaKeyFor(SIGN_KEY_STRING.getBytes());
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000L; // 12hours

    public static String generateJwt(Long userId, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SIGN_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims parseJWT(String jwt) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SIGN_KEY)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Token signature error: " + e.getMessage());
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println("Token format error: " + e.getMessage());
        }
        return null;
    }
}