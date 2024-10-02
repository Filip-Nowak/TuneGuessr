package com.example.tuneguessrserver.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers(HttpMethod.POST, "api/auth/reset-password")
//                .authenticated()
//                .requestMatchers("api/auth/**")
//                .permitAll()
//                .requestMatchers(HttpMethod.GET, "api/challenge/**")
//                .permitAll()
//                .requestMatchers(HttpMethod.GET, "api/user/{nickname}")
//                .permitAll()
//                .requestMatchers(HttpMethod.GET, "api/user")
//                .authenticated()
//                .requestMatchers("api/test/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                .csrf(
                        csrf->{
                            csrf.disable();
                        }
                )
                .authorizeHttpRequests(
                        auth->{
                            auth.requestMatchers(HttpMethod.POST, "api/auth/reset-password").authenticated();
                            auth.requestMatchers("api/auth/**").permitAll();
                            auth.requestMatchers(HttpMethod.GET, "api/challenge/**").permitAll();
                            auth.requestMatchers(HttpMethod.GET, "api/user/{nickname}").permitAll();
                            auth.requestMatchers(HttpMethod.GET, "api/user").authenticated();
                            auth.requestMatchers("api/test/**").permitAll();
                            auth.requestMatchers("ws/**").permitAll();
                            auth.requestMatchers("create-user").permitAll();
                            auth.anyRequest().authenticated();
                        }
                )
                .sessionManagement(
                        session->{
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                        }
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
