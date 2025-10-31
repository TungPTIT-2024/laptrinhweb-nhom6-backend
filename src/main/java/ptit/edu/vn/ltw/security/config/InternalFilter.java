package ptit.edu.vn.ltw.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ptit.edu.vn.ltw.security.dto.InternalToken;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InternalFilter extends OncePerRequestFilter {

    private final InternalProperties internalProperties;

    private static final String API_KEY_HEADER = "INTERNAL_API_KEY";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (Strings.isEmpty(request.getHeader(API_KEY_HEADER))){
            throw new AuthenticationCredentialsNotFoundException("No authentication header");
        }

        String secretToken = request.getHeader(API_KEY_HEADER);

        if (!secretToken.equals(internalProperties.getSecret())){
            throw new BadCredentialsException("Invalid internal api key");
        }

        List<GrantedAuthority> authority = List.of(new SimpleGrantedAuthority("ADMIN"));

        InternalToken internalToken = new InternalToken(internalProperties.getSecret(), authority);

        SecurityContextHolder.getContext().setAuthentication(internalToken);
        try {
            SecurityContextHolder.getContext().setAuthentication(internalToken);
            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}
