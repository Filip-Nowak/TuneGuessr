package com.example.tuneguessrserver.controller;

import com.example.tuneguessrserver.entity.Challenge;
import com.example.tuneguessrserver.entity.Song;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.mapper.ChallengeMapper;
import com.example.tuneguessrserver.model.ChallengeModel;
import com.example.tuneguessrserver.model.ResponseModel;
import com.example.tuneguessrserver.model.SongModel;
import com.example.tuneguessrserver.model.challange.AddSongModel;
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
    @PostMapping("/challenge/{id}")
    public ResponseEntity<ResponseModel> addSongToChallenge(@RequestHeader(name = "Authorization")String header,@PathVariable long id, @RequestBody SongModel song){
        Challenge challenge=challengeService.getChallengeById(id);
        if(challenge==null)
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("Challenge not found")
                    .build());
        UserProfile profile = userService.getProfileByHeader(header);
        if(profile==null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if(challenge.getUser().getId()!=profile.getId())
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("You are not the author of this challenge")
                    .build());
        challenge=challengeService.addSongToChallenge(challenge,song);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(ChallengeMapper.toModel(challenge))
                .build());
    }
    @DeleteMapping("/challenge/{id}")
    public ResponseEntity<ResponseModel> deleteChallenge(@RequestHeader(name = "Authorization")String header, @PathVariable long id){
        Challenge challenge=challengeService.getChallengeById(id);
        if(challenge==null)
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("Challenge not found")
                    .build());
        UserProfile profile = userService.getProfileByHeader(header);
        if(profile==null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if(challenge.getUser().getId()!=profile.getId())
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("You are not the author of this challenge")
                    .build());
        challengeService.deleteChallenge(challenge);
        return ResponseEntity.ok(ResponseModel.builder()
                        .data("Challenge deleted")
                .build());
    }
    @PutMapping("/challenge/{id}/song/{songId}")
    public ResponseEntity<ResponseModel> updateSong(@RequestHeader(name = "Authorization")String header, @PathVariable long id, @PathVariable long songId, @RequestBody @Valid AddSongModel model) {
        Challenge challenge=challengeService.getChallengeById(id);
        if(challenge==null)
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("Challenge not found")
                    .build());
        UserProfile profile = userService.getProfileByHeader(header);
        if(profile==null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if(challenge.getUser().getId()!=profile.getId())
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("You are not the author of this challenge")
                    .build());
        if(challenge.getSongs().size()<songId)
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("Song not found")
                    .build());
        Song songEntity=challenge.getSongs().get((int) songId-1);
        songEntity.setArtist(model.getArtist());
        songEntity.setName(model.getTitle());
        songEntity.setUrl(model.getUrl());
        challengeService.updateSong(songEntity);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(ChallengeMapper.toModel(challenge))
                .build());
    }
    @DeleteMapping("/challenge/{id}/song/{songId}")
    public ResponseEntity<ResponseModel> deleteSong(@RequestHeader(name = "Authorization")String header, @PathVariable long id, @PathVariable long songId) {
        Challenge challenge=challengeService.getChallengeById(id);
        if(challenge==null)
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("Challenge not found")
                    .build());
        UserProfile profile = userService.getProfileByHeader(header);
        if(profile==null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if(challenge.getUser().getId()!=profile.getId())
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("You are not the author of this challenge")
                    .build());
        if(challenge.getSongs().size()<songId)
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage("Song not found")
                    .build());
        challenge.getSongs().remove((int) songId-1);
        challengeService.save(challenge);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(ChallengeMapper.toModel(challenge))
                .build());
    }


}
