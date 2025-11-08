package ptit.edu.vn.ltw.security.service;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.PathContainer;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.List;

@Component
public class PublicApiMatcher implements RequestMatcher {
    private static final PathPatternParser PARSER = new PathPatternParser();

    private static final List<String> PUBLIC_API_PATHS = List.of(
            "/api/v1/login/**",
            "/api/v1/products/**",
            "/api/v1/register/**",
            "/h2-console/**",
            "/health", "/favicon.ico", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**", "/swagger-ui.html",
            "/webjars/**", "/swagger-resources/**", "/swagger-resources", "/error/**"
    );

    private List<PathPattern> publicPatterns;

    @PostConstruct
    protected void init() {
        publicPatterns = PUBLIC_API_PATHS.stream()
                .map(PARSER::parse)
                .toList();
    }


    @Override
    public boolean matches(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (contextPath != null && !contextPath.isEmpty() && requestPath.startsWith(contextPath)) {
            requestPath = requestPath.substring(contextPath.length());
            if (requestPath.isEmpty()) {
                requestPath = "/";
            }
        }

        PathContainer pathContainer = PathContainer.parsePath(requestPath);

        for (PathPattern pattern : publicPatterns) {
            if (pattern.matches(pathContainer)) {
                return true;
            }
        }
        return false;
    }

}
