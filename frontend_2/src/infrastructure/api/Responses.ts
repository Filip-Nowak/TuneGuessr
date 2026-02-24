export interface AuthResponse {
  status: number;
  message: string;
  token: string;
  errors: ErrorBody[];
}
export interface ErrorBody {
  status: number;
  message: string;
}
export interface ApiDataResponse<T> {
  data: T;
  errors: ErrorBody[] | null;
}
