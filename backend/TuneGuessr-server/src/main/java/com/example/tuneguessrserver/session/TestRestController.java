package com.example.tuneguessrserver.session;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestRestController {
    private final RedisService redisService;
    @GetMapping("/test/{key}/{value}")
    public String test(@PathVariable String key, @PathVariable String value) {
        redisService.save(key, value);
        return (String) redisService.find(key);
    }
    @GetMapping("/test/{key}")
    public String test(@PathVariable String key) {
        return (String) redisService.find(key);
    }
}
