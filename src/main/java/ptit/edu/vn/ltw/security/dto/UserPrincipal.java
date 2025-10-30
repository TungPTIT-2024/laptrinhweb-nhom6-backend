package ptit.edu.vn.ltw.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserPrincipal {
    private String id;
    private String username;
}
