package com.example.tuneguessrserver.response.websocket;

import com.example.tuneguessrserver.session.room.PlayerModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageModel {
    private MessageInfo info;
    private Object message;

    public static MessageModel createUserInfo(Object message) {
        return MessageModel.builder().info(MessageInfo.USER_SESSION).message(message).build();
    }

    public static MessageModel createRoomInfo(Object message) {
        return MessageModel.builder().info(MessageInfo.ROOM_SESSION).message(message).build();
    }

    public static MessageModel createRoomCreationInfo(Object message) {
        return MessageModel.builder().info(MessageInfo.ROOM_CREATED).message(message).build();
    }

    public static MessageModel createNewPlayerJoinedInfo(Object message) {
        return MessageModel.builder().info(MessageInfo.NEW_PLAYER_JOINED).message(message).build();
    }

    public static MessageModel createJoinedRoomInfo(Object message) {
        return MessageModel.builder().info(MessageInfo.JOINED_ROOM).message(message).build();
    }

    public static MessageModel createPlayerLeftInfo(Object playerModel) {
        return MessageModel.builder().info(MessageInfo.PLAYER_LEFT).message(playerModel).build();
    }

    public static MessageModel createPlayerReadyInfo(Object playerModel) {
        return MessageModel.builder().info(MessageInfo.PLAYER_READY).message(playerModel).build();
    }
}
