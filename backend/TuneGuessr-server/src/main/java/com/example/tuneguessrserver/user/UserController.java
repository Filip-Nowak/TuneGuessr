package com.example.tuneguessrserver.user;

import com.example.tuneguessrserver.response.ResponseModel;
import com.example.tuneguessrserver.response.status.ApiError;
import com.example.tuneguessrserver.response.status.ErrorModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    private UserService userService;
    @GetMapping("/user/{nickname}")
    public ResponseEntity<ResponseModel> getUser(@PathVariable String nickname){
        try {
            UserModel user = userService.parseUserToModel(userService.getProfileByNickname(nickname));
            return ResponseEntity.ok(ResponseModel.builder()
                    .data(user)
                    .build());
        } catch (ApiError e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errors(List.of(new ErrorModel(e.getStatus())))
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
        } catch (ApiError e) {
            return ResponseEntity.ok(ResponseModel.builder()
                            .errors(List.of(new ErrorModel(e.getStatus())))
                    .build());
        }
    }
}
