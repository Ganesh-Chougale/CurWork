package com.PB.ParkingBay.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Disables CSRF for simplicity in testing
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers(HttpMethod.POST, "/**").permitAll()  // Permit all POST requests
                    .requestMatchers(HttpMethod.GET, "/**").permitAll()   // Permit all GET requests
                    .requestMatchers(HttpMethod.PUT, "/**").permitAll()   // Permit all PUT requests
                    .requestMatchers(HttpMethod.DELETE, "/**").permitAll() // Permit all DELETE requests
                    .anyRequest().permitAll()  // Permit all other requests
            );
        return http.build();
    }
}
