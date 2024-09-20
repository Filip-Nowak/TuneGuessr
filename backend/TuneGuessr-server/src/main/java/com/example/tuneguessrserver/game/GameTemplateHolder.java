package com.example.tuneguessrserver.game;

import com.example.tuneguessrserver.game.classic.GameTemplate;
import lombok.Builder;
import lombok.Data;

@Data
public class GameTemplateHolder implements GameData{
    private GameMode mode;
    private String id;
    private GameTemplate template;
    public GameTemplateHolder(Game game){
        this.mode=game.getMode();
        this.id=game.getId();
        this.template=game.getTemplate();
    }
}
