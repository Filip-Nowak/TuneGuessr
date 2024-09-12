import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import MessageHandler from "./MessageHandler";

class Online {
  #stompClient;
  #userId;
  #roomId;
  /**
   * @type {MessageHandler}
   */
  #messageHandler = new MessageHandler();
  connect(onConnect) {
    const socket = new SockJS("http://localhost:8080/ws?name=" + name);
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        client.subscribe("/topic/messages", (message) => {
          console.log("message:", message);
        });
        console.log("/xdd/" + this.#userId);
        client.subscribe("/user/" + this.#userId + "/info", (message) => {
          console.log("got message");
          console.log(message);
          const body = JSON.parse(message.body);
          console.log(body);
          this.handleSessionChange(body);
        });
        onConnect();
      },
      onStompError: (error) => {
        console.error("STOMP error:", error);
      },
    });

    client.activate();
    this.#stompClient = client;
  }
  async createUser() {
    console.log(this);
    const response = await fetch("http://localhost:8080/create-user");
    console.log("response:", response);
    const data = await response.json();
    this.#userId = data.data.userId;
    console.log("data:", this.#userId);
  }
  setSession(nickname) {
    console.log("setId");
    console.log(this);
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
    console.log("getSession");
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/user/session",
        body: JSON.stringify(),
      });
    }
  }
  handleSessionChange;
  async quickConnect(onConnect) {
    await this.createUser();
    const socket = new SockJS("http://localhost:8080/ws");
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        client.subscribe("/user/" + this.#userId + "/info", (message) => {
          const body = JSON.parse(message.body);
          console.log("body:", body);
          this.#messageHandler.handle(body.info, body.message);
        });
        const nickname = prompt("Enter your nickname");
        this.setSession(nickname);

        onConnect();
      },
      onStompError: (error) => {
        console.error("STOMP error:", error);
      },
    });

    client.activate();
    this.#stompClient = client;
  }

  createRoom() {
    console.log("createRoom");
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/room/create",
      });
    }
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
  getUserId() {
    return this.#userId;
  }
  joinRoom(roomId) {
    console.log("joinRoom");
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.publish({
        destination: "/app/room/join",
        body: roomId,
      });
    }
  }
  setRoomId(roomId) {
    this.#roomId = roomId;
    if (this.#stompClient && this.#stompClient.connected) {
      this.#stompClient.subscribe("/room/" + roomId, (message) => {
        const body = JSON.parse(message.body);
        this.#messageHandler.handle(body.info, body.message);
      });
    } else {
      throw new Error("not connected");
    }
  }
}
export default new Online();
