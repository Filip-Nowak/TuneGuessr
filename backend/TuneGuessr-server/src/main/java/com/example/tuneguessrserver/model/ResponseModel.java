package com.example.tuneguessrserver.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseModel {
    private boolean error;
    private String message;
    private Object data;
}
