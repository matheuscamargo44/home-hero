import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterTypeComponent } from './components/register/register-type.component';
import { RegisterClienteComponent } from './components/register/register-cliente.component';
import { RegisterPrestadorComponent } from './components/register/register-prestador.component';
import { AdminLoginComponent } from './components/admin/admin-login.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard.component';
import { DatabaseTestComponent } from './components/admin/database-test.component';
import { adminGuard } from './guards/admin.guard';
import { clienteGuard } from './guards/cliente.guard';
import { prestadorGuard } from './guards/prestador.guard';

/**
 * Configuração de rotas da aplicação
 * Define todas as rotas públicas e protegidas do sistema
 */
export const routes: Routes = [
  // Rota pública - Página inicial
  {
    path: '',
    component: HomeComponent
  },
  // Rota pública - Login unificado (admin, cliente, prestador)
  {
    path: 'login',
    component: LoginComponent
  },
  // Rota pública - Seleção de tipo de cadastro
  {
    path: 'register',
    component: RegisterTypeComponent
  },
  // Rota pública - Cadastro de cliente
  {
    path: 'register/cliente',
    component: RegisterClienteComponent
  },
  // Rota pública - Cadastro de prestador
  {
    path: 'register/prestador',
    component: RegisterPrestadorComponent
  },
  // Redireciona /admin/login para /login (login unificado)
  {
    path: 'admin/login',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  // Rota protegida - Dashboard do administrador (requer autenticação)
  {
    path: 'admin',
    component: AdminDashboardComponent,
    canActivate: [adminGuard],
    children: [
      // Sub-rota - Teste de banco de dados
      {
        path: 'database-test',
        component: DatabaseTestComponent
      }
    ]
  },
  // Rota catch-all - Redireciona qualquer rota não encontrada para a home
  {
    path: '**',
    redirectTo: ''
  }
];
