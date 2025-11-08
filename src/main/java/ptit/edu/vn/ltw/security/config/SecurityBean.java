package ptit.edu.vn.ltw.security.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ptit.edu.vn.ltw.security.service.InternalApiMatcher;
import ptit.edu.vn.ltw.security.service.PublicApiMatcher;
import ptit.edu.vn.ltw.security.service.UserManager;

import java.util.List;

@Configuration
public class SecurityBean {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<InternalFilter> disableInternalFilterRegistration(InternalFilter filter) {
        FilterRegistrationBean<InternalFilter> reg = new FilterRegistrationBean<>(filter);
        reg.setEnabled(false);
        return reg;
    }

    @Bean
    public FilterRegistrationBean<UserFilter> disableUserFilterRegistration(UserFilter filter) {
        FilterRegistrationBean<UserFilter> reg = new FilterRegistrationBean<>(filter);
        reg.setEnabled(false);
        return reg;
    }

    @Bean("corsAllOrigin")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // chấp nhận tất cả origin, nhưng vẫn xét header Authorization
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain publicSecurityChain(HttpSecurity http, PublicApiMatcher publicApiMatcher,
                                                   @Qualifier("corsAllOrigin") CorsConfigurationSource source) throws Exception {

        http.securityMatcher(publicApiMatcher)
                .cors(cors -> cors.configurationSource(source))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain internalSecurityChain(HttpSecurity http, InternalApiMatcher internalApiMatcher, InternalFilter internalFilter,
                                                     @Qualifier("corsAllOrigin") CorsConfigurationSource source) throws Exception {

        http.securityMatcher(internalApiMatcher)
                .cors(cors -> cors.configurationSource(source))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .addFilterBefore(internalFilter, ExceptionTranslationFilter.class);
        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain apiSecurityChain(HttpSecurity http,
                                                UserManager userManager,
                                                UserFilter userFilter,
                                                @Qualifier("corsAllOrigin") CorsConfigurationSource source) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(source))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .authenticationManager(userManager)
                .addFilterBefore(userFilter, ExceptionTranslationFilter.class);

        return http.build();
    }
}
