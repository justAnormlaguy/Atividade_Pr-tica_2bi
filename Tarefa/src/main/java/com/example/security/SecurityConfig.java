//package com.example.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sem estado (sem sessões HTTP)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/tasks", "/login", "/register", "/css/**", "/js/**").permitAll() // Endpoints públicos
//                .anyRequest().authenticated() // Requer autenticação para outros endpoints
//                .and()
//                .oauth2ResourceServer()
//                .jwt(); // Usar autenticação via JWT
//
//        return http.build();
//    }
//
//
//}
