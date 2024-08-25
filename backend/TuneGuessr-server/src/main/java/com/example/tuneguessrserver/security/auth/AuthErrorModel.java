package com.example.tuneguessrserver.security.auth;

import com.example.tuneguessrserver.enums.AuthStatus;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize
@Data
public class AuthErrorModel {
    private final int status;
    private final String message;
    public AuthErrorModel(int status) {
        this.status = status;
        this.message = AuthStatus.getMessage(status);

    }
}
