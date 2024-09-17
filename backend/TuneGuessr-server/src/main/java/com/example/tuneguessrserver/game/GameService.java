package com.example.tuneguessrserver.game;

import com.example.tuneguessrserver.challenge.ChallengeService;
import com.example.tuneguessrserver.challenge.Song;
import com.example.tuneguessrserver.challenge.SongModel;
import com.example.tuneguessrserver.game.classic.ClassicGame;
import com.example.tuneguessrserver.game.classic.ClassicGameTemplate;
import com.example.tuneguessrserver.game.classic.ClassicPlayer;
import com.example.tuneguessrserver.game.classic.TemplateConverter;
import com.example.tuneguessrserver.session.RedisService;
import com.example.tuneguessrserver.session.room.Room;
import com.example.tuneguessrserver.session.room.RoomService;
import com.example.tuneguessrserver.session.user.UserSessionService;
import io.lettuce.core.ScriptOutputType;
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
    public void startGame(String roomId) {
        Room room = roomService.getRoom(roomId);
        GameMode mode = room.getMode();
        if (mode == GameMode.CLASSIC) {
            List<SongModel> songs = challengeService.getRandomSongs(room.getChallengeId(), 20);
            ClassicGame classicGame = ClassicGame.builder()
                    .id(roomId)
                    .challengeId(room.getChallengeId())
                    .songs(songs)
                    .players(room.getPlayers().stream()
                            .map(player -> ClassicPlayer.builder()
                                    .id(player)
                                    .build())
                            .toList())
                    .build();
            redisService.save(classicGame.getTemplate());
            for (ClassicPlayer player : classicGame.getPlayers()) {
                redisService.save(player);
            }
        }

    }
    public String getCurrentSong(String roomId) {
        ClassicGame game = getGame(roomId);
        return game.getSongs().get(game.getCurrentSongIndex()).getUrl();
    }

    public ClassicGame getGame(String roomId) {
        ClassicGameTemplate template = (ClassicGameTemplate) redisService.find("g-"+roomId);
        return templateConverter.toClassicGame(template);
    }
    public ClassicPlayer getPlayer(String userId) {
        return (ClassicPlayer) redisService.find("g-"+userId);
    }

    public void setReady(String userId) {
        ClassicPlayer player = getPlayer(userId);
        System.out.println(player);
        player.setReady(true);
        System.out.println(player);
        redisService.save(player);
    }

    public boolean checkAllReady(String roomId) {
        ClassicGame game = getGame(roomId);
        System.out.println(game);
        for (ClassicPlayer player : game.getPlayers()) {
            if (!player.isReady()) {
                return false;
            }
        }
        return true;
    }
}
