package com.example.tuneguessrserver.session;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String,Object> redisRepository;
    public void save(String key, Object value) {
        redisRepository.opsForValue().set(key, value);
    }
    public Object find(String key) {
        return redisRepository.opsForValue().get(key);
    }
}
