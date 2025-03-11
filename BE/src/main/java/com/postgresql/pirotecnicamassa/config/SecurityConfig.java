package com.postgresql.pirotecnicamassa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disabilita CSRF per le API REST
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/admin/layout/save-layout").hasRole("ADMIN") // Solo ADMIN può salvare layout
                                .requestMatchers("/api/prodotti/carica").hasRole("ADMIN") // Solo ADMIN può caricare prodotti
                                .anyRequest().permitAll() // Accesso aperto alle altre pagine
                )
                .formLogin().disable() // Disabilita il modulo di login di Spring Security
                .httpBasic(); // Abilita autenticazione HTTP Basic per test
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin123") // Usa {noop} per password plaintext (solo per test, non in produzione)
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}
