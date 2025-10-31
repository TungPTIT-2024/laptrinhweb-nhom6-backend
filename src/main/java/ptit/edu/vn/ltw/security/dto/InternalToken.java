package ptit.edu.vn.ltw.security.dto;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;

@EqualsAndHashCode(callSuper = false)
public class InternalToken extends AbstractAuthenticationToken {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String internalKey;
    private final transient InternalPrincipal principal;

    public InternalToken(String internalKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.internalKey = internalKey;
        principal = new InternalPrincipal("ADMIN");
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.internalKey;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
