//package com.getyourdoc.getyourdoctors.config;
//
//import com.getyourdoc.getyourdoctors.models.Patient;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.SecurityBuilder;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//public class JwtConfig implements WebSecurityConfigurer {
//    @Value("${app.jwtSecret}") // Set the value in application.properties
//    private String jwtSecret;
//
//
//    @Override
//    public void init(SecurityBuilder builder) throws Exception {
//
//    }
//
//    @Override
//    public void configure(SecurityBuilder builder) throws Exception {
//
//    }
//
//
//    @Deprecated
//    public SecurityFilterChain createSecurityFilterChain(HttpSecurity http) throws Exception {
//        // Configure your security filters and rules here
//        return http
//                .exceptionHandling(exceptionHandling ->
//                        exceptionHandling
//                                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
//                )
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .antMatchers("/login").permitAll() // Allow access to the login endpoint
//                                .anyRequest().authenticated() // Require authentication for other endpoints
//                )
//                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection for simplicity, but it's recommended to enable it
//                .build();
//    }
//}
