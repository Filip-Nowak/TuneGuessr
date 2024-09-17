package com.example.tuneguessrserver.game.classic;

import com.example.tuneguessrserver.challenge.SongModel;
import com.example.tuneguessrserver.game.GameData;
import com.example.tuneguessrserver.session.room.SessionData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassicGameTemplate implements GameData {
    private String id;

    private List<SongModel> songs;
    private long challengeId;
    private int currentSongIndex;
    private List<String> players;
}
