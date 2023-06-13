package by.demoProject.instaZoo.security;

import by.demoProject.instaZoo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {
    public String generateToken(Authentication authentication) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes());
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expireDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
        String userId = Long.toString(user.getId());

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", userId);
        claimsMap.put("username", user.getEmail());
        claimsMap.put("firstname", user.getName());
        claimsMap.put("lastname", user.getLastName());
        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecurityConstants.SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String id = (String) claims.get("id");
        return Long.parseLong(id);
    }

    public boolean validateToken(String token) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes());
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            // Токен успешно проверен и декодирован
            // Можно выполнить дополнительные проверки или получить информацию из токена
            // через claimsJws.getBody()
            return true;
        } catch (Exception e) {
            // Возникла ошибка при проверке токена или токен недействителен
            return false;
        }
    }
}