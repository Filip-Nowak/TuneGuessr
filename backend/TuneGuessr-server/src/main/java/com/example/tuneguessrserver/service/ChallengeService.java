package com.example.tuneguessrserver.service;

import com.example.tuneguessrserver.entity.Challenge;
import com.example.tuneguessrserver.entity.Song;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.mapper.ChallengeMapper;
import com.example.tuneguessrserver.model.ChallengeModel;
import com.example.tuneguessrserver.model.SongModel;
import com.example.tuneguessrserver.model.challange.CreateChallengeModel;
import com.example.tuneguessrserver.model.challange.SearchResultModel;
import com.example.tuneguessrserver.repository.ChallengeRepository;
import com.example.tuneguessrserver.repository.SongRepostitory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final SongRepostitory songRepository;
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

    public Challenge addSongToChallenge(Challenge challenge, SongModel songModel) {
        if(challenge.getSongs()==null)
            challenge.setSongs(new LinkedList<>());
        Song song=ChallengeMapper.toEntity(songModel);
        song.setNumber(challenge.getSongs().size()+1);
        song.setChallenge(challenge);
        challenge.getSongs().add(song);
        songRepository.save(song);
        return save(challenge);
    }
    public void deleteChallenge(Challenge challenge) {
        challengeRepository.delete(challenge);
    }
    public Song updateSong(Song song) {
        return songRepository.save(song);
    }
}
