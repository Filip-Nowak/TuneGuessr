package com.example.tuneguessrserver;

import com.example.tuneguessrserver.entity.Challenge;
import com.example.tuneguessrserver.entity.Role;
import com.example.tuneguessrserver.entity.User;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.repository.RoleRepository;
import com.example.tuneguessrserver.security.auth.AuthenticationService;
import com.example.tuneguessrserver.security.auth.RegisterRequest;
import com.example.tuneguessrserver.service.ChallengeService;
import com.example.tuneguessrserver.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TuneGuessrServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuneGuessrServerApplication.class, args);
    }
    @Bean
    public CommandLineRunner runner(AuthenticationService authenticationService, RoleRepository roleRepository){
        return runner->{
            loadRoles(roleRepository);
//            loaduser(userService,roleRepository);
//            loadChallenge(challengeService,userService);
            loadMiki(authenticationService);
        };
    }

    private void loadMiki(AuthenticationService authenticationService) {
        authenticationService.register(RegisterRequest.builder()
                .email("test@test.gmail.com")
                .password("kanapka")
                .nickname("Mikołaj Bala")
                .build());
    }

    private void loaduser(UserService userService,RoleRepository roleRepository) {
        User user=User.builder()
                .email("user@gmail.com")
                .roles(List.of(roleRepository.findByName("USER")))
                .password("$2a$10$wzSBD27giCRjTQYEFNd6ZOSsP5NEqARabqxiihK7PQVE0U.Xjs9Q6").build();
        UserProfile userProfile=UserProfile.builder()
                .user(user)
                .nickname("jebacz kurew")
                .build();
        userService.saveProfile(userProfile);
    }

    private void loadChallenge(ChallengeService challengeService, UserService userService) {
        UserProfile userProfile=userService.getProfileByNickname("jebacz kurew");
        Challenge challenge=Challenge.builder()
                .user(userProfile)
                .name("xd").build();
        challengeService.save(challenge);
    }

    private void loadRoles(RoleRepository roleRepository) {
        Role role=Role.builder()
                .name("USER")
                .build();
        roleRepository.save(role);
    }
}
