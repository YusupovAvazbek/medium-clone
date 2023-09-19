package com.example.mediumclone.security;

import com.example.mediumclone.moduls.userservice.dto.UsersDto;
import com.example.mediumclone.moduls.userservice.repository.UsersRepository;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
public class JwtService {
    @Value("${spring.security.secret.key}")
    private String secretKey;
    @Autowired
    private Gson gson;

//    @Autowired
//    UsersRepository usersRepository;
    public String generateToken(UsersDto usersDto){
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*60*48))
                .setSubject(gson.toJson(usersDto))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }
    private String generateRefreshToken(UsersDto usersDto) {
        long refreshTokenExpirationMillis = 30L * 24L * 60L * 60L * 1000L;
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMillis))
                .setSubject(gson.toJson(usersDto))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return refreshToken;
    }
    public Claims claims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean isExpire(String token){
        return claims(token).getExpiration().getTime()<System.currentTimeMillis();
    }
    public UsersDto subject(String token){
        return gson.fromJson(claims(token).getSubject(), UsersDto.class);
    }
}
