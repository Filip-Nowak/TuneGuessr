package com.example.tuneguessrserver.session;

import com.example.tuneguessrserver.session.room.Player;
import com.example.tuneguessrserver.session.room.RoomController;
import com.example.tuneguessrserver.session.user.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebsocketEventListener {
    private final PlayerSession playerSession;
    private final RedisService redisService;
    private final RoomController roomController;
    @EventListener
public void handleSessionDisconnect(SessionDisconnectEvent event) {
        System.out.println("disconnected");
        String userId = playerSession.getUserId();
        Player player = (Player) redisService.find(userId);
        if (player.getRoomId() != null) {
            roomController.leaveRoom();
        }
        redisService.delete(userId);
    }
}
