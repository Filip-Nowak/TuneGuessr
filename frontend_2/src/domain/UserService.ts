import { apiClient } from "../infrastructure/api/ApiClient";
import { ApiDataResponse } from "../infrastructure/api/Responses";
import { webSocketClient } from "../infrastructure/ws/WebSocketClient";
interface UserId {
  userId: string;
}
class UserService {
  async connect() {
    const userId = await this.createUser();
    await webSocketClient.connect(() => {
      console.log("connected");
      webSocketClient.subscribe("/user/" + userId + "/info");
    });
  }
  private async createUser() {
    // const response = await fetch("https://localhost:8080/create-user");
    const response = await apiClient.sendRequest<ApiDataResponse<UserId>>({
      url: "create-user",
      method: "GET",
      auth: false,
    });
    console.log(response.data.userId);
    return response.data.userId;
  }
}
export const userService = new UserService();
