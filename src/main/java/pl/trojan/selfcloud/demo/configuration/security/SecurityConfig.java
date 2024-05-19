package pl.trojan.selfcloud.demo.configuration.security;

import jakarta.servlet.DispatcherType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.trojan.selfcloud.demo.filter.JwtAuthenticationFilter;
import pl.trojan.selfcloud.demo.model.privileges.RoleName;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig  {

    private final static String[] staticResources  =  {
            "/static/**",
            "/css/**",
            "/images/**",
    };
    private final static String[] developerAccess = {
            "/h2-console/**"
    };
    @Autowired
    private final AuthenticationProvider authenticationProvider;
    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final static String USER = RoleName.USER.toString();
    private final static String MODERATOR = RoleName.MODERATOR.toString();
    private final static String ADMIN = RoleName.ADMIN.toString();

    @Autowired
    private final PasswordEncoder encoder;
    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private final DefaultOAuth2UserService oAuth2UserService;

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {


        http.authorizeHttpRequests(authz -> authz
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(staticResources).permitAll()
                        .requestMatchers(developerAccess).permitAll()
//                        .requestMatchers( HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers( "/auth/**").permitAll()
                        .requestMatchers( "/login/**").permitAll()
                        .requestMatchers( "/oath2/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/home/user/**").hasRole(USER)
                        .requestMatchers("/home/moderator/**").hasRole(MODERATOR)
                        .requestMatchers("/home/admin/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/orders/**").hasRole(USER)
                        .requestMatchers(HttpMethod.POST, "/orders/**").hasRole(MODERATOR)
                        .requestMatchers(HttpMethod.PUT, "/orders/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/orders/**").hasRole(ADMIN)
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults()
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/home/hello")
                        .userInfoEndpoint(infoEndpoint ->
                                infoEndpoint.userService(oAuth2UserService))
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                )
//            .exceptionHandling(auth -> auth.authenticationEntryPoint(
//                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")
                .disable());
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8090"));
        configuration.setAllowedMethods(List.of("GET","POST", "DELETE", "PUT"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}
