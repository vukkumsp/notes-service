package com.vukkumsp.notes_service.security;

import com.vukkumsp.notes_service.service.JwtService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class UserJwtAuthenticationFilter implements WebFilter {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        System.out.println("before appName : "+appName);
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            System.out.println("authHeader : "+authHeader);

            try {
                Jwt jwt = jwtService.validateToken(token);
//                String apn = jwt.getIssuer().getAuthority();
                String username = jwt.getSubject();
//                System.out.println("apn : "+apn);
                System.out.println("appName : "+appName);

//                Claims claims = JwtUtil.getClaimsFromToken(token);
//                String username = claims.getSubject();

                List<String> roles = Collections.singletonList(appName);
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                        .toList();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                System.out.println("auth : "+auth.getName());
                System.out.println("authorities : "+authorities);

                Mono<Void> m = chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));

                System.out.println("m : "+m.hasElement());

                return m;
            }
            catch(JwtException e){
                System.out.println("exception!!");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        return chain.filter(exchange);
    }
}
