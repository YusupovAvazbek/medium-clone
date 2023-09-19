package com.example.mediumclone.config;

import com.example.mediumclone.baseDto.ResponseDto;
import com.example.mediumclone.message.AppStatusCodes;
import com.example.mediumclone.moduls.userservice.service.impl.UsersServiceImpl;
import com.example.mediumclone.security.JwtFilter;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    @Lazy
    UsersServiceImpl usersService;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    Gson gson;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request ->
                        request.requestMatchers( "/api/users/sign-up").permitAll()
                        .requestMatchers("/api/users/login").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
    private CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedHeaders(List.of("Authentication","SECRET-HEADER","Content-Type"));
        cors.addAllowedMethod("*");
        cors.addAllowedOrigin("null");

        UrlBasedCorsConfigurationSource url = new UrlBasedCorsConfigurationSource();
        url.registerCorsConfiguration("/**",cors);

        return url;
    }

    private AuthenticationEntryPoint entryPoint(){
        return ((request, response, exception) -> {
            response.getWriter().println(gson.toJson(ResponseDto.builder()
                    .message("Token is not valid "+exception.getMessage()+
                            (exception.getCause()!=null?exception.getCause().getMessage():""))
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .build()));
            response.setContentType("application/json");
        });
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
