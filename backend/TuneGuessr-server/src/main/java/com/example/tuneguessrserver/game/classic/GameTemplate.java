package com.example.tuneguessrserver.game.classic;

import com.example.tuneguessrserver.game.GameMode;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class GameTemplate implements Serializable {
    protected GameMode mode;
}
