package com.inghubs.creditmodule.controller;

import com.inghubs.creditmodule.security.CustomUserDetails;
import com.inghubs.creditmodule.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    public static class AuthRequest {
        public String username;
        public String password;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        try {
            // Bu satır: InMemoryUserDetailsManager içinde var mı kontrol eder
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username, request.password)
            );

            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            return jwtUtil.generateToken(userDetails);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }
}