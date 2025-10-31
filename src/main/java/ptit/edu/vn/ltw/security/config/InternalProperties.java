package ptit.edu.vn.ltw.security.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "internal")
@NoArgsConstructor
public class InternalProperties {
    private String secret;
}
