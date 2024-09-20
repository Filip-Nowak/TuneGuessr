package com.example.tuneguessrserver.game.classic;

import com.example.tuneguessrserver.game.Game;
import com.example.tuneguessrserver.game.GameMode;
import com.example.tuneguessrserver.session.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemplateConverter {
    private final RedisService gameService;

    public ClassicGame toClassicGame(ClassicGameTemplate data) {
        ClassicGame game = ClassicGame.builder()
                .id(data.getId())
                .challengeId(data.getChallengeId())
                .currentSongIndex(data.getCurrentSongIndex())
                .songs(data.getSongs())
                .build();
        for(String id : data.getPlayers()) {
            System.out.println("g-"+id);
            Object player = gameService.find("g-"+id);
            System.out.println(player);
            game.getPlayers().add((GamePlayer) gameService.find("g-"+id));
        }
        return game;
    }

    public Game toGame(GameTemplate template) {
        if (template.getMode() == GameMode.CLASSIC) {
            return toClassicGame((ClassicGameTemplate) template);
        }
        return null;
    }
}
