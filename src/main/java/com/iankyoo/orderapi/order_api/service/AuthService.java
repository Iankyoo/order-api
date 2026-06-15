package com.iankyoo.orderapi.order_api.service;

import com.iankyoo.orderapi.order_api.dto.AuthResponse;
import com.iankyoo.orderapi.order_api.dto.LoginRequest;
import com.iankyoo.orderapi.order_api.dto.RegisterRequest;
import com.iankyoo.orderapi.order_api.entity.Role;
import com.iankyoo.orderapi.order_api.entity.User;
import com.iankyoo.orderapi.order_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    public AuthResponse registerUser(RegisterRequest request){
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        User saved = repository.save(user);
        String token = jwtService.generateToken(saved);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request){
        User user = repository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
