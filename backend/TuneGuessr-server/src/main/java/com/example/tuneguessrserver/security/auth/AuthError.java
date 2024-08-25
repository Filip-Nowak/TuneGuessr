package com.example.tuneguessrserver.security.auth;

import com.example.tuneguessrserver.enums.AuthStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class AuthError extends Exception{
    private final List<Integer> errors;
}
