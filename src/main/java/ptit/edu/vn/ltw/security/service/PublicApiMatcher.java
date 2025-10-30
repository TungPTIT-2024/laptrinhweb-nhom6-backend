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
            "/api/v1/register/**"
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
        PathContainer pathContainer = PathContainer.parsePath(requestPath);

        for (PathPattern pattern : publicPatterns) {
            if (pattern.matches(pathContainer)) {
                return true;
            }
        }
        return false;
    }

}
