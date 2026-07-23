package com.rambabu.ai.controller;

import com.rambabu.ai.dto.LoginRequest;
import com.rambabu.ai.dto.LoginResponse;
import com.rambabu.ai.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        ));
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();
        String token =
                jwtService.generateToken( userDetails);

        return new LoginResponse(token);
    }
}