import { Component } from '@angular/core'; // Decorador base de componentes.
import { CommonModule } from '@angular/common'; // Diretivas como ngIf/ngFor.
import { RouterModule } from '@angular/router'; // Para usar routerLink nos cards.
import { NavbarComponent } from '../navbar/navbar.component'; // Navbar exibida no topo da página.

@Component({ // Declaração do componente de seleção de tipo.
  selector: 'app-register-type', // Tag usada pela rota /register.
  standalone: true, // Componente independente.
  imports: [CommonModule, RouterModule, NavbarComponent], // Dependências utilizadas no template.
  template: `
    <div class="min-h-screen bg-white dark:bg-dark-bg">
      <app-navbar></app-navbar>
      <div class="flex items-center justify-center min-h-screen px-4 sm:px-6 lg:px-8 py-12 pt-20">
        <div class="max-w-2xl w-full">
        <!-- Logo e Cabeçalho -->
        <div class="text-center mb-12">
          <div class="flex justify-center mb-6">
            <img 
              src="assets/logo-homehero.png" 
              alt="HomeHero" 
              class="h-16 w-auto"
            />
          </div>
          <h2 class="text-3xl font-display font-bold text-primary-900 dark:text-white">
            Criar Conta
          </h2>
          <p class="mt-2 text-sm text-slate-600 dark:text-slate-400">
            Escolha o tipo de conta que deseja criar
          </p>
        </div>

        <!-- Cards de Seleção de Tipo -->
        <div class="grid md:grid-cols-2 gap-6">
          <!-- Card Cliente -->
          <a 
            routerLink="/register/cliente"
            class="group relative bg-white dark:bg-dark-surface p-8 rounded-2xl border-2 border-slate-200 dark:border-dark-border hover:border-accent-500 dark:hover:border-accent-600 transition-all hover:shadow-lg dark:hover:shadow-primary-950/50 cursor-pointer block"
          >
            <div class="text-center">
              <div class="inline-flex items-center justify-center w-16 h-16 rounded-full bg-accent-100 dark:bg-accent-900/30 text-accent-600 dark:text-accent-400 mb-4 group-hover:scale-110 transition-transform">
                <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
                </svg>
              </div>
              <h3 class="text-xl font-bold text-primary-900 dark:text-white mb-2">
                Cliente
              </h3>
              <p class="text-sm text-slate-600 dark:text-slate-400">
                Contrate serviços residenciais de forma rápida e segura
              </p>
            </div>
            <div class="absolute top-4 right-4 opacity-0 group-hover:opacity-100 transition-opacity">
              <svg class="w-5 h-5 text-accent-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
              </svg>
            </div>
          </a>

          <!-- Card Prestador -->
          <a 
            routerLink="/register/prestador"
            class="group relative bg-white dark:bg-dark-surface p-8 rounded-2xl border-2 border-slate-200 dark:border-dark-border hover:border-accent-500 dark:hover:border-accent-600 transition-all hover:shadow-lg dark:hover:shadow-primary-950/50 cursor-pointer block"
          >
            <div class="text-center">
              <div class="inline-flex items-center justify-center w-16 h-16 rounded-full bg-accent-100 dark:bg-accent-900/30 text-accent-600 dark:text-accent-400 mb-4 group-hover:scale-110 transition-transform">
                <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path>
                </svg>
              </div>
              <h3 class="text-xl font-bold text-primary-900 dark:text-white mb-2">
                Prestador
              </h3>
              <p class="text-sm text-slate-600 dark:text-slate-400">
                Ofereça seus serviços e aumente sua renda
              </p>
            </div>
            <div class="absolute top-4 right-4 opacity-0 group-hover:opacity-100 transition-opacity">
              <svg class="w-5 h-5 text-accent-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
              </svg>
            </div>
          </a>
        </div>

        <!-- Voltar para Login -->
        <div class="mt-8 text-center">
          <p class="text-sm text-slate-600 dark:text-slate-400">
            Já tem uma conta?
            <a routerLink="/login" class="font-medium text-accent-500 hover:text-accent-600 dark:text-accent-400 dark:hover:text-accent-300 transition-colors">
              Fazer login
            </a>
          </p>
        </div>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class RegisterTypeComponent {} // Componente puramente visual, sem lógica adicional.

