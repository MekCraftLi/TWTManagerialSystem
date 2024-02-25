package com.example.demo4.utils;

import com.example.demo4.Service.UserService;
import com.example.demo4.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    //key的大小必须大于或等于256bit,需要32位英文字符，一个英文字符为：8bit,一个中文字符为12bit
    @Value("${jwt.secret-key:19260817192608171926081719260817}")
    private String SECRET_KEY;
    //设置过期时间，单位毫秒
    @Value("${jwt.expiration-time:604800000}")
    private Long EXPIRATION_TIME;
    //设置加密算法
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    //token缓存
    public static ConcurrentMap<String, Date> tokenMap = new ConcurrentHashMap<>();

    //获取转换后的私钥对象
    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 生成JWT令牌
    public String generateToken(User user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", user.getId());
        payload.put("username", user.getUserName());
        payload.put("password", user.getPassword());
        payload.put("role", user.getAuthority());
        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey(), signatureAlgorithm)
                .compact();
    }

    // 验证JWT令牌
    public boolean validateToken(UserService userService, String token) {
        Long id = (Long) extractFromPayload(token, "id");
        String password = (String) extractFromPayload(token, "password");
        return password.equals(userService.getUserById(id).getPassword()) && isNotTokenExpired(token);
    }

    // 只验证JWT令牌有没有过期
    public boolean simpleValidateToken(String token) {
        return isNotTokenExpired(token);
    }

    // 从JWT令牌中提取payload
    public Claims getPayload(String token) {
        Claims payload;
        try {
            payload = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            payload = e.getClaims();
        }
        return payload;
    }

    // 从JWT令牌中提取payload属性
    public Object extractFromPayload(String token, String key) {
        return getPayload(token).get(key);
    }

    // 检查JWT令牌是否过期
    public boolean isNotTokenExpired(String token) {
        return tokenMap.containsKey(token) && !getPayload(token).getExpiration().before(new Date());
    }

}