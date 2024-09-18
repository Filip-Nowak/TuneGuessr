package com.example.tuneguessrserver.game;

public class GameError extends RuntimeException{
    public GameError(String message) {
        super(message);
    }
}
