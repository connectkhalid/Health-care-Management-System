package com.khalid.hms.CommunityPortal.utilities.token;

import com.khalid.hms.CommunityPortal.utilities.constants.TokenConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class JWTUtils {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = TokenConstants.ALPHABETS;
    public static Boolean hasTokenExpired(String token){
        Claims claims = Jwts.parser().setSigningKey(TokenConstants.TOKEN_SECRET).parseClaimsJws(token).getBody();
        Date tokenExpirationDate = claims.getExpiration();
        Date today = new Date();
        return tokenExpirationDate.before(today);
    }

    public static String extractUser(String token) {
        return Jwts.parser().setSigningKey(TokenConstants.TOKEN_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public static List<String> extractUserRoles(String token) {
        Claims claims = Jwts.parser().setSigningKey(TokenConstants.TOKEN_SECRET).parseClaimsJws(token).getBody();
        return (List<String>) claims.get("roles");
    }
}
