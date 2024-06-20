package com.example.tuneguessrserver;

import com.example.tuneguessrserver.entity.Challenge;
import com.example.tuneguessrserver.entity.Song;
import com.example.tuneguessrserver.entity.User;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.model.UserModel;
import com.example.tuneguessrserver.model.requests.TestRequest;
import com.example.tuneguessrserver.repository.ChallengeRepository;
import com.example.tuneguessrserver.repository.UserProfileRepository;
import com.example.tuneguessrserver.repository.UserRepository;
import com.example.tuneguessrserver.service.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    UserService userService;
    UserProfileRepository userProfileRepository;
    ChallengeRepository challengeRepository;
    @GetMapping("/")
    public String test(){
        return "xd";
    }

    @GetMapping("/test")
    public List<UserProfile> test2(){
        User user= User.builder()
                .email("email@xd.com")
                .password("password")
                .build();
        UserProfile userProfile=UserProfile.builder()
                .user(user)
                .nickname("user1")
                .build();
        userProfileRepository.save(userProfile);
        List<UserProfile> users=userProfileRepository.findAll();
        Challenge challenge=Challenge.builder()
                .description("this is description of example challenge")
                .name("my challenge")
                .user(userProfile)
                .build();
//        Song song1= Song.builder()
//                .link("url.to.song")
//                .name("firstSong").build();
//        challenge.addSong(song1);
        users.get(0).addChallenge(challenge);
        userProfileRepository.save(userProfile);
        List<Challenge> challengeList=challengeRepository.findAll();
        return null;
    }
    @PostMapping("/xd")
    public String xd(@Valid @RequestBody TestRequest r){
        return r.getTest();
    }

}
