package com.sombra.management.security.config;

import com.sombra.management.entity.enumeration.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/auth/**"
                ).permitAll()
                .requestMatchers(
                        "/api/course/user/**",
                        "/api/file/**",
                        "/api/mark/calculate",
                        "/api/homework/**"
                ).hasAnyAuthority(UserRole.ADMIN.name(), UserRole.INSTRUCTOR.name(), UserRole.STUDENT.name())
                .requestMatchers(
                        "/api/course/create",
                        "/api/mark",
                        "/api/feedback/**"
                ).hasAnyAuthority(UserRole.ADMIN.name(), UserRole.INSTRUCTOR.name())
                .requestMatchers(
                        "/api/course-graduation/**",
                        "/api/lesson/**",
                        "/api/course/register/**"
                ).hasAnyAuthority(UserRole.ADMIN.name(), UserRole.STUDENT.name())
                .requestMatchers(
                        "/api/user/**",
                        "/api/course/assign/**"
                ).hasAnyAuthority(UserRole.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

}
