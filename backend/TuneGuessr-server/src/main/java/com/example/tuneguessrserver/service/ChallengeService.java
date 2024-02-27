package com.example.tuneguessrserver.service;

import com.example.tuneguessrserver.entity.Challenge;
import com.example.tuneguessrserver.model.ChallengeModel;
import com.example.tuneguessrserver.model.SongModel;
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

    public ChallengeModel parseChallengeToModel(Challenge challenge) {
        ChallengeModel model=ChallengeModel.builder()
                .name(challenge.getName())
                .description(challenge.getDescription())
                .author(challenge.getUser().getNickname())
                .build();
        List<SongModel> songModels=new LinkedList<>();
        for(int i=0;i<challenge.getSongs().size();i++){
            songModels.add(SongModel.builder()
                    .name(challenge.getSongs().get(i).getName())
                    .url(challenge.getSongs().get(i).getUrl()).build());
        }
        model.setSongs(songModels);
        return  model;
    }

    public List<ChallengeModel> searchChallengesByName(String name) {
        List<Challenge> challengeList=challengeRepository.findByNameContains(name);
        if(challengeList!=null)
            return parseChallengeToModel(challengeList);
        return null;
    }

    private List<ChallengeModel> parseChallengeToModel(List<Challenge> challengeList) {
        List<ChallengeModel> models=new LinkedList<>();
        for(Challenge challenge:challengeList){
            models.add(parseChallengeToModel(challenge));
        }
        return models;
    }

    public void save(Challenge challenge) {
        challengeRepository.save(challenge);
    }
}
