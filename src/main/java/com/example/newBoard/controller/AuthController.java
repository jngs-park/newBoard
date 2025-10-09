package com.example.newBoard.controller;

import com.example.newBoard.service.UserService;
import com.example.newBoard.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Article API", description = "게시글 CRUD 및 Kafka 전송 관련 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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
            String token = jwtUtil.generateToken(username);
            response.put("message", "로그인 성공");
            response.put("token", token); // ✅ JWT 토큰 발급
        } else {
            response.put("message", "로그인 실패");
        }
        return response;
    }
}
