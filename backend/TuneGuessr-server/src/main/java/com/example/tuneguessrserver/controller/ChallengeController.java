package com.example.tuneguessrserver.controller;

import com.example.tuneguessrserver.entity.Challenge;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.mapper.ChallengeMapper;
import com.example.tuneguessrserver.model.ChallengeModel;
import com.example.tuneguessrserver.model.ResponseModel;
import com.example.tuneguessrserver.model.challange.CreateChallengeModel;
import com.example.tuneguessrserver.model.challange.SearchResultModel;
import com.example.tuneguessrserver.service.ChallengeService;
import com.example.tuneguessrserver.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChallengeController {
    private final ChallengeService challengeService;
    private final UserService userService;

    @GetMapping("/challenge/{id}")
    public ResponseEntity<ResponseModel> getChallenge(@PathVariable long id){
        Challenge challenge=challengeService.getChallengeById(id);
        if(challenge==null)
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("Challenge not found")
                    .build());
        ChallengeModel model= ChallengeMapper.toModel(challenge);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(model)
                .build());
    }
    @GetMapping("/challenge/search/{name}")
    public ResponseEntity<ResponseModel> searchChallenge(@PathVariable String name){
        List<SearchResultModel> challenges=challengeService.searchChallengesByName(name);
        if(challenges==null)
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("Challenge not found")
                    .build());
        return ResponseEntity.ok(ResponseModel.builder()
                .data(challenges)
                .build());
    }
    @PostMapping("/challenge")
    public ResponseEntity<ResponseModel> createChallenge(@RequestHeader(name = "Authorization")String header, @RequestBody @Valid CreateChallengeModel challengeModel){
        UserProfile profile = userService.getProfileByHeader(header);
        if(profile==null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Challenge challenge=challengeService.createChallenge(challengeModel,profile);
        ChallengeModel model= ChallengeMapper.toModel(challenge);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(model)
                .build());
    }
}
