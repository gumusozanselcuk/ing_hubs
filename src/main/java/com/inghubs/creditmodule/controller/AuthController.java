package com.inghubs.creditmodule.controller;

import com.inghubs.creditmodule.dto.AuthRequestDTO;
import com.inghubs.creditmodule.security.CustomUserDetails;
import com.inghubs.creditmodule.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller
 */
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * Authentication Manager
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Jwt Util
     */
    private final JwtUtil jwtUtil;

    /**
     * Constructor for AuthControler
     *
     * @param authenticationManager authentication manager
     * @param jwtUtil the service handling jwt operations
     */
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Prepare response message by data, error, message and http status
     *
     * @param authRequestDTO auth request that contains username and password
     * @return token
     */
    @PostMapping("/login")
    public String login(@RequestBody @Valid AuthRequestDTO authRequestDTO) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            return jwtUtil.generateToken(userDetails);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }
}