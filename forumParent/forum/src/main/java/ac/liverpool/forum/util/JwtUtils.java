package ac.liverpool.forum.util;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final String SIGN_KEY = "Y29tcHV0ZXJzY2llbmNl"; // Signing Key（Base64 encoding "computerscience"）
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000L; // 12 hours of expiration time

    /**
     * Generate JWT token
     * @param userId
     * @param claims extra claims to be stored in the token
     * @return JWT token
     */
    public static String generateJwt(Long userId, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // Set userId as JWT theme
                .addClaims(claims) // Extra stored claims
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // Set expiration time
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY) // Set signing algorithm and key
                .compact();
    }

    /**
     * Analyze JWT token and extract userId
     * @param jwt
     * @return userId
     */
    public static Claims parseJWT(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(SIGN_KEY)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Tokenm signature error: " + e.getMessage());
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println("Token format error: " + e.getMessage());
        }
        return null;
    }

}
