import { AuthResponse, ErrorBody } from "./Responses";
export function checkHandlable(error: Error) {
  if (error instanceof ServerError) {
    throw new Error(error.message);
  }
}
export interface ApiError {
  status: number;
  message: "";
}
interface ServerError extends Error {}
export function ServerError(msg: string) {
  return new Error(msg) as ServerError;
}
interface AuthError extends Error {
  status: number;
  message: string;
  errors: ApiError[];
}
export function AuthError(response: AuthResponse) {
  const error = new Error() as AuthError;
  error.status = response.status;
  error.errors = [];
  response.errors.forEach((e) => {
    error.errors.push({
      status: e.status,
      message: e.message,
    } as ApiError);
  });
  return error;
}
