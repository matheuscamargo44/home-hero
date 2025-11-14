import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AdminService } from '../../services/admin.service';
import { AuthService } from '../../services/auth.service';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, NavbarComponent],
  template: `
    <div class="min-h-screen bg-white dark:bg-dark-bg">
      <app-navbar></app-navbar>
      <div class="flex items-center justify-center min-h-screen px-4 sm:px-6 lg:px-8 pt-20">
        <div class="max-w-md w-full space-y-8">
          <!-- Logo e Cabeçalho -->
          <div class="text-center">
            <div class="flex justify-center mb-6">
              <img 
                src="assets/logo-homehero.png" 
                alt="HomeHero" 
                class="h-16 w-auto"
              />
            </div>
            <h2 class="text-3xl font-display font-bold text-primary-900 dark:text-white">
              Login Administrador
            </h2>
            <p class="mt-2 text-sm text-slate-600 dark:text-slate-400">
              Acesso restrito para administradores
            </p>
          </div>

          <!-- Formulário de Login -->
          <form [formGroup]="loginForm" (ngSubmit)="onSubmit()" class="mt-8 space-y-6">
            <div class="space-y-4">
              <!-- Email -->
              <div>
                <label for="email" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Email
                </label>
                <input
                  id="email"
                  name="email"
                  type="email"
                  formControlName="email"
                  autocomplete="email"
                  required
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                  placeholder="admin@homehero.com"
                />
                <div *ngIf="loginForm.get('email')?.invalid && loginForm.get('email')?.touched" 
                     class="mt-1 text-sm text-red-600 dark:text-red-400">
                  Por favor, insira um email válido
                </div>
              </div>

              <!-- Senha -->
              <div>
                <label for="senha" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Senha
                </label>
                <input
                  id="senha"
                  name="senha"
                  type="password"
                  formControlName="senha"
                  autocomplete="current-password"
                  required
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                  placeholder="••••••••"
                />
                <div *ngIf="loginForm.get('senha')?.invalid && loginForm.get('senha')?.touched" 
                     class="mt-1 text-sm text-red-600 dark:text-red-400">
                  A senha é obrigatória
                </div>
              </div>
            </div>

            <!-- Mensagem de Erro -->
            <div *ngIf="error" class="rounded-lg bg-red-50 dark:bg-red-900/20 p-4">
              <p class="text-sm text-red-800 dark:text-red-300">{{ error }}</p>
            </div>

            <!-- Botão de Envio -->
            <div>
              <button
                type="submit"
                [disabled]="loginForm.invalid || loading"
                class="group relative w-full flex justify-center py-3 px-4 border border-transparent text-sm font-semibold rounded-lg text-white bg-accent-500 hover:bg-accent-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-accent-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
              >
                <span *ngIf="!loading">Entrar</span>
                <span *ngIf="loading" class="flex items-center">
                  <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647A7.962 7.962 0 0112 20c4.418 0 8-3.582 8-8h-4a4 4 0 00-4 4v4z"></path>
                  </svg>
                  Entrando...
                </span>
              </button>
            </div>

            <!-- Link de Voltar -->
            <div class="text-center">
              <a routerLink="/" class="text-sm font-medium text-accent-500 hover:text-accent-600 dark:text-accent-400 dark:hover:text-accent-300 transition-colors">
                ← Voltar para o início
              </a>
            </div>
          </form>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class AdminLoginComponent {
  loginForm: FormGroup;
  loading = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private adminService: AdminService,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.loading = true;
      this.error = '';
      
      this.adminService.login(
        this.loginForm.value.email,
        this.loginForm.value.senha
      ).subscribe({
        next: (response) => {
          if (response.success) {
            this.authService.setAdmin(response.admin, response.token || 'admin-token');
            this.router.navigate(['/admin']);
          } else {
            this.error = response.message || 'Erro ao fazer login';
            this.loading = false;
          }
        },
        error: (err) => {
          if (err.status === 0) {
            this.error = 'Erro ao conectar com o servidor. Verifique se o backend está rodando em http://localhost:8080';
          } else if (err.status === 404) {
            this.error = 'Endpoint não encontrado. O backend pode estar ainda iniciando. Aguarde alguns segundos e tente novamente.';
          } else if (err.status === 401) {
            this.error = 'Email ou senha incorretos';
          } else {
            this.error = `Erro ao conectar com o servidor (${err.status || 'desconhecido'})`;
          }
          this.loading = false;
        }
      });
    }
  }
}

