package ptit.edu.vn.ltw.security.service;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.PathContainer;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

@Component
public class InternalApiMatcher implements RequestMatcher {
    private static final PathPatternParser PARSER = new PathPatternParser();

    private static final String INTERNAL_API_PATH = "/internal/api/**";


    private PathPattern privatePattern;

    @PostConstruct
    protected void init() {
        privatePattern = PARSER.parse(INTERNAL_API_PATH);
    }


    @Override
    public boolean matches(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        PathContainer pathContainer = PathContainer.parsePath(requestPath);
        return privatePattern.matches(pathContainer);
    }

}
