package com.example.newBoard.service;

import com.example.newBoard.entity.User;
import com.example.newBoard.repository.UserRepository;
import com.example.newBoard.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, String> signup(Map<String, String> req) {
        String username = req.get("username");
        String rawPassword = req.get("password");
        if (username == null || rawPassword == null) {
            throw new IllegalArgumentException("username/password required");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("user exists");
        }
        User user = new User(username, passwordEncoder.encode(rawPassword));
        userRepository.save(user);
        return Map.of("message", "signup ok");
    }

    public Map<String, String> login(Map<String, String> req) {
        String username = req.get("username");
        String rawPassword = req.get("password");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("bad credentials");
        }

        String token = jwtUtil.generateToken(username);
        // 반환은 '순수 토큰'으로 (Bearer 접두어는 헤더에서 붙이기)
        return Map.of("token", token);
    }
}
