package com.khalid.hms.DoctorProfile.utilities;

import com.khalid.hms.DoctorProfile.constants.AppConstants;
import com.khalid.hms.DoctorProfile.exceptions.AuthenticationExceptionFound;
import com.khalid.hms.DoctorProfile.exceptions.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class JWTUtils {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static Boolean hasTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(AppConstants.TOKEN_SECRET).parseClaimsJws(token).getBody();
            Date tokenExpirationDate = claims.getExpiration();
            Date today = new Date();
            return tokenExpirationDate.before(today);
        } catch (Exception e) {
            throw new AuthenticationExceptionFound("You are not authenticated");
        }
    }

    public static String generateToken(String id, List<String> roles) {
        return Jwts.builder()
                .setSubject(id)
                .claim("roles", roles)
                .setExpiration(new Date(System.currentTimeMillis() + AppConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, AppConstants.TOKEN_SECRET)
                .compact();
    }

    public static String generateUserID(int length) {
        return generateRandomString(length);
    }

    private static String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        return new String(returnValue);
    }

    public static String extractUser(String token) {
        try {
            return Jwts.parser().setSigningKey(AppConstants.TOKEN_SECRET).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            throw new CustomException("Error parsing or validating the token: " + e.getMessage());
        }
    }

    public static List<String> extractUserRoles(String token) {
        Claims claims = Jwts.parser().setSigningKey(AppConstants.TOKEN_SECRET).parseClaimsJws(token).getBody();
        return (List<String>) claims.get("roles");
    }
}

