import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeroComponent } from '../hero/hero.component';
import { NavbarComponent } from '../navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';
import { SmoothScrollService } from '../../services/smooth-scroll.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    NavbarComponent,
    HeroComponent,
    FooterComponent
  ],
  template: `
    <div class="min-h-screen">
      <app-navbar></app-navbar>
      <main>
        <app-hero></app-hero>
      </main>
      <app-footer></app-footer>
    </div>
  `,
  styles: []
})
export class HomeComponent implements OnInit {
  private smoothScrollService = inject(SmoothScrollService);

  ngOnInit(): void {
    this.smoothScrollService.init();
  }
}


