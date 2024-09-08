package com.example.tuneguessrserver.session.room;

import com.example.tuneguessrserver.response.ResponseModel;
import com.example.tuneguessrserver.session.PlayerSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class RoomController {
    private final RoomService roomService;
    private final PlayerSession playerSession;
    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/room/create")
    public void createRoom() {
        Room room=roomService.createRoom(playerSession.getUserId());
        messagingTemplate.convertAndSend("/user/"+playerSession.getUserId(),room.getId());

    }
    @MessageMapping("/user/set-session")
    public void setId(Map <String,String> data) {
        if(data.containsKey("userId")) {
            playerSession.setUserId(data.get("userId"));
        }
        if(data.containsKey("roomId")) {
            playerSession.setRoomId(data.get("roomId"));
        }
        if(data.containsKey("nickname")) {
            playerSession.setNickname(data.get("nickname"));
        }
    }
    @GetMapping("/create-user")
    public ResponseEntity<ResponseModel> createUser() {
        String id=roomService.createUser();
        return ResponseEntity.ok(ResponseModel.builder().data(Map.of(
                "userId",id
        )).build());
    }

    @MessageMapping("/user/session")
    public void session() {
        messagingTemplate.convertAndSend("/user/"+playerSession.getUserId(),playerSession);
    }
    @MessageMapping("/room/session")
    public void roomSession() {
        messagingTemplate.convertAndSend("/room/"+playerSession.getUserId(),playerSession);
    }
}
