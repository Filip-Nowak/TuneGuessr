package com.example.tuneguessrserver.challenge;

import com.example.tuneguessrserver.response.status.ApiError;
import com.example.tuneguessrserver.response.status.ApiStatus;
import com.example.tuneguessrserver.response.status.ErrorModel;
import com.example.tuneguessrserver.user.UserProfile;
import com.example.tuneguessrserver.response.mapper.ChallengeMapper;
import com.example.tuneguessrserver.response.mapper.ModelConverter;
import com.example.tuneguessrserver.response.ResponseModel;
import com.example.tuneguessrserver.challenge.requests.AddSongModel;
import com.example.tuneguessrserver.challenge.requests.CreateChallengeModel;
import com.example.tuneguessrserver.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class ChallengeController {
    private final ChallengeService challengeService;
    private final UserService userService;

    @GetMapping("/challenge/{id}")
    public ResponseEntity<ResponseModel> getChallenge(@PathVariable long id, HttpServletRequest request) {
        try {
            List<String> showParams = getParams(request);
            Challenge challenge = challengeService.getChallengeById(id);
            Map<String, Object> model = ModelConverter.convertChallengeToMap(showParams, challenge);
            return ResponseEntity.ok(ResponseModel.builder()
                    .data(model)
                    .build());
        } catch (ApiError e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errors(List.of(new ErrorModel(e.getStatus())))
                    .build());
        }

    }

    @GetMapping("/challenge/search/{name}")
    public ResponseEntity<ResponseModel> searchChallenge(@PathVariable String name, HttpServletRequest request) {
        List<String> showParams = getParams(request);
        List<Challenge> challenges = challengeService.searchChallengesByName(name);
        if (showParams.isEmpty()) {
            List<ChallengeModel> models = ChallengeMapper.toModel(challenges);
            return ResponseEntity.ok(ResponseModel.builder()
                    .data(models)
                    .build());
        }
        List<Map<String, Object>> models = ModelConverter.convertChallengesToMap(showParams, challenges);
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
        try {
            Challenge challenge = challengeService.getChallengeById(id);
            UserProfile profile = userService.getProfileByHeader(header);
            challengeService.checkIsChallengeOwner(challenge, profile);
            challenge = challengeService.addSongToChallenge(challenge, song);
            return ResponseEntity.ok(ResponseModel.builder()
                    .data(ChallengeMapper.toModel(challenge))
                    .build());

        } catch (ApiError e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errors(List.of(new ErrorModel(e.getStatus())))
                    .build());
        }


    }

    @DeleteMapping("/challenge/{id}")
    public ResponseEntity<ResponseModel> deleteChallenge(@RequestHeader(name = "Authorization") String header, @PathVariable long id) {
        try {
            Challenge challenge = challengeService.getChallengeById(id);
            UserProfile profile = userService.getProfileByHeader(header);
            challengeService.checkIsChallengeOwner(challenge, profile);
            challengeService.deleteChallenge(challenge);
            return ResponseEntity.ok(ResponseModel.builder()
                    .data("Challenge deleted")
                    .build());

        } catch (ApiError e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errors(List.of(new ErrorModel(ApiStatus.NOT_CHALLENGE_OWNER)))
                    .build());
        }

    }

    @PutMapping("/challenge/{id}/song/{songId}")
    public ResponseEntity<ResponseModel> updateSong(@RequestHeader(name = "Authorization") String header, @PathVariable long id, @PathVariable long songId, @RequestBody @Valid AddSongModel model) {

        try {
            Challenge challenge= challengeService.getChallengeById(id);
            UserProfile profile = userService.getProfileByHeader(header);
            challengeService.checkIsChallengeOwner(challenge, profile);
            Song song = challengeService.getSongByNumber(challenge, songId);
            song.setName(model.getTitle());
            song.setUrl(model.getUrl());
            challengeService.updateSong(song);
            return ResponseEntity.ok(ResponseModel.builder()
                    .data(ChallengeMapper.toModel(challenge))
                    .build());
        } catch (ApiError e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errors(List.of(new ErrorModel(e.getStatus())))
                    .build());
        }

    }

    @DeleteMapping("/challenge/{id}/song/{songId}")
    public ResponseEntity<ResponseModel> deleteSong(@RequestHeader(name = "Authorization") String header, @PathVariable long id, @PathVariable long songId) {
        try {
            Challenge challenge = challengeService.getChallengeById(id);
            UserProfile profile = userService.getProfileByHeader(header);
            challengeService.checkIsChallengeOwner(challenge, profile);
            challengeService.deleteSong(challenge, songId);
            return ResponseEntity.ok(ResponseModel.builder()
                    .data(ChallengeMapper.toModel(challenge))
                    .build());
        } catch (ApiError e) {
            return ResponseEntity.ok(ResponseModel.builder()
                    .errors(List.of(new ErrorModel(e.getStatus())))
                    .build());
        }

    }

    private List<String> getParams(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();
        List<String> showParams = new LinkedList<>();
        while (params.hasMoreElements()) {
            String element = params.nextElement();
            if (element.equals("name") || element.equals("id") || element.equals("description") || element.equals("author") || element.equals("songs"))
                showParams.add(element);
        }
        return showParams;
    }


}
