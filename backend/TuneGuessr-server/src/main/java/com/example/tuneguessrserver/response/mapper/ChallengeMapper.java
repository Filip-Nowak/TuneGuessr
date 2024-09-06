package com.example.tuneguessrserver.response.mapper;

import com.example.tuneguessrserver.challenge.Challenge;
import com.example.tuneguessrserver.challenge.Song;
import com.example.tuneguessrserver.challenge.ChallengeModel;
import com.example.tuneguessrserver.challenge.SongModel;
import com.example.tuneguessrserver.challenge.requests.AddSongModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChallengeMapper {
    public static ChallengeModel toModel(Challenge challenge) {
        ChallengeModel model= ChallengeModel.builder()
                .id(challenge.getId())
                .name(challenge.getName())
                .description(challenge.getDescription())
                .author(challenge.getUser().getNickname())
                .build();
        if(challenge.getSongs()!=null)
            model.setSongs(SongMapper.toModel(challenge.getSongs()));
        else model.setSongs(new ArrayList<>());
        return model;
    }

    public static Song toEntity(SongModel songModel) {
        return Song.builder()
                .name(songModel.getTitle())
                .artist(songModel.getArtist())
                .url(songModel.getUrl())
                .build();
    }

    public static Song toEntity(AddSongModel songModel) {
        return Song.builder()
                .name(songModel.getTitle())
                .artist(songModel.getArtist())
                .url(songModel.getUrl())
                .build();
    }

    public static List<ChallengeModel> toModel(List<Challenge> challenges) {
        return challenges.stream().map(ChallengeMapper::toModel).collect(Collectors.toList());
    }
}
