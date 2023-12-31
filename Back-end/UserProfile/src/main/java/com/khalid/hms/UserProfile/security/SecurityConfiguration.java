package com.khalid.hms.UserProfile.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/patient/register", "/patient/login").permitAll()
                        .requestMatchers("/patient/create-profile").hasAuthority("PATIENT")
                        .requestMatchers("/patient/get-patient-profile-by-token").hasAuthority("PATIENT")
                        .requestMatchers("/patient/get-patient-profile-by-id").hasAnyAuthority("PATIENT", "DOCTOR", "ADMIN")
                        .requestMatchers("/patient/update-patient-profile").hasAuthority("PATIENT")
                        .requestMatchers("/patient/health-data").hasAuthority("PATIENT")
                        .requestMatchers("/patient/create-health-data").hasAuthority("PATIENT")
                        .requestMatchers("/patient/get-health-data-by-token").hasAuthority("PATIENT")
                        .requestMatchers("/patient/update-patient-health-data").hasAuthority("PATIENT")
                        .requestMatchers("/patient/get-all-health-data").permitAll()
                        .requestMatchers("/patient/get-patient-by-email/{email}").hasAuthority("PATIENT")
                        .anyRequest().authenticated()
                )
                .cors(Customizer.withDefaults())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new CustomAuthenticationFilter(authenticationManager))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}