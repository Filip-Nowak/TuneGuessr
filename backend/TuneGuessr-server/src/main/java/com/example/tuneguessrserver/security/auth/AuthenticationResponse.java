package com.example.tuneguessrserver.security.auth;

import com.example.tuneguessrserver.enums.AuthStatus;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class AuthenticationResponse {
    private String token;
    private int status;
    private String message;
    private List<AuthErrorModel> errors=new LinkedList<>();
    public void addError(AuthErrorModel error){
        errors.add(error);
    }
    public AuthenticationResponse(int status){
        this.status = status;
        this.message = AuthStatus.getMessage(status);

    }
    public AuthenticationResponse(List<Integer> errors){
        System.out.println("errors");
        System.out.println(errors);
        if(errors.size()==1){
            this.status = errors.getFirst();
            this.message = AuthStatus.getMessage(errors.getFirst());
        }else{
            this.status = AuthStatus.MULTIPLE_ERRORS;
            this.message = AuthStatus.getMessage(AuthStatus.MULTIPLE_ERRORS);
        }
        for (int error:errors){
            this.errors.add(new AuthErrorModel(error));
        }
        System.out.println(this.errors.size());
    }

}
