import { Component, OnInit, inject } from '@angular/core'; // Importa decoradores e helper para injeção.
import { CommonModule } from '@angular/common'; // Módulo com diretivas básicas (ngIf, ngFor).
import { RouterOutlet } from '@angular/router'; // Componente que renderiza as rotas filhas.
import { ThemeService } from './services/theme.service'; // Serviço responsável por alternar o tema.

@Component({ // Declaração do componente raiz.
  selector: 'app-root', // Nome da tag usada no index.html.
  standalone: true, // Define que o componente não precisa de módulo separado.
  imports: [CommonModule, RouterOutlet], // Módulos que este componente utiliza.
  template: ` // Template inline extremamente simples.
    <router-outlet></router-outlet> // Renderiza o componente correspondente à rota atual.
  `,
  styles: [] // Não há estilos específicos aqui.
})
export class AppComponent implements OnInit { // Classe do componente principal.
  private themeService = inject(ThemeService); // Injeta o ThemeService usando a API de injeção funcional.

  ngOnInit(): void { // Hook chamado quando o componente é inicializado.
    // Nada a fazer aqui porque o ThemeService já inicializa o tema ao ser injetado.
  }
}

