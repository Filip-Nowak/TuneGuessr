package com.example.tuneguessrserver.game;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class GameSongModel implements Serializable {
    private String url;
    private double start;
    private String title;
    private String artist;
}
