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
                Gerencie e teste views, procedures e triggers do banco
              </p>
            </a>
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

