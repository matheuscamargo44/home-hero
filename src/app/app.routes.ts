import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterTypeComponent } from './components/register/register-type.component';
import { RegisterClienteComponent } from './components/register/register-cliente.component';
import { RegisterPrestadorComponent } from './components/register/register-prestador.component';

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
    path: '**',
    redirectTo: ''
  }
];

