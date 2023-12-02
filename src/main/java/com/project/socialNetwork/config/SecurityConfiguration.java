package com.project.socialNetwork.config;

import com.project.socialNetwork.model.response.BaseResponse;
import com.project.socialNetwork.repository.TokenRepository;
import com.project.socialNetwork.service.authentication.LogoutService;
import com.project.socialNetwork.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private final LogoutService logoutService;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout().logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutService)
                .logoutSuccessHandler(((request, response, authentication) -> {
                    final String authHeader = request.getHeader("Authorization");
                    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        BaseResponse.sendUnauthorized(response,"Unauthorized");
                        return;
                    }
                    final String jwt = authHeader.substring(7);
                    final String email;
                    try {
                        email = jwtService.extractUserName(jwt);
                    } catch (Exception e) {
                        log.info(e.getMessage());
                        BaseResponse.sendUnauthorized(response,"Unauthorized");
                        return;
                    }
                    BaseResponse.sendOk(response, "Logout successfully");
                    SecurityContextHolder.clearContext();
                }));

        return http.build();
    }

}
