import { HTTPManager } from "../network/HTTPManager";
import { networkManager } from "../network/NetworkManager";
import { AuthError } from "../ApiErrors";
class AuthService {
  async authenticate(email: string, password: string) {
    const response = await networkManager.authenticate(email, password);
    if (response.errors.length !== 0) {
      throw AuthError(response);
    }
    if (response.status === 30) {
      localStorage.setItem("token", response.token);
    }
  }
}

export const authService = new AuthService();
