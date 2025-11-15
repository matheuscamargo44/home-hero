import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { ThemeService } from './services/theme.service';

/**
 * Componente raiz da aplicação
 * Responsável por renderizar o router-outlet e inicializar serviços globais
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  template: `
    <router-outlet></router-outlet>
  `,
  styles: []
})
export class AppComponent implements OnInit {
  private themeService = inject(ThemeService);

  /**
   * Inicializa componentes e serviços necessários
   * O tema é inicializado automaticamente pelo ThemeService
   */
  ngOnInit(): void {
    // O tema é inicializado no construtor do ThemeService
  }
}

