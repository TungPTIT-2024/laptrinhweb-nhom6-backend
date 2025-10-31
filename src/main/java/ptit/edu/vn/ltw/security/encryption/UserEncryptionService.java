package ptit.edu.vn.ltw.security.encryption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ptit.edu.vn.ltw.entity.UserInfo;
import ptit.edu.vn.ltw.exception.HttpStatusException;
import ptit.edu.vn.ltw.security.config.JwtProperties;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;


@Service
@RequiredArgsConstructor
@SuppressWarnings({"java:S1874", "java:S1166"})
public class UserEncryptionService {
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    private SecretKey jwtKey;

    @PostConstruct
    public void init() {
        String secret = jwtProperties.getSecret();
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT secret not configured");
        }

        try {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            if (keyBytes.length < 64) {
                throw new IllegalStateException("Decoded key too short for HS512: " + (keyBytes.length * 8) + " bits. Provide a 512-bit (64 byte) key encoded in Base64.");
            }
            jwtKey = Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("JWT secret must be a Base64-encoded 64-byte (512-bit) key for HS512", ex);
        }
    }

    public UserJWTToken decryptToken(String token) throws BadCredentialsException {
        try {
            JwtParser parser = Jwts.parser()
                    .setSigningKey(jwtKey)
                    .build();

            // parseClaimsJws validates signature; throws JwtException on failure
            Jws<Claims> jws = parser.parseClaimsJws(token);

            String alg = jws.getHeader().getAlgorithm();
            if (!"HS512".equalsIgnoreCase(alg)) {
                throw new BadCredentialsException("Unsupported algorithm: " + alg);
            }

            Claims claims = jws.getBody();
            Date exp = claims.getExpiration();
            if (exp != null && exp.before(new Date())) {
                throw new BadCredentialsException("Expired token");
            }

            String subject = claims.getSubject();
            return objectMapper.readValue(subject, UserJWTToken.class);
        } catch (JsonProcessingException e) {
            throw new BadCredentialsException("Token payload parsing error", e);
        } catch (JwtException | IllegalArgumentException e) {
            // includes SignatureException, MalformedJwtException, ExpiredJwtException, etc.
            throw new BadCredentialsException("Invalid JWT: " + e.getMessage(), e);
        }
    }

    public String encryptToken(UserInfo userInfo) {
        UserJWTToken token = new UserJWTToken()
                .setUserId(userInfo.getId())
                .setUserName(userInfo.getUsername());

        Instant expireTime = Instant.now().plus(jwtProperties.getExpiration(), ChronoUnit.SECONDS);
        try {
            return Jwts.builder()
                    .setSubject(objectMapper.writeValueAsString(token))
                    .setExpiration(Date.from(expireTime))
                    .signWith(jwtKey, SignatureAlgorithm.HS512)
                    .compact();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Generate Token Error", e);
        }
    }

}
