package clever.cloning.repository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.util.Date;

@Repository
public class TokenRepository {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long tokenValidityInMilliseconds = 500000; // 5분

    public String generateTokenWithTimestamp(String username) {
        long timestamp = System.currentTimeMillis();
        String usernameWithTimestamp = username + "_" + timestamp;

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(usernameWithTimestamp)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean isTokenNeedRefresh(String token) {
        try {
            String usernameWithTimestamp = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
            String[] parts = usernameWithTimestamp.split("_");

            if (parts.length == 2) {
                long timestamp = Long.parseLong(parts[1]);
                long currentTime = System.currentTimeMillis();

                // 5분 경과 시 토큰 재생성 필요
                return (currentTime - timestamp) >= tokenValidityInMilliseconds;
            }

            return false;
        } catch (ExpiredJwtException e) {
            // Token has expired, return false
            return true; // 변경: 토큰이 만료된 경우도 true로 반환
        } catch (Exception e) {
            // Handle other exceptions if needed
            return false;
        }
    }
}
