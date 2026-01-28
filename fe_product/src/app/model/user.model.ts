export interface UserResDTO {
  id: number;
  username: string;
}

export interface UserCreDTO {
  id?: number;
  username: string;
  password?: string;
}
export interface LoginResponse {
  accessToken: string; 
  username: string;
}

export interface LoginRequest {
  username: string;
  password?: string;
}
