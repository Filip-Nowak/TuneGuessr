package com.example.tuneguessrserver.controller;

import com.example.tuneguessrserver.model.ChallengeModel;
import com.example.tuneguessrserver.service.ChallengeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ChallengeController {
    ChallengeService challengeService;
    @GetMapping("/challenge/{name}")
    public ResponseEntity<ChallengeModel>getChallenge(@PathVariable String name){
        ChallengeModel challengeModel=challengeService.getChallengeByName(name);
        if(challengeModel!=null)
            return new ResponseEntity<>(challengeModel, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("challenge/search/{name}")
    public ResponseEntity<List<ChallengeModel>> searchChallenge(@PathVariable(required = false) String name){
        List<ChallengeModel> models = challengeService.searchChallengesByName(name);
        if(models!=null)
            return new ResponseEntity<>(models, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
