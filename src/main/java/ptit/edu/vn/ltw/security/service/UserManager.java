package ptit.edu.vn.ltw.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ptit.edu.vn.ltw.security.dto.UserInfoDetail;
import ptit.edu.vn.ltw.security.dto.UserPrincipal;
import ptit.edu.vn.ltw.security.dto.UserToken;
import ptit.edu.vn.ltw.security.encryption.UserEncryptionService;
import ptit.edu.vn.ltw.security.encryption.UserJWTToken;

@Component
@RequiredArgsConstructor
public class UserManager implements AuthenticationManager {
    private final UserInfoService userInfoService;
    private final UserEncryptionService userEncryptionService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String credentials = authentication.getCredentials().toString();
        UserJWTToken jwtToken = userEncryptionService.decryptToken(credentials);
        UserInfoDetail userDetail = (UserInfoDetail) userInfoService.loadUserByUsername(jwtToken.getUserName());

        UserPrincipal principal = new UserPrincipal()
                .setId(userDetail.getId())
                .setUsername(userDetail.getUsername());

        return new UserToken(credentials, principal, userDetail.getAuthorities());
    }
}
