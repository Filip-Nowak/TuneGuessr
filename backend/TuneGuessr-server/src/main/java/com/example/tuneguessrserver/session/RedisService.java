package com.example.tuneguessrserver.session;

import com.example.tuneguessrserver.game.GameData;
import com.example.tuneguessrserver.session.room.SessionData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String,Object> redisRepository;
    public void save(String key, Object value) {
        redisRepository.opsForValue().set(key, value);
    }
    public void save(Long key, Object value) {
        save(key.toString(), value);
    }
    public void save(SessionData data) {
        save(data.getId(), data);
    }
    public void save(GameData data) {
        save("g-"+data.getId(), data);
    }
    public Object find(String key) {
        return redisRepository.opsForValue().get(key);
    }

    public String generateRoomId() {
        return UUID.randomUUID().toString().substring(0,10);
    }
    public String generatePlayerId() {
        return "p-"+UUID.randomUUID();
    }

    public void delete(String id) {
        redisRepository.delete(id);
    }
}
