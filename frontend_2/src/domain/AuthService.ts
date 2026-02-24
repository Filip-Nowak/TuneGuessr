import { apiClient } from "../infrastructure/api/ApiClient";
import { AuthError } from "../infrastructure/api/ApiErrors";
import { AuthResponse } from "../infrastructure/api/Responses";
class AuthService {
  async authenticate(email: string, password: string) {
    const response = await apiClient.sendRequest<AuthResponse>({
      url: "api/auth/authenticate",
      method: "POST",
      body: {
        email,
        password,
      },
      auth: false,
    });
    if (response.errors.length !== 0) {
      throw AuthError(response);
    }
    if (response.status === 30) {
      localStorage.setItem("token", response.token);
    }
  }
}

export const authService = new AuthService();
