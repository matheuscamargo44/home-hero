import { Component, OnInit, inject } from '@angular/core'; // Importa helpers para criar o componente e injetar serviços.
import { CommonModule } from '@angular/common'; // Módulo com diretivas básicas.
import { HeroComponent } from '../hero/hero.component'; // Seção hero reutilizável.
import { NavbarComponent } from '../navbar/navbar.component'; // Barra de navegação fixa.
import { FooterComponent } from '../footer/footer.component'; // Rodapé da página.
import { SmoothScrollService } from '../../services/smooth-scroll.service'; // Serviço que aplica o comportamento de scroll suave.

@Component({ // Declaração do componente standalone.
  selector: 'app-home', // Tag utilizada nas rotas.
  standalone: true, // Dispensamos módulos externos.
  imports: [ // Componentes utilizados dentro do template.
    CommonModule,
    NavbarComponent,
    HeroComponent,
    FooterComponent
  ],
  template: ` // Template inline simples.
    <div class="min-h-screen"> <!-- Container que ocupa toda a altura da tela -->
      <app-navbar></app-navbar> <!-- Cabeçalho -->
      <main>
        <app-hero></app-hero> <!-- Banner principal -->
      </main>
      <app-footer></app-footer> <!-- Rodapé -->
    </div>
  `,
  styles: [] // Sem estilos adicionais aqui.
})
export class HomeComponent implements OnInit { // Classe do componente home.
  private smoothScrollService = inject(SmoothScrollService); // Injeta o serviço de scroll.

  ngOnInit(): void { // Hook disparado quando o componente é carregado.
    this.smoothScrollService.init(); // Habilita o comportamento de rolagem suave.
  }
}


