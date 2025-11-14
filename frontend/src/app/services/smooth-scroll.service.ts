import { Injectable, inject, PLATFORM_ID, OnDestroy } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import Lenis from 'lenis';

@Injectable({
  providedIn: 'root'
})
export class SmoothScrollService implements OnDestroy {
  private lenis: Lenis | null = null;
  private platformId = inject(PLATFORM_ID);
  private rafId: number | null = null;

  init(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.lenis = new Lenis({
        duration: 1.2,
        easing: (t) => Math.min(1, 1.001 - Math.pow(2, -10 * t)),
        orientation: 'vertical',
        gestureOrientation: 'vertical',
        smoothWheel: true,
        wheelMultiplier: 1,
        touchMultiplier: 2,
        infinite: false,
      });

      const raf = (time: number) => {
        if (this.lenis) {
          this.lenis.raf(time);
          this.rafId = requestAnimationFrame(raf);
        }
      };

      this.rafId = requestAnimationFrame(raf);
    }
  }

  scrollTo(target: string | number, options?: { offset?: number; duration?: number }): void {
    if (this.lenis) {
      if (typeof target === 'string') {
        const element = document.querySelector(target);
        if (element instanceof HTMLElement) {
          this.lenis.scrollTo(element, {
            offset: options?.offset ?? 0,
            duration: options?.duration ?? 1.2
          });
        }
      } else {
        this.lenis.scrollTo(target, {
          duration: options?.duration ?? 1.2
        });
      }
    }
  }

  ngOnDestroy(): void {
    if (this.rafId !== null) {
      cancelAnimationFrame(this.rafId);
    }
    if (this.lenis) {
      this.lenis.destroy();
      this.lenis = null;
    }
  }
}

