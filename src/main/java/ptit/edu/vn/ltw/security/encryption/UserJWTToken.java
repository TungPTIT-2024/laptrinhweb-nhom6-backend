package ptit.edu.vn.ltw.security.encryption;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class UserJWTToken {
    private String userId;
    private String userName;
}
