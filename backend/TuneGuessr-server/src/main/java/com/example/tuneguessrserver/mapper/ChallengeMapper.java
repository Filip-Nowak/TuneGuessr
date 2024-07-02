package com.example.tuneguessrserver.mapper;

import com.example.tuneguessrserver.entity.Challenge;
import com.example.tuneguessrserver.model.ChallengeModel;
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
}
