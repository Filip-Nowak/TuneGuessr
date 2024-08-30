package com.example.tuneguessrserver.response;

import com.example.tuneguessrserver.response.status.ErrorModel;

import java.util.List;

public interface ApiResponse {
    List<ErrorModel> getErrors();
    void setErrors(List<ErrorModel> error);

}
