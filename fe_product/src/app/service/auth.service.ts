import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    // Gọi tới endpoint xử lý đăng nhập (thường là /login hoặc /signin)
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(res => {
        // Lưu accessToken nhận được từ class AuthResponse (Java) vào trình duyệt
        localStorage.setItem('accessToken', res.accessToken);
        localStorage.setItem('username', res.username);
      })
    );
  }

  logout() {
    localStorage.clear();
  }
}
