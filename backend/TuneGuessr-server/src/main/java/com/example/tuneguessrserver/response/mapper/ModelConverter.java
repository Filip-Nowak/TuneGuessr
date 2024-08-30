package com.example.tuneguessrserver.response.mapper;

import com.example.tuneguessrserver.challenge.Challenge;
import com.example.tuneguessrserver.response.mapper.SongMapper;

import java.util.*;

public class ModelConverter {
    public static Map<String, Object> convertChallengeToMap(List<String> params, Challenge challenge) {
        if(params==null || params.isEmpty()){
            return convertChallengeToMap(List.of("id","name","description","author","songs"),challenge);
        }
        Map<String,Object> model=new HashMap<>();
        if(params.contains("id")) {
            model.put("id", challenge.getId());
        }
        if(params.contains("name")) {
            model.put("name", challenge.getName());
        }
        if(params.contains("description")) {
            model.put("description", challenge.getDescription());
        }
        if(params.contains("author")) {
            model.put("author", challenge.getUser().getNickname());
        }
        if(params.contains("songs")) {
            model.put("songs", SongMapper.toModel(challenge.getSongs()));
        }
        return model;
    }

    public static List<Map<String, Object>> convertChallengesToMap(List<String> show, List<Challenge> challenges) {
        List<Map<String, Object>> models = new LinkedList<>();
        for (Challenge challenge : challenges) {
            models.add(convertChallengeToMap(show, challenge));
        }
        return models;
    }
}
