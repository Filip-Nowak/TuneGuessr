package com.example.tuneguessrserver.controller;

import com.example.tuneguessrserver.entity.Challenge;
import com.example.tuneguessrserver.entity.Song;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.mapper.ChallengeMapper;
import com.example.tuneguessrserver.model.ModelConverter;
import com.example.tuneguessrserver.model.challange.ChallengeModel;
import com.example.tuneguessrserver.model.ResponseModel;
import com.example.tuneguessrserver.model.challange.AddSongModel;
import com.example.tuneguessrserver.model.challange.CreateChallengeModel;
import com.example.tuneguessrserver.model.challange.SearchResultModel;
import com.example.tuneguessrserver.service.ChallengeService;
import com.example.tuneguessrserver.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class ChallengeController {
    private final ChallengeService challengeService;
    private final UserService userService;

    @GetMapping("/challenge/{id}")
    public ResponseEntity<ResponseModel> getChallenge(@PathVariable long id, @RequestParam(required = false) List< String> show) {
        Challenge challenge;
        try {
            challenge = challengeService.getChallengeById(id);

        } catch (Exception e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage(e.getMessage())
                    .build());
        }
        Map<String, Object> model = ModelConverter.convertChallengeToMap(show,challenge);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(model)
                .build());
    }

    @GetMapping("/challenge/search/{name}")
    public ResponseEntity<ResponseModel> searchChallenge(@PathVariable String name,@RequestParam(required = false) List<String> show){
        List<Challenge> challenges = challengeService.searchChallengesByName(name);
        if(show==null || show.isEmpty()){
            List<ChallengeModel> models = ChallengeMapper.toModel(challenges);
            return ResponseEntity.ok(ResponseModel.builder()
                    .data(models)
                    .build());
        }
        List<Map<String, Object>> models = ModelConverter.convertChallengesToMap(show,challenges);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(models)
                .build());
    }

    @PostMapping("/challenge")
    public ResponseEntity<ResponseModel> createChallenge(@RequestHeader(name = "Authorization") String header, @RequestBody @Valid CreateChallengeModel challengeModel) {
        UserProfile profile = userService.getProfileByHeader(header);
        if (profile == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Challenge challenge = challengeService.createChallenge(challengeModel, profile);
        ChallengeModel model = ChallengeMapper.toModel(challenge);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(model)
                .build());
    }

    @PostMapping("/challenge/{id}/song")
    public ResponseEntity<ResponseModel> addSongToChallenge(@RequestHeader(name = "Authorization") String header, @PathVariable long id, @RequestBody @Valid AddSongModel song) {
        Challenge challenge;
        try {
            challenge = challengeService.getChallengeById(id);
            UserProfile profile = userService.getProfileByHeader(header);
            challengeService.checkIsChallengeOwner(challenge, profile);

        } catch (Exception e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage(e.getMessage())
                    .build());
        }

        challenge = challengeService.addSongToChallenge(challenge, song);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(ChallengeMapper.toModel(challenge))
                .build());
    }

    @DeleteMapping("/challenge/{id}")
    public ResponseEntity<ResponseModel> deleteChallenge(@RequestHeader(name = "Authorization") String header, @PathVariable long id) {
        Challenge challenge;
        try {
            challenge = challengeService.getChallengeById(id);
            UserProfile profile = userService.getProfileByHeader(header);
            challengeService.checkIsChallengeOwner(challenge, profile);

        } catch (Exception e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage(e.getMessage())
                    .build());
        }
        challengeService.deleteChallenge(challenge);
        return ResponseEntity.ok(ResponseModel.builder()
                .data("Challenge deleted")
                .build());
    }

    @PutMapping("/challenge/{id}/song/{songId}")
    public ResponseEntity<ResponseModel> updateSong(@RequestHeader(name = "Authorization") String header, @PathVariable long id, @PathVariable long songId, @RequestBody @Valid AddSongModel model) {
        Challenge challenge;
        Song song;
        try {
            challenge = challengeService.getChallengeById(id);
            UserProfile profile = userService.getProfileByHeader(header);
            challengeService.checkIsChallengeOwner(challenge, profile);
            song = challengeService.getSongByNumber(challenge, songId);
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage(e.getMessage())
                    .build());
        }
        song.setName(model.getTitle());
        song.setUrl(model.getUrl());
        challengeService.updateSong(song);
        return ResponseEntity.ok(ResponseModel.builder()
                .data(ChallengeMapper.toModel(challenge))
                .build());
    }

    @DeleteMapping("/challenge/{id}/song/{songId}")
    public ResponseEntity<ResponseModel> deleteSong(@RequestHeader(name = "Authorization") String header, @PathVariable long id, @PathVariable long songId) {
        Challenge challenge;
        try {
            challenge = challengeService.getChallengeById(id);
            UserProfile profile = userService.getProfileByHeader(header);
            challengeService.checkIsChallengeOwner(challenge, profile);
            challengeService.deleteSong(challenge, songId);
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errorMessage(e.getMessage())
                    .build());
        }
        return ResponseEntity.ok(ResponseModel.builder()
                .data(ChallengeMapper.toModel(challenge))
                .build());
    }


}
