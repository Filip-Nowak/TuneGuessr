package com.example.tuneguessrserver.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseModel {
    private String errorMessage;
    private Object data;
}
