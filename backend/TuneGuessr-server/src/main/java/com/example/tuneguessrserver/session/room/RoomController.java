package com.example.tuneguessrserver.session.room;

import com.example.tuneguessrserver.response.ResponseModel;
import com.example.tuneguessrserver.session.PlayerSession;
import com.example.tuneguessrserver.session.SessionModel;
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
        System.out.println("setting session");
        if(data.containsKey("userId")) {
            playerSession.setUserId(data.get("userId"));
        }
        if(data.containsKey("roomId")) {
            playerSession.setRoomId(data.get("roomId"));
        }
        if(data.containsKey("nickname")) {
            playerSession.setNickname(data.get("nickname"));
        }
        if(data.containsKey("ready")) {
            playerSession.setReady(Boolean.parseBoolean(data.get("ready")));
        }
        if(playerSession.getUserId()!=null) {
            System.out.println("sending session");
            SessionModel model=SessionModel.builder().userId(playerSession.getUserId()).roomId(playerSession.getRoomId()).nickname(playerSession.getNickname()).build();
            System.out.println("/xdd/"+playerSession.getUserId());
            messagingTemplate.convertAndSendToUser(playerSession.getUserId(),"/info",model);
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
        if(playerSession.getUserId()==null) {
            return;
        }
        SessionModel model=SessionModel.builder().userId(playerSession.getUserId()).roomId(playerSession.getRoomId()).nickname(playerSession.getNickname()).build();
        messagingTemplate.convertAndSend("/user/"+5,model   );
    }
    @MessageMapping("/room/session")
    public void roomSession() {
        messagingTemplate.convertAndSend("/room/"+playerSession.getUserId(),playerSession);
    }
}
