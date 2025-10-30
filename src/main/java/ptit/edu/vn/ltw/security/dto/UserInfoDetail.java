package ptit.edu.vn.ltw.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Setter
@Accessors(chain = true)
public class UserInfoDetail implements UserDetails {
    private Collection<? extends GrantedAuthority> authorities;
    private String username;
    private String password;
    @Getter
    private String id;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
