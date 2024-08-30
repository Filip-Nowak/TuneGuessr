package com.example.tuneguessrserver.challenge;

import com.example.tuneguessrserver.user.UserProfile;
import com.example.tuneguessrserver.response.mapper.ChallengeMapper;
import com.example.tuneguessrserver.challenge.requests.AddSongModel;
import com.example.tuneguessrserver.challenge.requests.CreateChallengeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final SongRepostitory songRepository;



    public List<Challenge> searchChallengesByName(String name) {
        List<Challenge> challengeList=challengeRepository.findByNameContains(name);
        if(challengeList!=null)
            return challengeList;
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

    public Challenge getChallengeById(long id) throws Exception {
        return challengeRepository.findById(id).orElseThrow(() -> new Exception("Challenge not found"));

    }

    public Challenge addSongToChallenge(Challenge challenge, AddSongModel songModel) {
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

    public void checkIsChallengeOwner(Challenge challenge, UserProfile profile) throws Exception {
        if(challenge.getUser().getId()!=profile.getId())
            throw new Exception("You are not the owner of this challenge");
    }


    public Song getSongByNumber(Challenge challenge, long songId) throws Exception {
        for(Song song:challenge.getSongs()){
            if(song.getNumber()==songId)
                return song;
        }
        throw new Exception("Song not found");

    }

    public void deleteSong(Challenge challenge, long songId) throws Exception {
        Song song=getSongByNumber(challenge,songId);
        challenge.getSongs().remove(song);
        songRepository.delete(song);
        save(challenge);
    }
}
