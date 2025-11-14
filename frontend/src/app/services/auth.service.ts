import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

export type UserRole = 'admin' | 'cliente' | 'prestador';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  login(identifier: string, senha: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { identifier, senha });
  }

  isAdminLoggedIn(): boolean {
    return !!sessionStorage.getItem('adminToken');
  }

  getAdminToken(): string | null {
    return sessionStorage.getItem('adminToken');
  }

  getAdmin(): any {
    const admin = sessionStorage.getItem('admin');
    return admin ? JSON.parse(admin) : null;
  }

  setAdmin(admin: any, token: string): void {
    sessionStorage.setItem('adminToken', token);
    sessionStorage.setItem('admin', JSON.stringify(admin));
    sessionStorage.setItem('userRole', 'admin');
  }

  isClienteLoggedIn(): boolean {
    return !!sessionStorage.getItem('clienteToken');
  }

  getClienteToken(): string | null {
    return sessionStorage.getItem('clienteToken');
  }

  getCliente(): any {
    const cliente = sessionStorage.getItem('cliente');
    return cliente ? JSON.parse(cliente) : null;
  }

  setCliente(cliente: any, token: string): void {
    sessionStorage.setItem('clienteToken', token);
    sessionStorage.setItem('cliente', JSON.stringify(cliente));
    sessionStorage.setItem('userRole', 'cliente');
  }

  isPrestadorLoggedIn(): boolean {
    return !!sessionStorage.getItem('prestadorToken');
  }

  getPrestadorToken(): string | null {
    return sessionStorage.getItem('prestadorToken');
  }

  getPrestador(): any {
    const prestador = sessionStorage.getItem('prestador');
    return prestador ? JSON.parse(prestador) : null;
  }

  setPrestador(prestador: any, token: string): void {
    sessionStorage.setItem('prestadorToken', token);
    sessionStorage.setItem('prestador', JSON.stringify(prestador));
    sessionStorage.setItem('userRole', 'prestador');
  }

  getCurrentRole(): UserRole | null {
    return sessionStorage.getItem('userRole') as UserRole | null;
  }

  isLoggedIn(): boolean {
    return this.isAdminLoggedIn() || this.isClienteLoggedIn() || this.isPrestadorLoggedIn();
  }

  logout(): void {
    sessionStorage.clear();
    this.router.navigate(['/']);
  }

  logoutAdmin(): void {
    sessionStorage.removeItem('adminToken');
    sessionStorage.removeItem('admin');
    if (sessionStorage.getItem('userRole') === 'admin') {
      sessionStorage.removeItem('userRole');
    }
    this.router.navigate(['/login']);
  }

  logoutCliente(): void {
    sessionStorage.removeItem('clienteToken');
    sessionStorage.removeItem('cliente');
    if (sessionStorage.getItem('userRole') === 'cliente') {
      sessionStorage.removeItem('userRole');
    }
    this.router.navigate(['/login']);
  }

  logoutPrestador(): void {
    sessionStorage.removeItem('prestadorToken');
    sessionStorage.removeItem('prestador');
    if (sessionStorage.getItem('userRole') === 'prestador') {
      sessionStorage.removeItem('userRole');
    }
    this.router.navigate(['/login']);
  }
}
