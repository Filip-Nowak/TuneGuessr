export function hello() {
  console.log("hello");
}
type ApiRequest =
  | { url: string; method: "GET"; auth: boolean }
  | { url: string; method: "POST"; body: unknown; auth: boolean };
export class HTTPManager {
  async sendRequest<T>(request: ApiRequest): Promise<T> {
    const API_URL = "https://localhost:8080/";
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    if (request.auth) {
      const token = localStorage.getItem("token");
      if (!token) {
        throw new Error("No token found in localStorage");
      }
      headers["Authorization"] = `Bearer ${token}`;
    }

    const response = await fetch(API_URL + request.url, {
      method: request.method,
      headers,
      body:
        request.method === "POST" ? JSON.stringify(request.body) : undefined,
    });

    if (!response.ok) {
      throw new Error(`Error: ${response.status} ${response.statusText}`);
    }

    return response.json();
  }
}
