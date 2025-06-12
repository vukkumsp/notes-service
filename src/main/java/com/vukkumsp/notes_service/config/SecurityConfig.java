package com.vukkumsp.notes_service.config;

import com.vukkumsp.notes_service.security.AdminJwtAuthenticationFilter;
import com.vukkumsp.notes_service.security.GuestJwtAuthenticationFilter;
import com.vukkumsp.notes_service.security.JwtDummyAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    GuestJwtAuthenticationFilter guestJwtAuthFilter;

    @Autowired
    AdminJwtAuthenticationFilter adminJwtAuthFilter;

    @Autowired
    JwtDummyAuthFilter dummyFilter;

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://localhost:5173/",
                "http://notes.vukkumsp.com",
                "https://notes.vukkumsp.com"
        )); // or ["http://localhost:3000"]
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true); // optional, for cookies/auth headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    @Bean
    public SecurityWebFilterChain guestSecurityFilterChain(ServerHttpSecurity http) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(Customizer.withDefaults())
                .addFilterBefore(guestJwtAuthFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/token/guest").permitAll()
//                        .pathMatchers(HttpMethod.GET, "/api/login").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/notes").hasRole("GUEST")
                        .pathMatchers(HttpMethod.POST, "/api/login").hasRole("GUEST")
                        .anyExchange()
                        .authenticated()
                )
                .build();
    }

    @Bean
    public SecurityWebFilterChain adminSecurityFilterChain(ServerHttpSecurity http) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(Customizer.withDefaults())
                .addFilterBefore(adminJwtAuthFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                        .pathMatchers(HttpMethod.POST, "/api/login").permitAll()
//                        .pathMatchers(HttpMethod.GET, "/api/token/admin").permitAll()
//                        .pathMatchers(HttpMethod.PUT, "/api/notes").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.POST, "/api/notes").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/notes").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/notes").hasRole("ADMIN")
                        .anyExchange()
                        .authenticated()
                )
                .build();
    }
}
