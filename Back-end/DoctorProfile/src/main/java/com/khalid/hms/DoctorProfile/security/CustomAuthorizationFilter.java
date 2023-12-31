package com.khalid.hms.DoctorProfile.security;

import com.khalid.hms.DoctorProfile.constants.AppConstants;
import com.khalid.hms.DoctorProfile.exceptions.AuthenticationExceptionFound;
import com.khalid.hms.DoctorProfile.utilities.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AppConstants.HEADER_STRING);
        if (header == null || !header.startsWith(AppConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
        } else {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(header);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String header) {
        try {
            if (header != null) {
                String token = header.replace(AppConstants.TOKEN_PREFIX, "");
                String user = JWTUtils.hasTokenExpired(token) ? null : JWTUtils.extractUser(token);

                if (user != null) {
                    List<String> userRoles = JWTUtils.extractUserRoles(token);
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    for (String role : userRoles) {
                        authorities.add(new SimpleGrantedAuthority(role));
                    }
                    return new UsernamePasswordAuthenticationToken(user, null, authorities);
                }
            }
            return null;
        } catch (Exception e) {
            throw new AuthenticationExceptionFound(e.getMessage());
        }
    }
}