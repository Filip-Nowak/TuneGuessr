package com.example.tuneguessrserver.controller;

import com.example.tuneguessrserver.entity.ResetPasswordToken;
import com.example.tuneguessrserver.enums.AuthStatus;
import com.example.tuneguessrserver.model.NewPasswordRequest;
import com.example.tuneguessrserver.model.PasswordResetRequest;
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
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody AuthenticationRequest request) {
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

    //password reset
    @PostMapping("/reset-password-email")
    public ResponseEntity<AuthenticationResponse> reset(@Valid @RequestBody PasswordResetRequest request) {
        try {
            authenticationService.resetPassword(request);
            return ResponseEntity.ok(new AuthenticationResponse(AuthStatus.EMAIL_SENT));
        } catch (AuthError e) {
            AuthenticationResponse response = new AuthenticationResponse(e.getErrors());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/check-token")
    public ResponseEntity<AuthenticationResponse> checkToken(@RequestParam String token) {
        try {
            authenticationService.checkToken(token);
            return ResponseEntity.ok(new AuthenticationResponse(AuthStatus.PASSWORD_RESET_TOKEN_CONFIRMED));
        } catch (AuthError e) {
            AuthenticationResponse response = new AuthenticationResponse(e.getErrors());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/new-password")
    public ResponseEntity<AuthenticationResponse> newPassword(@Valid @RequestBody NewPasswordRequest request, @RequestParam String token) {
        try {
            authenticationService.newPassword(request,token);
            return ResponseEntity.ok(new AuthenticationResponse(AuthStatus.PASSWORD_CHANGED));
        } catch (AuthError e) {
            AuthenticationResponse response = new AuthenticationResponse(e.getErrors());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AuthenticationResponse> resetPassword(@Valid @RequestBody NewPasswordRequest currentPassword, Authentication authentication) {
        try {
            ResetPasswordToken token=authenticationService.getTokenByPassword(currentPassword, authentication);
            AuthenticationResponse response = new AuthenticationResponse(AuthStatus.AUTHENTICATION_SUCCESS);
            response.setToken(token.getToken());
            return ResponseEntity.ok(response);
        } catch (AuthError e) {
            AuthenticationResponse response = new AuthenticationResponse(e.getErrors());
            return ResponseEntity.ok(response);
        }
    }


}
