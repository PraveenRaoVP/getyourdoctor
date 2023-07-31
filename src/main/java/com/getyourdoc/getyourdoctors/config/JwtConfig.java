package com.getyourdoc.getyourdoctors.config;

import com.getyourdoc.getyourdoctors.models.Patient;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtConfig {
    private static final String SECRET_KEY="SAIDIMNEVERLACKINGALWAYSPISTOLPACKINGWITHTHEMAUTOMATICSWEGONSENDTHEMTOHEAVEN";

    public static String generateToken(Patient patient) {
        long expirationTimeMillis = System.currentTimeMillis() + 3600000;

        // Create the claims for the token (you can include any additional information here)
        Map<String, Object> claims = new HashMap<>();
        claims.put("patientId", patient.getPatientId());
        claims.put("email", patient.getPatientEmail());

        // Generate the token using the claims and the secret key
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(expirationTimeMillis))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return token;
    }
}
