import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent, FooterComponent],
  template: `
    <div class="min-h-screen bg-white dark:bg-dark-bg">
      <app-navbar></app-navbar>
      
      <main class="pt-20 pb-8 min-h-[calc(100vh-5rem)]">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
          <div class="mb-8">
            <div class="flex justify-between items-center">
              <div>
                <h1 class="text-4xl font-display font-bold text-primary-900 dark:text-white mb-2">
                  Painel Administrativo
                </h1>
                <p class="text-slate-600 dark:text-slate-400">
                  Bem-vindo, {{ admin?.nome || 'Administrador' }}
                </p>
              </div>
              <button
                (click)="logout()"
                class="px-6 py-2.5 text-slate-700 dark:text-slate-300 font-semibold rounded-lg hover:bg-slate-100 dark:hover:bg-primary-800 transition-colors text-sm border border-slate-300 dark:border-dark-border"
              >
                Sair
              </button>
            </div>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
            <a 
              routerLink="/admin/database-test"
              class="block bg-white dark:bg-dark-surface rounded-lg shadow-sm border border-slate-200 dark:border-primary-800 p-6 hover:shadow-md transition-shadow cursor-pointer"
            >
              <div class="flex items-center mb-4">
                <div class="p-3 bg-accent-100 dark:bg-accent-900/30 rounded-lg">
                  <svg class="w-6 h-6 text-accent-600 dark:text-accent-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 7v10c0 2.21 3.582 4 8 4s8-1.79 8-4V7M4 7c0 2.21 3.582 4 8 4s8-1.79 8-4M4 7c0-2.21 3.582-4 8-4s8 1.79 8 4m0 5c0 2.21-3.582 4-8 4s-8-1.79-8-4"></path>
                  </svg>
                </div>
                <h3 class="ml-4 text-lg font-semibold text-primary-900 dark:text-white">
                  Banco de Dados
                </h3>
              </div>
              <p class="text-slate-600 dark:text-slate-400 text-sm">
                Gerencie e teste visualizações, procedimentos armazenados e gatilhos do banco
              </p>
            </a>

            <div class="bg-white dark:bg-dark-surface rounded-lg shadow-sm border border-slate-200 dark:border-primary-800 p-6 opacity-50">
              <div class="flex items-center mb-4">
                <div class="p-3 bg-slate-100 dark:bg-primary-800 rounded-lg">
                  <svg class="w-6 h-6 text-slate-600 dark:text-slate-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"></path>
                  </svg>
                </div>
                <h3 class="ml-4 text-lg font-semibold text-primary-900 dark:text-white">
                  Usuários
                </h3>
              </div>
              <p class="text-slate-600 dark:text-slate-400 text-sm">
                Em breve: Gerencie clientes e prestadores
              </p>
            </div>

            <div class="bg-white dark:bg-dark-surface rounded-lg shadow-sm border border-slate-200 dark:border-primary-800 p-6 opacity-50">
              <div class="flex items-center mb-4">
                <div class="p-3 bg-slate-100 dark:bg-primary-800 rounded-lg">
                  <svg class="w-6 h-6 text-slate-600 dark:text-slate-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                  </svg>
                </div>
                <h3 class="ml-4 text-lg font-semibold text-primary-900 dark:text-white">
                  Relatórios
                </h3>
              </div>
              <p class="text-slate-600 dark:text-slate-400 text-sm">
                Em breve: Visualize relatórios e estatísticas
              </p>
            </div>
          </div>

          <router-outlet></router-outlet>
        </div>
      </main>

      <app-footer class="mt-auto"></app-footer>
    </div>
  `,
  styles: []
})
export class AdminDashboardComponent implements OnInit {
  admin: any = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    if (!this.authService.isAdminLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }
    
    this.admin = this.authService.getAdmin();
  }

  logout() {
    this.authService.logoutAdmin();
  }
}

