package com.cmc.jwtauthenticationdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/user")
    public String userAccess() {
        return "authorized - Chào User, bạn đã truy cập thành công!";
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "authorized - Chào Admin, bạn có quyền cao nhất!";
    }
}
