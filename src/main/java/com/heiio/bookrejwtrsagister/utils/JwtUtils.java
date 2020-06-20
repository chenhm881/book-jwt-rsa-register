package com.heiio.bookrejwtrsagister.utils;


import com.google.gson.Gson;
import com.heiio.bookrejwtrsagister.domain.Payload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;


public class JwtUtils {

    private static final String JWT_PAYLOAD_USER_KEY = "user";


    public static String generateTokenExpireInMinutes(Object userInfo, PrivateKey privateKey, int expire) {
        ZonedDateTime zonedDateTime = LocalDateTime.now().plusMinutes(expire).atZone(ZoneId.systemDefault());
                return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, new Gson().toJson(userInfo))
                .setId(createJTI())
                .setExpiration(Date.from(zonedDateTime.toInstant()))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }


    public static String generateTokenExpireInSeconds(Object userInfo, PrivateKey privateKey, int expire) {
        ZonedDateTime zonedDateTime = LocalDateTime.now().plusMinutes(expire).atZone(ZoneId.systemDefault());
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, new Gson().toJson(userInfo))
                .setId(createJTI())
                .setExpiration(Date.from(zonedDateTime.toInstant()))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }


    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        //return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
    }

    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }


    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey, Class<T> userType) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setUserInfo(new Gson().fromJson(body.get(JWT_PAYLOAD_USER_KEY).toString(), userType));
        claims.setExpiration(body.getExpiration());
        return claims;
    }


    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }
}
