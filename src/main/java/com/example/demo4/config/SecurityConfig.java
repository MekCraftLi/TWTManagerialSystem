package com.example.demo4.config;

import com.example.demo4.Filter.JwtFilter;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Resource
    private JwtFilter jwtFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Disable CSRF and session management
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.logout(AbstractHttpConfigurer::disable);

        // Set Authorization Policy
        http.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(
                                "/hello/**",
                                "/user/login/**",
                                "/user/register/**"
                        ).permitAll()
                        .anyRequest().authenticated()
        );

        // Add Jwt Authentication Filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


//        // Add Exception Handling
        http.exceptionHandling(
                exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(
                                (request, response, accessDeniedException) -> {
                                    response.setHeader("Content-Type", "application/json;charset=utf-8");
                                    response.getWriter().println("{\"code\":403,\"msg\":\"Forbidden\",\"data\":\"无权限！\"}");
                                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                }
                        )
                        .authenticationEntryPoint(
                                (request, response, authException) -> {
                                    response.setHeader("Content-Type", "application/json;charset=utf-8");
                                    response.getWriter().println("{\"code\":401,\"msg\":\"Unauthorized\",\"data\":\"令牌失效或不存在！\"}");
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                }
                        )
        );

        return http.build();
    }

}
