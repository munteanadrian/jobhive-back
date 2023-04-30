package tech.adrianmuntean.jobhive.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tech.adrianmuntean.jobhive.security.services.UserDetailsImpl;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jobhive.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${jobhive.app.jwtSecret}")
    private String jwtSecret;

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e);
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e);
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e);
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e);
        } catch (Exception e) {
            System.out.println("Token expired or something else: " + e);
        }

        return false;
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

//        getUsername returns email (correct)
        return Jwts.builder().setSubject((user.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
