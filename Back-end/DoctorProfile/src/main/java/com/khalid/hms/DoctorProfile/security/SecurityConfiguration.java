package com.khalid.hms.DoctorProfile.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        .requestMatchers(HttpMethod.POST,"/doctor/register", "/doctor/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/doctor/verify").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/doctor/disable-doctor/{doctorId}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/doctor/change-availability-status/{doctorId}").hasAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.PUT,"/doctor/update").hasAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.GET,"/doctor/get-doctor-profile-by-token").hasAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.GET,"/doctor/get-doctor-profile-by-id/{doctorId}").hasAnyAuthority("ADMIN", "PATIENT")
                        .requestMatchers(HttpMethod.GET,"/doctor/get-by-email/{email}").hasAnyAuthority("ADMIN", "PATIENT")
                        .requestMatchers(HttpMethod.GET,"/doctor/get-all-doctor").hasAnyAuthority("ADMIN", "PATIENT")
                        .requestMatchers(HttpMethod.GET,"/doctor/get-all-available-doctor").hasAnyAuthority("ADMIN", "PATIENT")
                        .requestMatchers(HttpMethod.GET,"/doctor/get-all-doctor-by-department/{department}").hasAnyAuthority("ADMIN", "PATIENT")
                        .requestMatchers(HttpMethod.POST,"/doctor/create-slots").hasAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.GET,"/doctor/get-all-available-slot-doctor/{doctorId}").hasAnyAuthority("DOCTOR","ADMIN", "PATIENT")
                        .requestMatchers(HttpMethod.GET,"/get-current-slot-doctor/{doctorId}").hasAnyAuthority("DOCTOR","ADMIN", "PATIENT")
                        .requestMatchers(HttpMethod.GET,"/doctor/get-available-slot-by-date/{doctorId}/{slotDate}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/doctor/get-all-reviews-doctor/{doctorId}").permitAll()
                        .requestMatchers(HttpMethod.POST,"/doctor/book-appointment-slot/{slotId}").hasAuthority("PATIENT")
                        .requestMatchers(HttpMethod.POST,"/doctor/create-doctor-review").hasAuthority("PATIENT")
                        .requestMatchers(HttpMethod.GET,"/doctor/review-doctor/{doctorId}").hasAnyAuthority("PATIENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST,"/appointment/create-appointment").hasAuthority("PATIENT")
                        .requestMatchers(HttpMethod.POST,"/appointment/cancel/{appointmentId}").hasAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.POST,"/appointment/complete/{appointmentId}").hasAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.GET,"/appointment/get-current-appointments-patient").hasAuthority("PATIENT")
                        .requestMatchers(HttpMethod.GET,"/appointment/get-upcoming-appointments-patient").hasAuthority("PATIENT")
                        .requestMatchers(HttpMethod.GET,"/appointment/patient-appointment-history").hasAnyAuthority("PATIENT")
                        .requestMatchers(HttpMethod.GET, "/appointment/get-appointment-by-appointmentId/{appointmentId}").hasAnyAuthority("PATIENT", "ADMIN", "DOCTOR" )
                        .requestMatchers(HttpMethod.POST,"/appointment/complete/{appointmentId}").hasAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.GET,"/appointment/get-current-appointments-doctor").hasAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.GET,"/appointment/get-upcoming-appointments-doctor").hasAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.GET,"/appointment/doctor-appointment-history").hasAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.GET,"/appointment/all-completed-appointment-patient").hasAuthority("PATIENT")
                        .requestMatchers(HttpMethod.GET,"/appointment/all-completed-appointment-doctor").hasAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.GET,"/appointment/all-cancelled-appointment-patient").hasAuthority("PATIENT")
                        .requestMatchers(HttpMethod.GET,"/appointment/all-cancelled-appointment-doctor").hasAuthority("DOCTOR")
                        .anyRequest().authenticated()
                )
                .cors(Customizer.withDefaults())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new CustomAuthenticationFilter(authenticationManager))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}