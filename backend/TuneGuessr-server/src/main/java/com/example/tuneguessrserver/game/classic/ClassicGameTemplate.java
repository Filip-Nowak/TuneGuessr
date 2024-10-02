package com.example.tuneguessrserver.game.classic;

import com.example.tuneguessrserver.challenge.SongModel;
import com.example.tuneguessrserver.game.GameData;
import com.example.tuneguessrserver.game.GameMode;
import com.example.tuneguessrserver.game.GameSongModel;
import com.example.tuneguessrserver.session.room.SessionData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
public class ClassicGameTemplate extends GameTemplate {
    private String id;
    private final GameMode mode = GameMode.CLASSIC;
    private List<GameSongModel> songs;
    private long challengeId;
    private int currentSongIndex;
    private List<String> players;
}
