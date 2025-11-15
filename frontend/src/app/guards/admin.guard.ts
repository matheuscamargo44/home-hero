import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

/**
 * Guard que protege rotas acessíveis apenas por administradores
 * Redireciona para /login se o usuário não estiver autenticado como admin
 */
export const adminGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Verifica se há um administrador logado
  if (authService.isAdminLoggedIn()) {
    return true;
  }

  // Redireciona para login se não estiver autenticado
  router.navigate(['/login']);
  return false;
};
