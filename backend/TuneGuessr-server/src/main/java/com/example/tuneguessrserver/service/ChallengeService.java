package com.example.tuneguessrserver.service;

import com.example.tuneguessrserver.entity.Challenge;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.mapper.ChallengeMapper;
import com.example.tuneguessrserver.model.ChallengeModel;
import com.example.tuneguessrserver.model.SongModel;
import com.example.tuneguessrserver.model.challange.CreateChallengeModel;
import com.example.tuneguessrserver.model.challange.SearchResultModel;
import com.example.tuneguessrserver.repository.ChallengeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChallengeService {
    ChallengeRepository challengeRepository;
    public Challenge getChallengeByName(String name){
        Challenge challenge=challengeRepository.findChallengeByName(name);
        return challenge;
    }


    public List<SearchResultModel> searchChallengesByName(String name) {
        List<Challenge> challengeList=challengeRepository.findByNameContains(name);
        if(challengeList!=null)
            return ChallengeMapper.toSearchResultModel(challengeList);
        return null;
    }


    public Challenge save(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    public Challenge createChallenge(CreateChallengeModel challengeModel, UserProfile profile) {
        Challenge challenge=Challenge.builder()
                .name(challengeModel.getName())
                .description(challengeModel.getDescription())
                .user(profile)
                .build();
        return save(challenge);

    }

    public Challenge getChallengeById(long id) {
        return challengeRepository.findById(id).orElse(null);
    }
}
