package com.example.tuneguessrserver.controller;

import com.example.tuneguessrserver.model.ResponseModel;
import com.example.tuneguessrserver.security.auth.AuthenticationRequest;
import com.example.tuneguessrserver.security.auth.AuthenticationResponse;
import com.example.tuneguessrserver.security.auth.AuthenticationService;
import com.example.tuneguessrserver.security.auth.RegisterRequest;
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
    public ResponseEntity<ResponseModel> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        System.out.println("start");
        try {
            authenticationService.register(request);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage(e.getMessage()).build());
        }
        System.out.println("registering");
        return ResponseEntity.ok(ResponseModel.builder()
                .data("email sent")
                .build());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseModel> register(
            @RequestBody AuthenticationRequest request
    ) {
        System.out.println("authenticating");
        AuthenticationResponse response;
        try{
            response=authenticationService.authenticate(request);
        }catch (RuntimeException e){
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage(e.getMessage()).build());
        }
        System.out.println(request);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(response)
                .build());

    }

    @GetMapping("/confirm")
    public ResponseEntity<ResponseModel> confirm(
            @RequestParam String token
    ) {
        try {
            authenticationService.confirmToken(token);

        } catch (RuntimeException e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage(e.getMessage()).build());
        }
        return ResponseEntity.ok(ResponseModel.builder()
                .data("confirmed")
                .build());
    }
}
