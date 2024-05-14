package com.mevy.libraryapi.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mevy.libraryapi.security.JWTAuthenticationFilter;
import com.mevy.libraryapi.security.JWTAuthorizationFilter;
import com.mevy.libraryapi.security.JWTUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    public static String[] PUBLIC_GET = {
        "/book/*"
    };

    public static String[] PUBLIC_POST = {
        "/user"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                    .getSharedObject(AuthenticationManagerBuilder.class);
                    authenticationManagerBuilder
                            .userDetailsService(userDetailsService)
                            .passwordEncoder(bCryptPasswordEncoder());
                    authenticationManager = authenticationManagerBuilder.build();
        
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.GET,PUBLIC_GET).permitAll()
                    .requestMatchers(HttpMethod.POST, PUBLIC_POST).permitAll()
                    .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager)
                .addFilter(new JWTAuthenticationFilter(authenticationManager, jwtUtil))
                .addFilter(new JWTAuthorizationFilter(authenticationManager, jwtUtil, userDetailsService))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
