package com.example.tuneguessrserver.response.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiError extends RuntimeException{
    private final int status;
    private final String message;
    public ApiError(int status){
        this.status = status;
        this.message = ApiStatus.getMessage(status);
    }
}
