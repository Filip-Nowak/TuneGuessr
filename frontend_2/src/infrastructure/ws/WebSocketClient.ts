import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import WebSocketUser from "./WebSocketUser";
import WSMessageHandler from "./WSMessageManager";
import { MessageType } from "./MessageType";
class WebSocketClient {
  private stompClient: Client | null = null;
  private user: WebSocketUser = { id: "" };
  private connected: boolean = false;
  private messageHandler: WSMessageHandler;
  constructor() {
    this.messageHandler = new WSMessageHandler();
    // console.log("connecting...");
    // this.connect();
  }
  public async connect(onConnect: () => void): Promise<void> {
    const socket = new SockJS("https://localhost:8080/ws");
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
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
    this.stompClient = client;
  }
  public async subscribe(channel: string) {
    if (this.stompClient === null) throw new Error("stompClient is null");
    this.stompClient.subscribe(channel, this.messageHandler.handleMessage);
  }

  public getSessionData() {
    console.log("sending");
    this.stompClient?.publish({ destination: "/app/user/session" });
  }
  public addMessageHandler(msgType: MessageType, callback: () => {}) {
    this.messageHandler.addHandler(msgType, callback);
  }
}
export const webSocketClient = new WebSocketClient();
