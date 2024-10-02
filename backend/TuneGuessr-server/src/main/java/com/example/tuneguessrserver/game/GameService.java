package com.example.tuneguessrserver.game;

import com.example.tuneguessrserver.challenge.ChallengeService;
import com.example.tuneguessrserver.challenge.SongModel;
import com.example.tuneguessrserver.game.classic.*;
import com.example.tuneguessrserver.response.websocket.MessageModel;
import com.example.tuneguessrserver.session.RedisService;
import com.example.tuneguessrserver.session.room.Player;
import com.example.tuneguessrserver.session.room.Room;
import com.example.tuneguessrserver.session.room.RoomService;
import com.example.tuneguessrserver.session.user.UserSessionService;
import com.example.tuneguessrserver.utils.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final RedisService redisService;
    private final RoomService roomService;
    private final UserSessionService userSessionService;
    private final ChallengeService challengeService;
    private final TemplateConverter templateConverter;
    public void loadGame(String roomId) {
        Room room = roomService.getRoom(roomId);
        GameMode mode = room.getMode();
        if (mode == GameMode.CLASSIC) {
            List<SongModel> songs = challengeService.getRandomSongs(room.getChallengeId(), 3);
            ClassicGame classicGame = ClassicGame.builder()
                    .id(roomId)
                    .challengeId(room.getChallengeId())
                    .songs(songs
                            .stream().map(song -> {
                                double randomSongStart = Math.floor(Math.random() * 100) / 100;
                                return GameSongModel.builder()
                                        .start(randomSongStart)
                                        .url(song.getUrl())
                                        .artist(song.getArtist())
                                        .title(song.getTitle())
                                        .build();
                            }).toList())
                    .players(room.getPlayers().stream()
                            .map(player -> GamePlayer.builder()
                                    .id(player)
                                    .build())
                            .toList())
                    .build();
            saveGame(classicGame);
            for (GamePlayer player : classicGame.getPlayers()) {
                redisService.save(player);
            }
            Game game = getGame(roomId);
            Log.info("Game loaded2: "+game);
        }

    }
    public void saveGame(Game game) {
        GameTemplateHolder holder = new GameTemplateHolder(game);
        redisService.save(holder);
        for(GamePlayer player:game.getPlayers()){
            redisService.save(player);
        }
    }
//    public String getCurrentSong(String roomId) {
//        Game game = getGame(roomId);
//        return game.getSongs().get(game.getCurrentSongIndex()).getUrl();
//    }

    public Game getGame(String roomId) {
        GameTemplateHolder holder = (GameTemplateHolder) redisService.find("g-"+roomId);
        return templateConverter.toGame(holder.getTemplate());
    }
    public GamePlayer getPlayer(String userId) {
        return (GamePlayer) redisService.find("g-"+userId);
    }

    public void setReady(String userId) {
        GamePlayer player = getPlayer(userId);
        player.setReady(true);
        redisService.save(player);
    }

    public boolean checkAllReady(String roomId) {
        Game game = getGame(roomId);
        for (GamePlayer player : game.getPlayers()) {
            if (!player.isReady()) {
                return false;
            }
        }
        return true;
    }

    public GameLog endGame(String id) {
        //todo end game
        Game game = getGame(id);
        redisService.delete("g-"+id);
        for(GamePlayer player:game.getPlayers()){
            redisService.delete("g-"+player.getId());
        }
        Room room = roomService.getRoom(id);
        for(String player:room.getPlayers()){
            Player player1 = (Player) redisService.find(player);
            player1.setReady(false);
            redisService.save(player1);
        }
        return GameLog.builder()
                .message(MessageModel.createEndInfo())
                .privateMessage(false)
                .build();
    }

    public GameLog startGame(String roomId) {
        Game game = getGame(roomId);
        GameLog log=game.start();
        saveGame(game);
        return log;
    }

    public GameLog nextSong(String playerId,String roomId) {
        Log.info("Next song");
        Game game = getGame(roomId);
        GameLog log=game.handleNext(playerId,roomId);
        saveGame(game);
        return log;
    }

    public GameLog guess(String userId, String roomId, String guess, boolean title, int time) {
        Game game = getGame(roomId);
        GameLog log=game.handleGuess(userId,guess,title,time);
        saveGame(game);
        return log;
    }
}
