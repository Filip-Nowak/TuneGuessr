package com.example.tuneguessrserver.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@CrossOrigin
public class ExceptionHandler extends ResponseEntityExceptionHandler{
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        List<Integer> errorCodes = new LinkedList<>();
        try{
            for (String error : errors) {
                errorCodes.add(Integer.parseInt(error));
            }
        } catch (NumberFormatException e) {
            errorCodes=null;
        }
        if(errorCodes==null){
            return ResponseEntity.ok(
                    new AuthenticationResponse(0)
            );
        }
        AuthenticationResponse response = new AuthenticationResponse(errorCodes);
        return ResponseEntity.ok(
                response
        );
    }
}
