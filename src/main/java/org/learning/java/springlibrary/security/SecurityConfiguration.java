package org.learning.java.springlibrary.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public UserDetailsService userDetailsService() {
    return new DatabaseUserDetailsService();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(userDetailsService());
    return authenticationProvider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers("/books", "/books/show/**").hasAnyAuthority("ADMIN", "USER")
        .requestMatchers("/books/create", "/books/edit/**", "/books/delete/**")
        .hasAuthority("ADMIN")
        .requestMatchers("/borrowings/**").hasAuthority("ADMIN")
        .requestMatchers("/users", "/users/**").hasAuthority("ADMIN")
        .requestMatchers("/categories", "/categories/**").hasAuthority("ADMIN")
        .requestMatchers("/**").permitAll()
        .and().formLogin()
        .and().logout();
    return http.build();
  }
}
