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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseModel> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthenticationResponse response;
        try {
            response = authenticationService.register(request);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage(e.getMessage()).build());
        }
        return ResponseEntity.ok(ResponseModel.builder()
                .data(response)
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
                    .errorMessage("invalid credentials").build());
        }
        System.out.println(request);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(response)
                .build());

    }
}
