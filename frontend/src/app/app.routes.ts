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

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterTypeComponent
  },
  {
    path: 'register/cliente',
    component: RegisterClienteComponent
  },
  {
    path: 'register/prestador',
    component: RegisterPrestadorComponent
  },
  {
    path: 'admin/login',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: 'admin',
    component: AdminDashboardComponent,
    canActivate: [adminGuard],
    children: [
      {
        path: 'database-test',
        component: DatabaseTestComponent
      }
    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];
