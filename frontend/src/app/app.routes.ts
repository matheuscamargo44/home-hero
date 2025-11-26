import { Routes } from '@angular/router'; // Tipo utilitário do Angular Router para declarar rotas.
import { HomeComponent } from './components/home/home.component'; // Página inicial pública.
import { LoginComponent } from './components/login/login.component'; // Tela de login unificada.
import { RegisterTypeComponent } from './components/register/register-type.component'; // Tela que pergunta o tipo de cadastro.
import { RegisterClienteComponent } from './components/register/register-cliente.component'; // Formulário de cadastro de clientes.
import { RegisterPrestadorComponent } from './components/register/register-prestador.component'; // Formulário de cadastro de prestadores.
import { AdminDashboardComponent } from './components/admin/admin-dashboard.component'; // Tela protegida do administrador.
import { adminGuard } from './guards/admin.guard'; // Guard que libera o acesso ao dashboard somente com token válido.
import { PrestadorAgendamentosComponent } from './components/prestador/prestador-agendamentos.component'; // Tela mock de agendamentos do prestador.
import { PrestadorChatComponent } from './components/prestador/prestador-chat.component'; // Tela mock de chat prestador-cliente.

export const routes: Routes = [ // Lista completa de rotas da aplicação.
  {
    path: '', // URL base (home).
    component: HomeComponent // Renderiza a landing page.
  },
  {
    path: 'prestador/agendamentos', // Tela mock pública.
    component: PrestadorAgendamentosComponent // Mostra cards de agendamento do prestador.
  },
  {
    path: 'prestador/chat', // Tela mock pública.
    component: PrestadorChatComponent // Mostra o chat simplificado.
  },
  {
    path: 'login', // Rota de autenticação unificada.
    component: LoginComponent // Renderiza o formulário de login.
  },
  {
    path: 'register', // Choose-your-path para cadastros.
    component: RegisterTypeComponent // Permite escolher entre cliente ou prestador.
  },
  {
    path: 'register/cliente', // Cadastro de clientes.
    component: RegisterClienteComponent // Formulário completo do cliente.
  },
  {
    path: 'register/prestador', // Cadastro de prestadores.
    component: RegisterPrestadorComponent // Formulário completo do prestador.
  },
  {
    path: 'admin/login', // Alias antigo.
    redirectTo: '/login', // Redireciona para o login unificado.
    pathMatch: 'full' // Garante que apenas /admin/login seja redirecionado.
  },
  {
    path: 'admin', // Dashboard protegido.
    component: AdminDashboardComponent, // Componente exibido.
    canActivate: [adminGuard] // Guard que valida o token antes de liberar acesso.
  },
  {
    path: '**', // Qualquer rota desconhecida.
    redirectTo: '' // Redireciona para a home.
  }
];
