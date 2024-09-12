package com.example.tuneguessrserver.session.user;

import com.example.tuneguessrserver.session.RedisService;
import com.example.tuneguessrserver.session.room.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSessionService {
    private final RedisService redisService;
    public String createUser() {
        String userId = redisService.generatePlayerId();
        redisService.save(Player.builder()
                .id(userId)
                .build());
        return userId;
    }
    public void saveUser(Player player) {
        redisService.save(player);
    }
}
