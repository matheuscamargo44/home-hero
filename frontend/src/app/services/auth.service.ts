import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

/**
 * Tipos de usuários do sistema
 */
export type UserRole = 'admin' | 'cliente' | 'prestador';

/**
 * Serviço responsável pela autenticação e gerenciamento de sessão
 * Gerencia login, logout e verificação de autenticação para todos os tipos de usuários
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  /**
   * Realiza login unificado (admin por email ou cliente/prestador por CPF)
   * @param identifier Email (admin) ou CPF (cliente/prestador)
   * @param senha Senha do usuário
   * @returns Observable com resposta do servidor
   */
  login(identifier: string, senha: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { identifier, senha });
  }

  // ========== Métodos para Administrador ==========

  /**
   * Verifica se há um administrador logado
   */
  isAdminLoggedIn(): boolean {
    return !!sessionStorage.getItem('adminToken');
  }

  /**
   * Obtém o token do administrador logado
   */
  getAdminToken(): string | null {
    return sessionStorage.getItem('adminToken');
  }

  /**
   * Obtém os dados do administrador logado
   */
  getAdmin(): any {
    const admin = sessionStorage.getItem('admin');
    return admin ? JSON.parse(admin) : null;
  }

  /**
   * Armazena os dados do administrador na sessão
   */
  setAdmin(admin: any, token: string): void {
    sessionStorage.setItem('adminToken', token);
    sessionStorage.setItem('admin', JSON.stringify(admin));
    sessionStorage.setItem('userRole', 'admin');
  }

  // ========== Métodos para Cliente ==========

  /**
   * Verifica se há um cliente logado
   */
  isClienteLoggedIn(): boolean {
    return !!sessionStorage.getItem('clienteToken');
  }

  /**
   * Obtém o token do cliente logado
   */
  getClienteToken(): string | null {
    return sessionStorage.getItem('clienteToken');
  }

  /**
   * Obtém os dados do cliente logado
   */
  getCliente(): any {
    const cliente = sessionStorage.getItem('cliente');
    return cliente ? JSON.parse(cliente) : null;
  }

  /**
   * Armazena os dados do cliente na sessão
   */
  setCliente(cliente: any, token: string): void {
    sessionStorage.setItem('clienteToken', token);
    sessionStorage.setItem('cliente', JSON.stringify(cliente));
    sessionStorage.setItem('userRole', 'cliente');
  }

  // ========== Métodos para Prestador ==========

  /**
   * Verifica se há um prestador logado
   */
  isPrestadorLoggedIn(): boolean {
    return !!sessionStorage.getItem('prestadorToken');
  }

  /**
   * Obtém o token do prestador logado
   */
  getPrestadorToken(): string | null {
    return sessionStorage.getItem('prestadorToken');
  }

  /**
   * Obtém os dados do prestador logado
   */
  getPrestador(): any {
    const prestador = sessionStorage.getItem('prestador');
    return prestador ? JSON.parse(prestador) : null;
  }

  /**
   * Armazena os dados do prestador na sessão
   */
  setPrestador(prestador: any, token: string): void {
    sessionStorage.setItem('prestadorToken', token);
    sessionStorage.setItem('prestador', JSON.stringify(prestador));
    sessionStorage.setItem('userRole', 'prestador');
  }

  // ========== Métodos Gerais ==========

  /**
   * Obtém o papel (role) do usuário atual
   */
  getCurrentRole(): UserRole | null {
    return sessionStorage.getItem('userRole') as UserRole | null;
  }

  /**
   * Verifica se há algum usuário logado (qualquer tipo)
   */
  isLoggedIn(): boolean {
    return this.isAdminLoggedIn() || this.isClienteLoggedIn() || this.isPrestadorLoggedIn();
  }

  /**
   * Faz logout de todos os usuários e limpa a sessão
   */
  logout(): void {
    sessionStorage.clear();
    this.router.navigate(['/']);
  }

  /**
   * Faz logout apenas do administrador
   */
  logoutAdmin(): void {
    sessionStorage.removeItem('adminToken');
    sessionStorage.removeItem('admin');
    if (sessionStorage.getItem('userRole') === 'admin') {
      sessionStorage.removeItem('userRole');
    }
    this.router.navigate(['/login']);
  }

  /**
   * Faz logout apenas do cliente
   */
  logoutCliente(): void {
    sessionStorage.removeItem('clienteToken');
    sessionStorage.removeItem('cliente');
    if (sessionStorage.getItem('userRole') === 'cliente') {
      sessionStorage.removeItem('userRole');
    }
    this.router.navigate(['/login']);
  }

  /**
   * Faz logout apenas do prestador
   */
  logoutPrestador(): void {
    sessionStorage.removeItem('prestadorToken');
    sessionStorage.removeItem('prestador');
    if (sessionStorage.getItem('userRole') === 'prestador') {
      sessionStorage.removeItem('userRole');
    }
    this.router.navigate(['/login']);
  }
}
