package io.seeyang.ppmtool.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.seeyang.ppmtool.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.seeyang.ppmtool.security.SecurityConstants.EXPIRATION_TIME;
import static io.seeyang.ppmtool.security.SecurityConstants.SECRET_JWT;

@Component
public class JWTTokenProvider {

    // Generate the Token - create a method
    public String generateToken(Authentication authentication) {

        // cast to the type User
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiredDate = new Date(now.getTime()+EXPIRATION_TIME);

        // grab the userID - token is a string so we need to cast the LONG into a STRING
        String userId = Long.toString(user.getId());

        // information of the user passed into the Token
        // claims = information about the user
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        // build the JSON Web Token
        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_JWT)
                .compact();
    }

    // Validate the Token

    // Get User ID from the Token

}
