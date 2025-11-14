import { Injectable, PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private platformId = inject(PLATFORM_ID);
  private readonly THEME_KEY = 'homehero-theme';
  private isDarkMode = false;

  constructor() {
    if (isPlatformBrowser(this.platformId)) {
      this.initTheme();
    }
  }

  private initTheme(): void {
    const savedTheme = localStorage.getItem(this.THEME_KEY);
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    
    this.isDarkMode = savedTheme ? savedTheme === 'dark' : prefersDark;
    this.applyTheme();
  }

  toggleTheme(): void {
    this.isDarkMode = !this.isDarkMode;
    this.applyTheme();
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(this.THEME_KEY, this.isDarkMode ? 'dark' : 'light');
    }
  }

  setTheme(isDark: boolean): void {
    this.isDarkMode = isDark;
    this.applyTheme();
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(this.THEME_KEY, this.isDarkMode ? 'dark' : 'light');
    }
  }

  isDark(): boolean {
    return this.isDarkMode;
  }

  private applyTheme(): void {
    if (isPlatformBrowser(this.platformId)) {
      const html = document.documentElement;
      if (this.isDarkMode) {
        html.classList.add('dark');
      } else {
        html.classList.remove('dark');
      }
    }
  }
}


