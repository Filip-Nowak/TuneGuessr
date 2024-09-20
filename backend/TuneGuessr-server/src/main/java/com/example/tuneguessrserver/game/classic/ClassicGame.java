package com.example.tuneguessrserver.game.classic;

import com.example.tuneguessrserver.challenge.SongModel;
import com.example.tuneguessrserver.game.Game;
import com.example.tuneguessrserver.game.GameLog;
import com.example.tuneguessrserver.game.GameSongModel;
import com.example.tuneguessrserver.response.websocket.MessageModel;
import com.example.tuneguessrserver.utils.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassicGame extends Game {
    private String id;
    @Builder.Default
    private List<GameSongModel> songs = new LinkedList<>();
    private long challengeId;
    private int currentSongIndex = 0;
    @Builder.Default
    private List<GamePlayer> players = new LinkedList<>();

    public ClassicGameTemplate getTemplate() {
        return ClassicGameTemplate.builder()
                .id(id)
                .challengeId(challengeId)
                .songs(songs)
                .currentSongIndex(currentSongIndex)
                .players(players.stream()
                        .map(GamePlayer::getId)
                        .toList())
                .build();
    }

    @Override
    public GameLog handleNext(String playerId, String roomId) {
        GamePlayer player = players.stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElseThrow();
        if (player.getCurrentSongIndex() < songs.size()) {
            GameSongModel song = songs.get(player.getCurrentSongIndex());
            MessageModel messageModel = MessageModel.createNextSongInfo(song);
            return GameLog.builder()
                    .message(messageModel)
                    .privateMessage(true)
                    .build();
        }
        return GameLog.builder()
                .message(MessageModel.createFinishInfo())
                .privateMessage(true)
                .build();
    }

    @Override
    public GameLog start() {
        double randomSongStart = Math.floor(Math.random() * 100) / 100;
        Log.info("Random song start: " + randomSongStart);
        MessageModel messageModel = MessageModel.createNextSongInfo(songs.getFirst());
        return GameLog.builder()
                .message(messageModel)
                .privateMessage(false)
                .build();
    }

    @Override
    public GameLog handleGuess(String userId, String guess,boolean title) {
        GamePlayer player = players.stream()
                .filter(p -> p.getId().equals(userId))
                .findFirst()
                .orElseThrow();
        GameSongModel song = songs.get(player.getCurrentSongIndex());
        if(title){
            if (song.getTitle().equalsIgnoreCase(guess)) {
                player.setCurrentSongIndex(player.getCurrentSongIndex() + 1);
                return GameLog.builder()
                        .message(MessageModel.createCorrectGuessInfo(song.getTitle()))
                        .privateMessage(true)
                        .build();
            }else{
                return GameLog.builder()
                        .message(MessageModel.createWrongGuessInfo())
                        .privateMessage(true)
                        .build();
            }
        }else{
            if (song.getArtist().equalsIgnoreCase(guess)) {
                player.setCurrentSongIndex(player.getCurrentSongIndex() + 1);
                return GameLog.builder()
                        .message(MessageModel.createCorrectGuessInfo(song.getArtist()))
                        .privateMessage(true)
                        .build();
            }else{
                return GameLog.builder()
                        .message(MessageModel.createWrongGuessInfo())
                        .privateMessage(true)
                        .build();
            }

        }
    }
}
