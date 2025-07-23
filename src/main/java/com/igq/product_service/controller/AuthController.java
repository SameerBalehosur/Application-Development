package com.igq.product_service.controller;

import com.igq.product_service.component.JwtUtil;
import com.igq.product_service.dto.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        // Normally you validate user against DB here.
        if ("admin".equals(authRequest.getUsername()) && "password".equals(authRequest.getPassword())) {
            String token = jwtUtil.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

