import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = `${environment.apiUrl}/admin`;

  constructor(private http: HttpClient) {}

  login(email: string, senha: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { email, senha });
  }

  verifyAdmin(): Observable<any> {
    const token = sessionStorage.getItem('adminToken');
    return this.http.post(`${this.apiUrl}/verify`, {}, {
      headers: { 'Authorization': token || '' }
    });
  }

  isAdminLoggedIn(): boolean {
    return !!sessionStorage.getItem('adminToken');
  }

  logout(): void {
    sessionStorage.removeItem('adminToken');
    sessionStorage.removeItem('admin');
    if (sessionStorage.getItem('userRole') === 'admin') {
      sessionStorage.removeItem('userRole');
    }
  }
}




