package com.example.tuneguessrserver.response.status;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize
@Data
public class ErrorModel {
    private final int status;
    private final String message;
    public ErrorModel(int status) {
        this.status = status;
        if(status<100)
            this.message = AuthStatus.getMessage(status);
        else
            this.message = ApiStatus.getMessage(status);

    }
}
