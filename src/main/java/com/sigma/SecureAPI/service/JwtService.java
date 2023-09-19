package com.sigma.SecureAPI.service;

import com.sigma.SecureAPI.repository.UserRepository;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    private final static String secretKey = "5bZXYAImp8/UhunoVYWcn+w/o4btgTXvlcQeCSzVE2ufknHZUWnq5pHQvoBwN0fH";


    // Validate Token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final  String username = extractUsername(token);
        boolean isValid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        System.out.println("Token is valid" + isValid);
        return isValid;
    }

    private boolean isTokenExpired(String token) {
        final Date expireDate =  extractClaim(token, Claims::getExpiration);
        return expireDate.before(new Date());
    }


    // Extract username from JWT Token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Generate token based on claims (payload) and Username
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Used Algo for encryption
                .compact(); // compact to String
    }

    // Extract a single claim from token and applied method
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // 1 - Extract All claims from token
        final Claims claims = extractAllClaims(token);
        // 2 - return the extracted claim according to its type
        return claimsResolver.apply(claims);
    }

    // Extract Claims from Token (Payload)
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Generate the secret key that will be used for encryption
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
