package com.khalid.hms.PharmacyInventory.security;

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
                        .requestMatchers("/medicines/all-available-medicine").permitAll()
                        .requestMatchers("/medicines/add").hasAuthority("ADMIN")
                        .requestMatchers("/medicines/update").hasAuthority("ADMIN")
                        .requestMatchers("/medicines/delete/{medicineName}").hasAuthority("ADMIN")
                        .requestMatchers("/medicines/all-medicine").hasAuthority("ADMIN")
                        .requestMatchers("/medicines/medicine-by-id/{id}").hasAuthority("ADMIN")
                        .requestMatchers("/medicines/sort-medicine-by-type/{medicineType}").hasAnyAuthority("ADMIN", "DOCTOR", "PATIENT")
                        .anyRequest().authenticated()
                )
                .cors(Customizer.withDefaults())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
