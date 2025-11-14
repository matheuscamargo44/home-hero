import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-login',
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
            Bem-vindo de volta
          </h2>
          <p class="mt-2 text-sm text-slate-600 dark:text-slate-400">
            Entre na sua conta para continuar
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
                placeholder="seu@email.com"
              />
              <div *ngIf="loginForm.get('email')?.invalid && loginForm.get('email')?.touched" 
                   class="mt-1 text-sm text-red-600 dark:text-red-400">
                Por favor, insira um email válido
              </div>
            </div>

            <!-- Senha -->
            <div>
              <label for="password" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                Senha
              </label>
              <input
                id="password"
                name="password"
                type="password"
                formControlName="password"
                autocomplete="current-password"
                required
                class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                placeholder="••••••••"
              />
              <div *ngIf="loginForm.get('password')?.invalid && loginForm.get('password')?.touched" 
                   class="mt-1 text-sm text-red-600 dark:text-red-400">
                A senha deve ter pelo menos 6 caracteres
              </div>
            </div>
          </div>

          <!-- Lembrar e Esqueceu -->
          <div class="flex items-center justify-between">
            <div class="flex items-center">
              <input
                id="remember-me"
                name="remember-me"
                type="checkbox"
                formControlName="rememberMe"
                class="h-4 w-4 text-accent-500 focus:ring-accent-500 border-slate-300 dark:border-dark-border rounded"
              />
              <label for="remember-me" class="ml-2 block text-sm text-slate-700 dark:text-slate-300">
                Lembrar-me
              </label>
            </div>

            <div class="text-sm">
              <a href="#" class="font-medium text-accent-500 hover:text-accent-600 dark:text-accent-400 dark:hover:text-accent-300 transition-colors">
                Esqueceu a senha?
              </a>
            </div>
          </div>

          <!-- Mensagem de Erro -->
          <div *ngIf="errorMessage" class="rounded-lg bg-red-50 dark:bg-red-900/20 p-4">
            <p class="text-sm text-red-800 dark:text-red-300">{{ errorMessage }}</p>
          </div>

          <!-- Botão de Envio -->
          <div>
            <button
              type="submit"
              [disabled]="loginForm.invalid || isLoading"
              class="group relative w-full flex justify-center py-3 px-4 border border-transparent text-sm font-semibold rounded-lg text-white bg-accent-500 hover:bg-accent-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-accent-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
            >
              <span *ngIf="!isLoading">Entrar</span>
              <span *ngIf="isLoading" class="flex items-center">
                <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647A7.962 7.962 0 0112 20c4.418 0 8-3.582 8-8h-4a4 4 0 00-4 4v4z"></path>
                </svg>
                Entrando...
              </span>
            </button>
          </div>

          <!-- Link de Cadastro -->
          <div class="text-center">
            <p class="text-sm text-slate-600 dark:text-slate-400">
              Não tem uma conta?
              <a routerLink="/register" class="font-medium text-accent-500 hover:text-accent-600 dark:text-accent-400 dark:hover:text-accent-300 transition-colors">
                Criar conta
              </a>
            </p>
          </div>
        </form>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  isLoading = false;
  errorMessage = '';
  
  private fb = inject(FormBuilder);
  private router = inject(Router);

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      rememberMe: [false]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    // Simulação de login (substitua por chamada real à API)
    setTimeout(() => {
      const { email, password } = this.loginForm.value;
      
      // Aqui você faria a chamada real à API
      // Por enquanto, apenas simula um login bem-sucedido
      if (email && password) {
        // Redirecionar após login bem-sucedido
        // this.router.navigate(['/dashboard']);
        console.log('Login realizado:', { email, rememberMe: this.loginForm.value.rememberMe });
        this.isLoading = false;
        // Por enquanto, apenas mostra mensagem de sucesso
        alert('Login realizado com sucesso! (Esta é uma simulação)');
      } else {
        this.errorMessage = 'Email ou senha inválidos';
        this.isLoading = false;
      }
    }, 1000);
  }
}

