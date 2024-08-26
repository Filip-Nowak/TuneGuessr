package com.example.tuneguessrserver.security.auth;

import com.example.tuneguessrserver.enums.AuthStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class AuthError extends RuntimeException{
    private final List<Integer> errors;
    public void addError(int error){
        errors.add(error);
    }
}
