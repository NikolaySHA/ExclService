package com.ExclusiveService.config;

import com.ExclusiveService.repo.UserRepository;
import com.ExclusiveService.service.impl.ExclusiveUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Permit all static resources
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                // Permit access to specific paths for all users
                                .requestMatchers("/", "/users/login", "/users/register", "/gallery","/users/login-error", "/contacts", "/services").permitAll()
                                // All other requests need to be authenticated
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/users/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/home", true )
                                .failureUrl("/users/login-error")
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/users/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                ).csrf(csrf -> csrf // This enables CSRF protection
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // Example of using a custom CSRF token repository
                )
                .build();
    }
    @Bean
    public ExclusiveUserDetailsService userDetailsService(UserRepository userRepository){
        return new ExclusiveUserDetailsService(userRepository);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder
                .defaultsForSpringSecurity_v5_8();
    }
}
