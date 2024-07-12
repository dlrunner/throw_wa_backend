//package com.project.throw_wa.jwt.provider;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//
//import java.security.Key;
//
//@Component
//public class JwtProvider {
//
//    @Value("${JWT.SECRET.KEY}")
//    private String jwtSecretKey;
//
//    // jwt 생성 메소드
//    public String create(String email) {
//
//        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
//        Key key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
//
//        String jwt = Jwts.builder()
//                .signWith(key, SignatureAlgorithm.HS256)
//                .setSubject(email).setIssuedAt(new Date()).setExpiration(expiredDate)
//                .compact();
//
//        return jwt;
//    }
//
//    // jwt 검증 메소드
//    public String validate(String jwt) {
//
//        String subject = null;
//        Key key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
//
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(jwt)
//                    .getBody();
//
//            subject = claims.getSubject();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return subject;
//    }
//}
