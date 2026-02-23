import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import WebSocketUser from "../types/WebSocketUser";
export class WebsocketManager {
  private stompClient: Client | null = null;
  private user: WebSocketUser = { id: "" };
  private connected: boolean = false;
  public async connect(): Promise<void> {
    const socket = new SockJS("https://localhsot:8080/ws");
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log("connected");
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
  }

  private async createUser() {
    const response = await fetch("https://localhost:8080/create-user");
    const data = await response.json();
    this.user.id = data.data.userId;
  }
}
