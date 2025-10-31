package ptit.edu.vn.ltw.security.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "jwt")
@NoArgsConstructor
public class JwtProperties {
    private String secret;
    private int expiration;
}
