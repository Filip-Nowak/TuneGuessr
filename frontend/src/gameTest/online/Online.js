import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import MessageHandler from "./MessageHandler";
import Room from "./Room";

class Online {
  #stompClient;
  #userId;
  #room;
  #roomSubscription;
  #userSubscription;
  #nickname;
  #ready = false;
  /**
   * @type {MessageHandler}
   */
  #messageHandler = new MessageHandler();
  async createUser() {
    const response = await fetch("http://localhost:8080/create-user");
    const data = await response.json();
    this.#userId = data.data.userId;
  }
  setSession(nickname) {
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/user/set-session",
        body: JSON.stringify({
          userId: this.#userId,
          ...(nickname ? { nickname: nickname } : {}),
        }),
      });
    }
    return {};
  }
  getSession() {
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/user/session",
        body: JSON.stringify(),
      });
    }
  }
  handleSessionChange;
  async quickConnect(nickname, onConnect) {
    await this.createUser();
    const socket = new SockJS("http://localhost:8080/ws");
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        this.setHandlers();
        this.#userSubscription = client.subscribe(
          "/user/" + this.#userId + "/info",
          (message) => {
            const body = JSON.parse(message.body);
            console.log("got USER message:", body);
            this.#messageHandler.handle(body.info, body.message);
          }
        );
        this.setSession(nickname);

        onConnect();
      },
      onStompError: (error) => {
        console.error("STOMP error:", error);
      },
      onDisconnect: () => {
        console.log("disconnected");
      },
      onWebSocketClose: () => {
        console.log("socket closed");
      },
      onWebSocketError: (error) => {
        console.error("socket error:", error);
      },
    });

    client.activate();
    this.#stompClient = client;
  }

  createRoom(challengeId, gamemode) {
    this.#sendMessage(
      "/app/room/create",
      JSON.stringify({ challengeId: challengeId, gameMode: gamemode })
    );
  }

  setHandlers() {
    this.#messageHandler.addHandler("ROOM_CREATED", (message) => {
      this.#room = new Room(message);
      this.#roomSubscription = this.#stompClient.subscribe(
        "/room/" + this.#room.getId(),
        (message) => {
          const body = JSON.parse(message.body);
          console.log("got ROOM message:", body);
          this.#messageHandler.handle(body.info, body.message);
        }
      );
    });
    this.#messageHandler.addHandler("JOINED_ROOM", (message) => {
      this.#room = new Room(message);
      this.#roomSubscription = this.#stompClient.subscribe(
        "/room/" + this.#room.getId(),
        (message) => {
          const body = JSON.parse(message.body);
          console.log("got ROOM message:", body);
          this.#messageHandler.handle(body.info, body.message);
        }
      );
    });
    this.#messageHandler.addHandler("NEW_PLAYER_JOINED", (message) => {
      this.#room.addPlayer(message);
    });
    this.#messageHandler.addHandler("PLAYER_LEFT", (message) => {
      if (message.playerId === this.#userId) {
        this.#room = null;
      } else {
        this.#room.removePlayer(message);
        this.#room.setHostId(message.hostId);
      }
    });
    this.#messageHandler.addHandler("PLAYER_READY", (message) => {
      if (message.playerId === this.#userId) {
        this.#ready = message.ready;
      }
      this.#room.setPlayerReady(message.playerId, message.ready);
    });
    this.#messageHandler.addHandler("GAME_START", (message) => {
      this.#room.setInGame(true);
    });
    this.#messageHandler.addHandler("USER_SESSION", (message) => {
      this.#userId = message.userId;
      this.#nickname = message.nickname;
    });
  }

  setSessionUpdateHandler(handler) {
    this.#messageHandler.addHandler("USER_SESSION", handler);
  }
  setCreateRoomHandler(handler) {
    this.#messageHandler.addHandler("ROOM_CREATED", handler);
  }
  setJoinedRoomHandler(handler) {
    this.#messageHandler.addHandler("JOINED_ROOM", handler);
  }
  setNewPlayerJoinedHandler(handler) {
    this.#messageHandler.addHandler("NEW_PLAYER_JOINED", handler);
  }
  setPlayerLeftHandler(handler) {
    this.#messageHandler.addHandler("PLAYER_LEFT", handler);
  }
  setPlayerReadyHandler(handler) {
    this.#messageHandler.addHandler("PLAYER_READY", handler);
  }
  setGameStartedHandler(handler) {
    this.#messageHandler.addHandler("GAME_START", handler);
  }
  setNextSongHandler(handler) {
    this.#messageHandler.addHandler("NEXT_SONG", handler);
  }
  setRoomErrorHandler(handler) {
    this.#messageHandler.addHandler("ROOM_ERROR", handler);
  }
  getUserId() {
    return this.#userId;
  }
  joinRoom(roomId) {
    if (roomId === "") {
      throw new Error("room id is empty");
    }
    this.#sendMessage("/app/room/join", roomId);
  }
  // setRoomId(roomId) {
  //   if (this.#stompClient && this.#stompClient.connected) {
  //     this.#room.getId() = roomId;
  //     if (roomId === "") {
  //       this.#roomSubscription.unsubscribe();
  //       return;
  //     }
  //     this.#roomSubscription = this.#stompClient.subscribe(
  //       "/room/" + roomId,
  //       (message) => {
  //         const body = JSON.parse(message.body);
  //         this.#messageHandler.handle(body.info, body.message);
  //       }
  //     );
  //   } else {
  //     throw new Error("not connected");
  //   }
  // }
  isInRoom() {
    return !!this.#room;
  }
  leaveRoom() {
    this.#sendMessage("/app/room/leave", "");
  }
  ready(ready) {
    this.#sendMessage("/app/room/ready", ready.toString());
  }
  startGame() {
    this.#sendMessage("/app/game/start", "");
  }
  gameReady() {
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/game/ready",
      });
    }
  }
  getRoom() {
    return this.#room;
  }
  #sendMessage(destination, body) {
    if (this.#stompClient && this.#stompClient.connected) {
      console.log("sending message:", body, "\nto:\n", destination);
      this.#stompClient.publish({
        destination: destination,
        body: body,
      });
    } else {
      console.log("not connected");
    }
  }
}
export default new Online();
