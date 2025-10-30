package ptit.edu.vn.ltw.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ptit.edu.vn.ltw.entity.UserInfo;
import ptit.edu.vn.ltw.repository.UserInfoRepository;
import ptit.edu.vn.ltw.security.dto.UserInfoDetail;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
        UserInfo userInfo = userInfoRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        List<SimpleGrantedAuthority> grantedAuthorityList = List.of(new SimpleGrantedAuthority("USER"));

        return new UserInfoDetail().setUsername(userInfo.getUsername())
                .setAuthorities(grantedAuthorityList)
                .setPassword(userInfo.getPassword());
    }

}
