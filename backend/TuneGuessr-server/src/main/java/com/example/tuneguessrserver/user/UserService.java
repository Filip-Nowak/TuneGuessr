package com.example.tuneguessrserver.user;

import com.example.tuneguessrserver.challenge.ChallengeModel;
import com.example.tuneguessrserver.response.status.ApiError;
import com.example.tuneguessrserver.response.status.ApiErrorStatus;
import com.example.tuneguessrserver.security.JwtService;
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
    private JwtService jwtService;
    private UserProfileRepository userProfileRepository;
    public UserProfile getProfileByNickname(String nickname) throws ApiError {
        UserProfile profile=profileRepository.findByNickname(nickname).orElseThrow(() -> new ApiError(ApiErrorStatus.USER_NOT_FOUND));
        return profile;
    }
    public UserModel parseUserToModel(UserProfile userProfile){
        UserModel user=UserModel.builder()
                .id(userProfile.getId())
                .nickname(userProfile.getNickname())
                .email(userProfile.getUser().getEmail())
                .build();
        List<ChallengeModel> challengeList=new ArrayList<>();
        for(int i=0;i<userProfile.getChallengeList().size();i++){
            challengeList.add(ChallengeModel.builder()
                            .id(userProfile.getChallengeList().get(i).getId())
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
    private long generateProfileId() {
        return profileRepository.count();
    }
    public UserProfile getProfileByHeader(String header) throws ApiError{
        String token=header.substring(7);
        String email=jwtService.extractUserEmail(token);
        return userProfileRepository.findProfileByEmail(email).orElseThrow(() -> new ApiError(ApiErrorStatus.USER_NOT_FOUND));
    }

    public UserModel getUserByNickname(String nickname) {
        UserProfile profile= profileRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return parseUserToModel(profile);
    }
}
