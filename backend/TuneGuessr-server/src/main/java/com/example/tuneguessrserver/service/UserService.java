package com.example.tuneguessrserver.service;

import com.example.tuneguessrserver.entity.Challenge;
import com.example.tuneguessrserver.entity.User;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.model.ChallengeModel;
import com.example.tuneguessrserver.model.UserModel;
import com.example.tuneguessrserver.repository.UserProfileRepository;
import com.example.tuneguessrserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserProfileRepository profileRepository;
    public UserModel getProfileByNickname(String nickname){
        UserProfile profile=profileRepository.findByNickname(nickname);
        if(profile==null)
            return null;
        UserModel model=parseUserToModel(profile);
        return model;
    }
    public UserModel parseUserToModel(UserProfile userProfile){
        UserModel user=UserModel.builder()
                .nickname(userProfile.getNickname())
                .email(userProfile.getUser().getEmail())
                .build();
        List<ChallengeModel> challengeList=new ArrayList<>();
        for(int i=0;i<userProfile.getChallengeList().size();i++){
            challengeList.add(ChallengeModel.builder()
                    .author(userProfile.getNickname())
                    .description(userProfile.getChallengeList().get(i).getDescription())
                    .name(userProfile.getChallengeList().get(i).getName()).build());
        }
        user.setChallengelList(challengeList);
        return user;
    }
}
