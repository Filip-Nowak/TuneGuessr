package com.example.tuneguessrserver.game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuessModel {
    private boolean title;
    private String guess;
    private int points;
    private int time;
}
