package com.example.tuneguessrserver.controller;

import com.example.tuneguessrserver.enums.AuthStatus;
import com.example.tuneguessrserver.model.ResponseModel;
import com.example.tuneguessrserver.security.auth.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        try{
            authenticationService.register(request);
            return ResponseEntity.ok(new AuthenticationResponse(AuthStatus.EMAIL_SENT));
        }catch (AuthError e){
            System.out.println("handled");
            AuthenticationResponse response = new AuthenticationResponse(e.getErrors());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        try {
            String token = authenticationService.authenticate(request);
            AuthenticationResponse response = new AuthenticationResponse(AuthStatus.AUTHENTICATION_SUCCESS);
            response.setToken(token);
            return ResponseEntity.ok(response);
        } catch (AuthError e) {
            AuthenticationResponse response = new AuthenticationResponse(e.getErrors());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<AuthenticationResponse> confirm(@RequestParam String token) {
        try {
            authenticationService.confirmToken(token);
            return ResponseEntity.ok(new AuthenticationResponse(AuthStatus.EMAIL_CONFIRMED));
        } catch (AuthError e) {
            AuthenticationResponse response = new AuthenticationResponse(e.getErrors());
            return ResponseEntity.ok(response);
        }
    }
}
