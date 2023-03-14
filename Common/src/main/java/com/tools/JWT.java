package com.tools;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JWT {
    private final static String salt = "MusicWebSalt";
    public static final long EXPIRE = 1000 * 60 * 30;

    public static String creat(Map<String, Object> claims) {
        JwtBuilder jwtBuilder = Jwts.builder()
                //设置header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //设置签发者
                .setSubject("MusicWeb")
                //设置签发时间和过期时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //设置加密内容
                .setClaims(claims)
                //设置加密方式和密钥
                .signWith(SignatureAlgorithm.HS256, salt);
        return jwtBuilder.compact();
    }
    public static String creat(String key,Object value) {
        Map<String,Object> claims = new HashMap<>();
        claims.put(key,value);
        JwtBuilder jwtBuilder = Jwts.builder()
                //设置header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //设置签发者
                .setSubject("MusicWeb")
                //设置签发时间和过期时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //设置加密内容
                .setClaims(claims)
                //设置加密方式和密钥
                .signWith(SignatureAlgorithm.HS256, salt);
        return jwtBuilder.compact();
    }

    public static boolean validation(String token) {
        if(token == null) return false;
        if (!StringUtils.hasText(token)) return false;
        try {
            Jwts.parser().setSigningKey(salt).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static Claims parseBody(String token) {
        if(token == null) return null;
        if (!StringUtils.hasText(token)) return null;
        Claims claims = Jwts.parser()
                .setSigningKey(salt)
                .parseClaimsJws(token).getBody();
        return claims;
    }
    public static JwsHeader parseHeader(String token){
        if(token == null) return null;
        if (!StringUtils.hasText(token)) return null;
        JwsHeader header = Jwts.parser()
                .setSigningKey(salt)
                .parseClaimsJws(token).getHeader();
        return header;
    }
}