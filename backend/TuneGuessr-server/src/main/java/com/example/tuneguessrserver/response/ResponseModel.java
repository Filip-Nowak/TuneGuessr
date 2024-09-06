package com.example.tuneguessrserver.response;

import com.example.tuneguessrserver.response.status.ErrorModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel implements ApiResponse{
    private Object data;
    private List<ErrorModel> errors=new LinkedList<>();
    public ResponseModel(List<Integer> errors){
        for (int error:errors){
            this.errors.add(new ErrorModel(error));
        }
    }
}
