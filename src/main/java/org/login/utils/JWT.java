package org.login.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

public class JWT {
    private static final String SECRET_KEY = "Wrong Password";

    public static String createJWT(String userName) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuer("Saurabh Chauhan")
                .setIssuedAt(now)
                .setSubject("User Token")
                .claim("UserName", userName)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public static boolean verifyUserToken(String jwtToken, String userName) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwtToken)
                .getBody()
                .get("UserName")
                .equals(userName);
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody()
                .get("UserName")
                .toString();

    }
}
