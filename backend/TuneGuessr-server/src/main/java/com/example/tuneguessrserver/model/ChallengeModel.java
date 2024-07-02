package com.example.tuneguessrserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChallengeModel {
    private long id;
    private String name;
    private String description;
    private List<SongModel> songs;
    private String author;

}
