package com.sigma.SecureAPI.controller;

import com.sigma.SecureAPI.dto.AuthenticationRequest;
import com.sigma.SecureAPI.dto.AuthenticationResponse;
import com.sigma.SecureAPI.dto.RegisterRequest;
import com.sigma.SecureAPI.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.RectangularShape;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody AuthenticationRequest request) {
        System.out.println("in /authenticate !!");
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
