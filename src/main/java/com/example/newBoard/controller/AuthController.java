package com.example.newBoard.controller;

import com.example.newBoard.entity.User;
import com.example.newBoard.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        userService.register(username, password);

        Map<String, String> response = new HashMap<>();
        response.put("message", "회원가입 성공");
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        boolean valid = userService.validateUser(username, password);

        Map<String, String> response = new HashMap<>();
        if (valid) {
            response.put("message", "로그인 성공");
            // 🔑 다음 단계에서 JWT 토큰 추가 예정
        } else {
            response.put("message", "로그인 실패");
        }
        return response;
    }
}
