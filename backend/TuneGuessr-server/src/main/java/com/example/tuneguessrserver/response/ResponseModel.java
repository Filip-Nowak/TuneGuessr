package com.example.tuneguessrserver.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseModel {
    private String errorMessage;
    private Object data;
}
