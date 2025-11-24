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

          <div class="mt-6 p-6 bg-white dark:bg-dark-surface rounded-lg shadow-sm border border-slate-200 dark:border-primary-800">
            <h2 class="text-2xl font-display font-bold text-primary-900 dark:text-white mb-2">
              Área administrativa
            </h2>
            <p class="text-slate-600 dark:text-slate-400">
              Este painel será usado para gerenciar usuários, serviços e agendamentos.
            </p>
          </div>
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

