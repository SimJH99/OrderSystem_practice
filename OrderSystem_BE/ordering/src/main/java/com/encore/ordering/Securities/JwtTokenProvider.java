package com.encore.ordering.Securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int expiration;


    public String createToken(String email, String role) {  //매개변수는 페이로드에 담을 데이터
//        claims : 클레임은 토큰 사용자에 대한 속성이나 데이터포함. 주로 페이로드를 의미.
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(claims);
        jwtBuilder.setIssuedAt(now);
        jwtBuilder.setExpiration(new Date(now.getTime()+expiration*60*1000L));
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        return jwtBuilder.compact();
    }
}
