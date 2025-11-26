import { Component, OnInit, inject } from '@angular/core'; // API básica para criar componentes.
import { CommonModule } from '@angular/common'; // Diretivas comuns do Angular.
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms'; // Construtor de formulários reativos.
import { Router, RouterModule } from '@angular/router'; // Navegação programática e routerLink.
import { NavbarComponent } from '../navbar/navbar.component'; // Navbar reutilizado na tela de login.
import { AuthService } from '../../services/auth.service'; // Serviço responsável pelas chamadas de autenticação.

@Component({ // Declaração do componente de login.
  selector: 'app-login', // Tag usada nas rotas.
  standalone: true, // Componente independente de módulos.
  imports: [CommonModule, ReactiveFormsModule, RouterModule, NavbarComponent], // Módulos e componentes utilizados.
  template: `
    <div class="min-h-screen bg-white dark:bg-dark-bg">
      <app-navbar></app-navbar>
      <div class="flex items-center justify-center min-h-screen px-4 sm:px-6 lg:px-8 pt-20">
        <div class="max-w-md w-full space-y-8">
        <div class="text-center">
          <div class="flex justify-center mb-6">
            <img 
              src="assets/logo-homehero.png" 
              alt="HomeHero" 
              class="h-16 w-auto"
            />
          </div>
          <h2 class="text-3xl font-display font-bold text-primary-900 dark:text-white">
            Bem-vindo de volta
          </h2>
          <p class="mt-2 text-sm text-slate-600 dark:text-slate-400">
            Entre na sua conta para continuar
          </p>
        </div>

        <form [formGroup]="loginForm" (ngSubmit)="onSubmit()" class="mt-8 space-y-6">
          <div class="space-y-4">
            <div>
              <label for="identifier" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                Email ou CPF
              </label>
              <input
                id="identifier"
                name="identifier"
                type="text"
                formControlName="identifier"
                autocomplete="username"
                required
                (input)="onIdentifierInput($event)"
                class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                [placeholder]="isEmail ? 'admin@homehero.com' : '000.000.000-00'"
              />
              <div *ngIf="loginForm.get('identifier')?.invalid && loginForm.get('identifier')?.touched" 
                   class="mt-1 text-sm text-red-600 dark:text-red-400">
                Por favor, insira um email ou CPF válido
              </div>
              <p class="mt-1 text-xs text-slate-500 dark:text-slate-400">
                Use email para administrador ou CPF para cliente/prestador
              </p>
            </div>

            <div>
              <label for="password" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                Senha
              </label>
              <input
                id="password"
                name="password"
                type="password"
                formControlName="password"
                autocomplete="current-password"
                required
                class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                placeholder="••••••••"
              />
              <div *ngIf="loginForm.get('password')?.invalid && loginForm.get('password')?.touched" 
                   class="mt-1 text-sm text-red-600 dark:text-red-400">
                A senha é obrigatória
              </div>
            </div>
          </div>

          <div class="flex items-center justify-between">
            <div class="flex items-center">
              <input
                id="remember-me"
                name="remember-me"
                type="checkbox"
                formControlName="rememberMe"
                class="h-4 w-4 text-accent-500 focus:ring-accent-500 border-slate-300 dark:border-dark-border rounded"
              />
              <label for="remember-me" class="ml-2 block text-sm text-slate-700 dark:text-slate-300">
                Lembrar-me
              </label>
            </div>

            <div class="text-sm">
              <a href="#" class="font-medium text-accent-500 hover:text-accent-600 dark:text-accent-400 dark:hover:text-accent-300 transition-colors">
                Esqueceu a senha?
              </a>
            </div>
          </div>

          <div *ngIf="errorMessage" class="rounded-lg bg-red-50 dark:bg-red-900/20 p-4">
            <p class="text-sm text-red-800 dark:text-red-300">{{ errorMessage }}</p>
          </div>

          <div>
            <button
              type="submit"
              [disabled]="loginForm.invalid || isLoading"
              class="group relative w-full flex justify-center py-3 px-4 border border-transparent text-sm font-semibold rounded-lg text-white bg-accent-500 hover:bg-accent-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-accent-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
            >
              <span *ngIf="!isLoading">Entrar</span>
              <span *ngIf="isLoading" class="flex items-center">
                <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647A7.962 7.962 0 0112 20c4.418 0 8-3.582 8-8h-4a4 4 0 00-4 4v4z"></path>
                </svg>
                Entrando...
              </span>
            </button>
          </div>

          <div class="text-center">
            <p class="text-sm text-slate-600 dark:text-slate-400">
              Não tem uma conta?
              <a routerLink="/register" class="font-medium text-accent-500 hover:text-accent-600 dark:text-accent-400 dark:hover:text-accent-300 transition-colors">
                Criar conta
              </a>
            </p>
          </div>
        </form>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class LoginComponent implements OnInit { // Classe que controla o formulário.
  loginForm!: FormGroup; // Formulário reativo.
  isLoading = false; // Flag usada para desabilitar o botão/enviar spinner.
  errorMessage = ''; // Mensagem de erro apresentada ao usuário.
  isEmail = false; // Indica se o campo está em formato de e-mail.
  
  private fb = inject(FormBuilder); // Injeção do FormBuilder via API funcional.
  private router = inject(Router); // Navegação programática pós-login.
  private authService = inject(AuthService); // Serviço que faz as requisições de login.

  ngOnInit(): void { // Inicializa o formulário quando o componente é criado.
    this.loginForm = this.fb.group({
      identifier: ['', [Validators.required]], // Campo obrigatório (email ou CPF).
      password: ['', [Validators.required]], // Campo obrigatório para a senha.
      rememberMe: [false] // Checkbox opcional.
    });
  }

  onIdentifierInput(event: any): void { // Formata o CPF dinamicamente e detecta e-mail.
    const value = event.target.value; // Conteúdo atual do campo.
    this.isEmail = value.includes('@'); // Considera e-mail quando há @.
    
    if (!this.isEmail && value) { // Apenas formata se for CPF.
      let cpf = value.replace(/\D/g, ''); // Remove caracteres não numéricos.
      if (cpf.length <= 11) { // Aplica máscara enquanto digita.
        cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
        cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
        cpf = cpf.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
        this.loginForm.patchValue({ identifier: cpf }, { emitEvent: false }); // Atualiza o campo sem disparar novos eventos.
      }
    }
  }

  onSubmit(): void { // Handler do submit do formulário.
    if (this.loginForm.invalid) { // Evita submissão se houver campos inválidos.
      return;
    }

    this.isLoading = true; // Exibe spinner.
    this.errorMessage = ''; // Limpa mensagens anteriores.

    const { identifier, password } = this.loginForm.value; // Desestrutura os valores do formulário.

    this.authService.login(identifier, password).subscribe({ // Chama o backend.
      next: (response) => { // Sucesso na requisição.
        if (response.success) { // Backend confirmou o login.
          const userType = response.userType; // Identifica o tipo de usuário.
          
          if (userType === 'admin') { // Fluxo para admin.
            this.authService.setAdmin(response.admin, response.token);
            this.router.navigate(['/admin']);
          } else if (userType === 'cliente') { // Fluxo para cliente.
            this.authService.setCliente(response.cliente, response.token);
            this.router.navigate(['/']);
          } else if (userType === 'prestador') { // Fluxo para prestador.
            this.authService.setPrestador(response.prestador, response.token);
            this.router.navigate(['/']);
          } else { // Caso inesperado.
            this.errorMessage = 'Tipo de usuário não reconhecido';
            this.isLoading = false;
          }
        } else { // Backend retornou sucesso=false.
          this.errorMessage = response.message || 'Email/CPF ou senha inválidos';
          this.isLoading = false;
        }
      },
      error: (err) => { // Erros de rede ou HTTP.
        if (err.status === 0) {
          this.errorMessage = 'Erro ao conectar com o servidor. Verifique se o backend está rodando.';
        } else if (err.status === 401) {
          this.errorMessage = err.error?.message || 'Email/CPF ou senha incorretos';
        } else {
          this.errorMessage = 'Erro ao fazer login. Tente novamente.';
        }
        this.isLoading = false; // Sempre reabilita o botão.
      }
    });
  }
}
