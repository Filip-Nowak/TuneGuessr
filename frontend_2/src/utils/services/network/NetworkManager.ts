import { HTTPManager } from "./HTTPManager";
import { User } from "../types/User";
import { WebsocketManager } from "./WebSocketManager";
import { AuthResponse } from "./responses/Responses";

class NetworkManager {
  websocketManager: WebsocketManager;
  httpManager: HTTPManager;
  constructor() {
    console.log("created NetworkManager");
    this.websocketManager = new WebsocketManager();
    this.httpManager = new HTTPManager();
  }
  public async authenticate(email: string, password: string) {
    const response = await this.httpManager.sendRequest<AuthResponse>({
      url: "api/auth/authenticate",
      method: "POST",
      body: {
        email,
        password,
      },
      auth: false,
    });
    console.log(response);

    return response;
  }
}
export const networkManager = new NetworkManager();
