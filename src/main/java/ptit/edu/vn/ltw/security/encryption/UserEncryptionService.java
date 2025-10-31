package ptit.edu.vn.ltw.security.encryption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
        jwtKey = new SecretKeySpec(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
    }

    public UserJWTToken decryptToken(String credentials) throws AuthenticationException {
        try {
            JwtParser parser = Jwts.parser().decryptWith(jwtKey).build();
            Jws<Claims> claimsJws = parser.parseSignedClaims(credentials);
            String algorithm = claimsJws.getHeader().getAlgorithm();
            if (!algorithm.equals("HS512")) {
                throw new BadCredentialsException("Unsupported algorithm: " + algorithm);
            }

            Date expireTime = claimsJws.getPayload().getExpiration();
            if (expireTime.before(Date.from(Instant.now()))) {
                throw new BadCredentialsException("Expired Token");
            }

            String body = claimsJws.getPayload().getSubject();

            return objectMapper.readValue(body, UserJWTToken.class);
        } catch (BadCredentialsException e){
            throw new BadCredentialsException(e.getMessage());
        } catch (JsonProcessingException _) {
            throw new BadCredentialsException("Token could not be decrypted");
        }
    }

    public String encryptToken(UserInfo userInfo){
        UserJWTToken token = new UserJWTToken().setUserId(userInfo.getId()).setUserName(userInfo.getFullName());
        Instant expireTime = Instant.now().plus(jwtProperties.getExpiration(), ChronoUnit.SECONDS);
        try {
            return Jwts.builder()
                    .subject(objectMapper.writeValueAsString(token))
                    .expiration(Date.from(expireTime))
                    .signWith(SignatureAlgorithm.HS512, jwtKey).compact();
        } catch (JsonProcessingException _) {
            throw HttpStatusException.internalServerError("Generate Token Error");
        }
    }
}
