package com.example.tuneguessrserver.controller;

import com.example.tuneguessrserver.entity.User;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.model.ResponseModel;
import com.example.tuneguessrserver.model.UserModel;
import com.example.tuneguessrserver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private UserService userService;
    @GetMapping("/user/{nickname}")
    public ResponseEntity<ResponseModel> getUser(@PathVariable String nickname){
        try {
            UserModel user = userService.parseUserToModel(userService.getProfileByNickname(nickname));
            return ResponseEntity.ok(ResponseModel.builder()
                    .data(user)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage(e.getMessage())
                    .build());
        }
    }
    @GetMapping("/user")
    public ResponseEntity<ResponseModel> getLoggedUser(@RequestHeader("Authorization") String header){
        try {
            UserProfile user = userService.getProfileByHeader(header);
            UserModel userModel = userService.parseUserToModel(userService.getProfileByNickname(user.getNickname()));
            return ResponseEntity.ok(ResponseModel.builder()
                    .data(userModel)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage(e.getMessage())
                    .build());
        }
    }
}
