import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
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
export class FooterComponent {
  currentYear = new Date().getFullYear();
}
