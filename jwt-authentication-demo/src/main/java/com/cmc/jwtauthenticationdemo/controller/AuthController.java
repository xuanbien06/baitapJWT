package com.cmc.jwtauthenticationdemo.controller;

import com.cmc.jwtauthenticationdemo.model.AuthRequest;
import com.cmc.jwtauthenticationdemo.model.AuthResponse;
import com.cmc.jwtauthenticationdemo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // GIẢ LẬP ĐĂNG NHẬP (Trong thực tế sẽ check với Database)
        if ("admin".equals(request.getUsername()) && "123".equals(request.getPassword())) {
            // Nếu đúng tài khoản admin, tạo token có quyền ROLE_ADMIN
            String token = jwtUtils.generateToken("admin", "ROLE_ADMIN");
            return ResponseEntity.ok(new AuthResponse(token));

        } else if ("user".equals(request.getUsername()) && "123".equals(request.getPassword())) {
            // Nếu đúng tài khoản user, tạo token có quyền ROLE_USER
            String token = jwtUtils.generateToken("user", "ROLE_USER");
            return ResponseEntity.ok(new AuthResponse(token));
        }

        // Sai tài khoản/mật khẩu
        return ResponseEntity.status(401).body("Tài khoản hoặc mật khẩu không đúng!");
    }
}