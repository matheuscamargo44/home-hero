import { Component } from '@angular/core'; // Importa o decorador de componente.
import { CommonModule } from '@angular/common'; // Necessário para bindings básicos.

@Component({ // Declaração do componente de rodapé.
  selector: 'app-footer', // Tag utilizada em outros templates.
  standalone: true, // Componente independente de módulos.
  imports: [CommonModule], // Disponibiliza diretivas como ngIf, ngFor.
  template: `
    <footer class="bg-primary-900 dark:bg-dark-bg text-slate-300 dark:text-slate-400 mt-auto">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
        <div class="flex flex-col md:flex-row items-center justify-between gap-4">
          <!-- Marca -->
          <div class="flex items-center gap-3">
            <img 
              src="assets/logo-homehero.png" 
              alt="HomeHero" 
              class="h-8 w-auto"
            />
            <span class="text-lg font-display font-bold text-white">HomeHero</span>
          </div>

          <!-- Direitos Autorais -->
          <div class="text-xs text-slate-400 dark:text-slate-500">
            <p>&copy; {{ currentYear }} HomeHero</p>
          </div>
        </div>
      </div>
    </footer>
  `,
  styles: []
})
export class FooterComponent { // Classe que controla o rodapé.
  currentYear = new Date().getFullYear(); // Exibe o ano corrente no template.
}
