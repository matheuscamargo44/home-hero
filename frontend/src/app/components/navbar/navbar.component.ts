import { Component, HostListener, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SmoothScrollService } from '../../services/smooth-scroll.service';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <nav 
      [class]="'fixed top-0 left-0 right-0 z-50 transition-all duration-300 ' + (isScrolled ? (themeService.isDark() ? 'bg-dark-bg/95 backdrop-blur-md shadow-sm' : 'bg-white/95 backdrop-blur-md shadow-sm') : 'bg-transparent')"
      role="navigation"
      aria-label="Navegação principal"
    >
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between h-20">
          <!-- Logo -->
          <div class="flex-shrink-0">
            <a 
              routerLink="/"
              class="flex items-center gap-3"
              aria-label="HomeHero - Ir para o início"
            >
              <img 
                src="assets/logo-homehero.png" 
                alt="HomeHero" 
                class="h-10 w-auto"
              />
              <span class="text-xl font-display font-bold text-primary-900 dark:text-white hidden sm:inline">HomeHero</span>
            </a>
          </div>

          <!-- Menu Desktop -->
          <div class="hidden md:flex items-center space-x-8">
          </div>

          <!-- Ações -->
          <div class="hidden md:flex items-center gap-4">
            <!-- Alternador de Tema Escuro -->
            <button 
              (click)="toggleTheme()"
              class="p-2 rounded-lg text-slate-700 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-primary-800 transition-colors"
              aria-label="Alternar tema"
            >
              <svg *ngIf="!themeService.isDark()" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z"></path>
              </svg>
              <svg *ngIf="themeService.isDark()" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z"></path>
              </svg>
            </button>
            <!-- Link de Login -->
            <a 
              routerLink="/login"
              class="px-6 py-2.5 text-slate-700 dark:text-slate-300 font-semibold rounded-lg hover:bg-slate-100 dark:hover:bg-primary-800 transition-colors text-sm"
            >
              Entrar
            </a>
            <!-- Link de Cadastro -->
            <a 
              routerLink="/register"
              class="px-6 py-2.5 bg-accent-500 text-white font-semibold rounded-lg hover:bg-accent-600 transition-colors text-sm"
            >
              Cadastrar-se
            </a>
          </div>

          <!-- Ações Mobile -->
          <div class="md:hidden flex items-center gap-2">
            <button 
              (click)="toggleTheme()"
              class="p-2 rounded-lg text-slate-700 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-primary-800 transition-colors"
              aria-label="Alternar tema"
            >
              <svg *ngIf="!themeService.isDark()" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z"></path>
              </svg>
              <svg *ngIf="themeService.isDark()" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z"></path>
              </svg>
            </button>
            <button 
              (click)="toggleMobileMenu()"
              class="p-2 rounded-lg text-slate-700 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-primary-800 transition-colors"
              [attr.aria-expanded]="isMobileMenuOpen"
              aria-label="Abrir menu"
              aria-controls="mobile-menu"
            >
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path 
                *ngIf="!isMobileMenuOpen"
                stroke-linecap="round" 
                stroke-linejoin="round" 
                stroke-width="2" 
                d="M4 6h16M4 12h16M4 18h16"
              ></path>
              <path 
                *ngIf="isMobileMenuOpen"
                stroke-linecap="round" 
                stroke-linejoin="round" 
                stroke-width="2" 
                d="M6 18L18 6M6 6l12 12"
              ></path>
            </svg>
            </button>
          </div>
        </div>
      </div>

      <!-- Menu Mobile -->
      <div 
        *ngIf="isMobileMenuOpen"
        id="mobile-menu"
        class="md:hidden bg-white dark:bg-dark-surface border-t border-slate-200 dark:border-primary-800"
      >
        <div class="px-4 pt-2 pb-4 space-y-2">
          <a 
            routerLink="/login"
            class="block px-3 py-2 text-slate-700 dark:text-slate-300 hover:bg-slate-50 dark:hover:bg-primary-800 rounded-lg transition-colors text-center"
          >
            Entrar
          </a>
          <a 
            routerLink="/register"
            class="block px-3 py-2 text-center bg-accent-500 text-white font-semibold rounded-lg hover:bg-accent-600 transition-colors"
          >
            Cadastrar-se
          </a>
        </div>
      </div>
    </nav>
  `,
  styles: []
})
export class NavbarComponent implements OnInit {
  isScrolled = false;
  isMobileMenuOpen = false;
  smoothScrollService = inject(SmoothScrollService);
  themeService = inject(ThemeService);

  ngOnInit(): void {
    // Inicialização do componente
  }

  toggleTheme(): void {
    this.themeService.toggleTheme();
  }

  @HostListener('window:scroll', [])
  onWindowScroll(): void {
    this.isScrolled = window.scrollY > 50;
  }

  toggleMobileMenu(): void {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  scrollToSection(section: string, event: Event): void {
    event.preventDefault();
    this.isMobileMenuOpen = false;
    this.smoothScrollService.scrollTo(`#${section}`, { offset: -80 });
  }
}
