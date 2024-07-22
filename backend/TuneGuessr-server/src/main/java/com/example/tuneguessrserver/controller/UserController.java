package com.example.tuneguessrserver.controller;

import com.example.tuneguessrserver.model.UserModel;
import com.example.tuneguessrserver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    private UserService userService;
    @GetMapping("/user/{nickname}")
    public ResponseEntity<UserModel> getUser(@PathVariable String nickname){
        //UserModel userModel=userService.getProfileByNickname(nickname);
        //if(userModel==null)
          //  return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        //return new ResponseEntity<>(userModel,HttpStatus.OK);
        return null;
    }

    @GetMapping("/user/search/{nickname}")
    public ResponseEntity<List<UserModel>> searchUsers(@PathVariable String nickname){
        List<UserModel> userModels= userService.searchUsers(nickname);
        if(userModels==null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userModels,HttpStatus.OK);
    }
}
