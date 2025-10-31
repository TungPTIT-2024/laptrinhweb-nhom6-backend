package ptit.edu.vn.ltw.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ptit.edu.vn.ltw.security.dto.UserToken;
import ptit.edu.vn.ltw.security.service.UserManager;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserFilter extends OncePerRequestFilter {

    private final UserManager userManager;


    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (Strings.isEmpty(request.getHeader(AUTHORIZATION_HEADER))){
            throw new AuthenticationCredentialsNotFoundException("No authentication header");
        }

        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (!token.startsWith(BEARER_PREFIX)){
            throw new AuthenticationCredentialsNotFoundException("Authentication header with wrong pattern");
        }

        String credential = token.substring(BEARER_PREFIX.length());
        Authentication authentication = userManager.authenticate(new UserToken(credential));
        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}
