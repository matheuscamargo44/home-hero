import { Component, AfterViewInit, ElementRef, ViewChild, inject, PLATFORM_ID } from '@angular/core'; // Importa APIs básicas do Angular.
import { CommonModule, isPlatformBrowser } from '@angular/common'; // CommonModule expõe diretivas e o helper detecta o ambiente.
import { RouterModule } from '@angular/router'; // Necessário para o uso de routerLink no template.
import { gsap } from 'gsap'; // Biblioteca de animações usada neste componente.

@Component({ // Configuração do componente hero.
  selector: 'app-hero', // Tag utilizada no template da home.
  standalone: true, // Permite usar o componente sem declarar em um módulo.
  imports: [CommonModule, RouterModule], // Módulos auxiliares usados pelo template.
  template: `
    <section
      id="home"
      class="relative min-h-screen flex items-center justify-center overflow-hidden bg-white dark:bg-dark-bg"
    >
      <!-- Gradiente de fundo sutil -->
      <div class="absolute inset-0 bg-gradient-to-br from-primary-50 via-white to-accent-50/30 dark:from-dark-bg dark:via-dark-bg dark:to-dark-surface"></div>
      
      <!-- Imagem de Fundo - Canto Superior Direito -->
      <div class="absolute top-0 right-0 w-full md:w-1/2 lg:w-2/5 h-full overflow-hidden pointer-events-none">
        <img 
          src="assets/logo-hero.png" 
          alt="HomeHero Background" 
          class="w-full h-full object-cover object-top opacity-30"
        />
      </div>
      
      <!-- Conteúdo -->
      <div class="relative z-10 max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-32 text-center">
        <!-- Logo -->
        <div #logo class="mb-12 flex justify-center">
          <img 
            src="assets/logo-homehero.png" 
            alt="HomeHero Logo" 
            class="h-16 w-auto opacity-0"
          />
        </div>

        <!-- Título Principal -->
        <h1 
          #headline
          class="text-5xl md:text-6xl lg:text-7xl font-display font-bold text-primary-900 dark:text-white mb-6 leading-tight"
        >
          Serviços Residenciais
          <span class="block text-accent-500 dark:text-accent-400 mt-2">Sob Demanda</span>
        </h1>

        <!-- Subtítulo -->
        <p 
          #subheadline
          class="text-xl md:text-2xl text-slate-600 dark:text-slate-300 max-w-2xl mx-auto mb-12 leading-relaxed"
        >
          Conectamos você aos melhores prestadores verificados para manutenção, reparos e reformas.
        </p>

        <!-- Botão CTA -->
        <div 
          #ctaButtons
          class="flex flex-col sm:flex-row items-center justify-center gap-4"
        >
          <a
            routerLink="/register"
            class="px-8 py-4 bg-accent-500 text-white font-semibold rounded-lg hover:bg-accent-600 transition-colors shadow-lg hover:shadow-xl transform hover:-translate-y-0.5 inline-block text-center"
            aria-label="Criar conta no HomeHero"
          >
            Começar Agora
          </a>
        </div>
      </div>
    </section>
  `,
  styles: []
})
export class HeroComponent implements AfterViewInit { // Componente que controla o banner animado.
  @ViewChild('logo', { static: false }) logo!: ElementRef<HTMLDivElement>; // Referência ao container do logo.
  @ViewChild('headline', { static: false }) headline!: ElementRef<HTMLHeadingElement>; // Referência ao título principal.
  @ViewChild('subheadline', { static: false }) subheadline!: ElementRef<HTMLParagraphElement>; // Referência ao subtítulo.
  @ViewChild('ctaButtons', { static: false }) ctaButtons!: ElementRef<HTMLDivElement>; // Referência ao bloco de botões.

  private platformId = inject(PLATFORM_ID); // Injeta o identificador da plataforma (server/browser).

  ngAfterViewInit(): void { // Hook disparado depois que o template é renderizado.
    if (isPlatformBrowser(this.platformId)) { // Evita rodar GSAP no lado do servidor.
      this.initAnimations(); // Configura as animações apenas no browser.
    }
  }

  private initAnimations(): void { // Encapsula a timeline GSAP.
    const tl = gsap.timeline({ defaults: { ease: 'power3.out' } }); // Timeline com easing padrão.

    tl.from(this.logo.nativeElement.querySelector('img'), { // Anima o logo.
      opacity: 0,
      scale: 0.8,
      duration: 0.8
    })
    .from(this.headline.nativeElement.children, { // Anima cada linha do título.
      opacity: 0,
      y: 30,
      duration: 0.8,
      stagger: 0.2
    }, '-=0.4')
    .from(this.subheadline.nativeElement, { // Anima o subtítulo.
      opacity: 0,
      y: 20,
      duration: 0.6
    }, '-=0.4')
    .from(this.ctaButtons.nativeElement.children, { // Anima os botões.
      opacity: 0,
      y: 20,
      duration: 0.5,
      stagger: 0.1
    }, '-=0.3');
  }
}
