package ptit.edu.vn.ltw.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ptit.edu.vn.ltw.exception.ExceptionResponse;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        ExceptionResponse body = new ExceptionResponse()
                .setHttpStatus(HttpServletResponse.SC_UNAUTHORIZED)
                .setMessage(authException.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(response.getOutputStream(), body);
    }
}
