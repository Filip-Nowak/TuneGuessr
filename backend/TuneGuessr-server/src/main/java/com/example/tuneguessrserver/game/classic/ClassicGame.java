package com.example.tuneguessrserver.game.classic;

import com.example.tuneguessrserver.challenge.SongModel;
import com.example.tuneguessrserver.session.room.Player;
import com.example.tuneguessrserver.session.room.SessionData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassicGame {
    private String id;
    @Builder.Default
    private List<SongModel> songs=new LinkedList<>();
    private long challengeId;
    private int currentSongIndex = 0;
    @Builder.Default
    private List<ClassicPlayer> players=new LinkedList<>();
    public ClassicGameTemplate getTemplate() {
        return new ClassicGameTemplate(id, songs, challengeId, currentSongIndex, players.stream()
                .map(ClassicPlayer::getId)
                .toList());
    }
}
