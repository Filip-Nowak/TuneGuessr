package com.example.tuneguessrserver.challenge;

import com.example.tuneguessrserver.response.mapper.SongMapper;
import com.example.tuneguessrserver.response.status.ApiError;
import com.example.tuneguessrserver.response.status.ApiStatus;
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
        return challengeRepository.findByNameContains(name);
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

    public Challenge getChallengeById(long id) throws ApiError {
        return challengeRepository.findById(id).orElseThrow(() -> new ApiError(ApiStatus.CHALLENGE_NOT_FOUND));

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

    public void checkIsChallengeOwner(Challenge challenge, UserProfile profile) throws ApiError {
        if(challenge.getUser().getId()!=profile.getId())
            throw new ApiError(ApiStatus.NOT_CHALLENGE_OWNER);
    }


    public Song getSongByNumber(Challenge challenge, long songId) throws ApiError {
        for(Song song:challenge.getSongs()){
            if(song.getNumber()==songId)
                return song;
        }
        throw new ApiError(ApiStatus.SONG_NOT_FOUND);

    }

    public void deleteSong(Challenge challenge, long songId) {
        Song song=getSongByNumber(challenge,songId);
        challenge.getSongs().remove(song);
        songRepository.delete(song);
        save(challenge);
    }
    public Challenge getChallengeWithSongs(long id) {
        return challengeRepository.findByIdAndFetchSongsEagerly(id).orElseThrow(()->new ApiError(ApiStatus.CHALLENGE_NOT_FOUND));
    }
    public List<SongModel> getRandomSongs(long challengeId, int count) {
        List<Song> songs = new LinkedList<>();
        Challenge challenge = getChallengeWithSongs(challengeId);
        for(int i=0;i<count;i++){
            int index = (int) (Math.random() * challenge.getSongs().size());
            songs.add(challenge.getSongs().get(index));
            challenge.getSongs().remove(index);
        }
        return SongMapper.toModel(songs);
    }

    public boolean challengeExists(long challengeId) {
        return challengeRepository.existsById(challengeId);
    }
}
