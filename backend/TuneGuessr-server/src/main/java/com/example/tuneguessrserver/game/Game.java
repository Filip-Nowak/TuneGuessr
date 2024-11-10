package com.example.tuneguessrserver.game;

import com.example.tuneguessrserver.game.classic.GamePlayer;
import com.example.tuneguessrserver.game.classic.GameTemplate;
import lombok.Data;

import java.util.List;

@Data
public abstract class Game {
    protected String id;
    protected GameMode mode;

    public abstract GameLog start();
    public abstract GameLog handleGuess(String userId, String guess,boolean title,int time);
    protected List<GamePlayer> players=List.of();

    public abstract GameTemplate getTemplate();

    public abstract GameLog handleNext(String playerId, String roomId);

    public abstract GameLog forfeit(String userId);

}
