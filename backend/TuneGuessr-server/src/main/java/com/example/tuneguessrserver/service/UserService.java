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
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserProfileRepository profileRepository;
    public UserProfile getProfileByNickname(String nickname){
        UserProfile profile=profileRepository.findByNickname(nickname);
        return profile;
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

    public List<UserModel> searchUsers(String nickname) {
        List<UserProfile> profiles=profileRepository.findByNicknameContains(nickname);
        if(profiles!=null)
            return parseUserToModel(profiles);
        return null;
    }

    private List<UserModel> parseUserToModel(List<UserProfile> profiles) {
        List<UserModel> models=new LinkedList<>();
        for (UserProfile profile : profiles) {
            models.add(parseUserToModel(profile));
        }
        return models;
    }
    public User getUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }

    public void saveProfile(UserProfile userProfile) {
        profileRepository.save(userProfile);
    }

}
