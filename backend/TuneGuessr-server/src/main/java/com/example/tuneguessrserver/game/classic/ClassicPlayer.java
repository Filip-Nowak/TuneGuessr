package com.example.tuneguessrserver.game.classic;

import com.example.tuneguessrserver.game.GameData;
import com.example.tuneguessrserver.session.room.SessionData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassicPlayer implements GameData {
    private String id;
    private int score=0;
    private long time=0;
    private boolean ready=false;

}
