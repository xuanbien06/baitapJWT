package com.cmc.jwtauthenticationdemo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {
    // Chuỗi bí mật để mã hóa (Phải dài tối thiểu 256-bit)
    private final String SECRET_KEY = "DayLaChuoiBiMatCuaToiDungDeMaHoaTokenJWTNhe123456";
    private final long EXPIRATION_TIME = 86400000; // Token sống trong 1 ngày (ms)

    // Hàm tạo Token
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // Nhét quyền (role) vào trong token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Hàm kiểm tra Token có hợp lệ không
    public Claims validateToken(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null; // Token sai hoặc hết hạn
        }
    }
}