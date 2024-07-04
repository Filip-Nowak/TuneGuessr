package com.example.tuneguessrserver.mapper;

import com.example.tuneguessrserver.entity.Challenge;
import com.example.tuneguessrserver.entity.Song;
import com.example.tuneguessrserver.model.challange.ChallengeModel;
import com.example.tuneguessrserver.model.SongModel;
import com.example.tuneguessrserver.model.challange.AddSongModel;
import com.example.tuneguessrserver.model.challange.SearchResultModel;

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
            model.setSongs(challenge.getSongs().stream().map(SongMapper::toModel).collect(Collectors.toList()));
        else model.setSongs(new ArrayList<>());
        return model;
    }
    public static SearchResultModel toSearchResultModel(Challenge challenge) {
        return SearchResultModel.builder()
                .id(String.valueOf(challenge.getId()))
                .name(challenge.getName())
                .description(challenge.getDescription())
                .author(challenge.getUser().getNickname())
                .build();
    }
    public static List<SearchResultModel> toSearchResultModel(List<Challenge> challenges) {
        return challenges.stream().map(ChallengeMapper::toSearchResultModel).collect(Collectors.toList());
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
