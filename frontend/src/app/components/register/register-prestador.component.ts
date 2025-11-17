import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { PrestadorService } from '../../services/prestador.service';

@Component({
  selector: 'app-register-prestador',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, NavbarComponent],
  template: `
    <div class="min-h-screen bg-white dark:bg-dark-bg">
      <app-navbar></app-navbar>
      <div class="flex items-center justify-center min-h-screen px-4 sm:px-6 lg:px-8 py-12 pt-20">
        <div class="max-w-2xl w-full">
        <!-- Logo e Cabeçalho -->
        <div class="text-center mb-8">
          <div class="flex justify-center mb-4">
            <img 
              src="assets/logo-homehero.png" 
              alt="HomeHero" 
              class="h-16 w-auto"
            />
          </div>
          <h2 class="text-2xl font-display font-bold text-primary-900 dark:text-white">
            Cadastro de Prestador
          </h2>
          <p class="mt-2 text-sm text-slate-600 dark:text-slate-400">
            Preencha os dados para criar sua conta de prestador
          </p>
        </div>

        <!-- Formulário de Cadastro -->
        <form [formGroup]="registerForm" (ngSubmit)="onSubmit()" class="bg-white dark:bg-dark-surface rounded-2xl shadow-sm dark:shadow-primary-950 p-8 space-y-6">
          <!-- Informações Pessoais -->
          <div>
            <h3 class="text-lg font-semibold text-primary-900 dark:text-white mb-4">Informações Pessoais</h3>
            <div class="space-y-4">
              <!-- Nome Completo -->
              <div>
                <label for="nomeCompleto" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Nome Completo <span class="text-red-500">*</span>
                </label>
                <input
                  id="nomeCompleto"
                  type="text"
                  formControlName="nomeCompleto"
                  required
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                  placeholder="João Silva Santos"
                />
                <div *ngIf="registerForm.get('nomeCompleto')?.invalid && registerForm.get('nomeCompleto')?.touched" 
                     class="mt-1 text-sm text-red-600 dark:text-red-400">
                  Nome completo é obrigatório
                </div>
              </div>

              <!-- CPF -->
              <div>
                <label for="cpf" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  CPF <span class="text-red-500">*</span>
                </label>
                <input
                  id="cpf"
                  type="text"
                  formControlName="cpf"
                  maxlength="14"
                  (input)="formatCPF($event)"
                  required
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                  placeholder="000.000.000-00"
                />
                <div *ngIf="registerForm.get('cpf')?.invalid && registerForm.get('cpf')?.touched" 
                     class="mt-1 text-sm text-red-600 dark:text-red-400">
                  CPF inválido
                </div>
              </div>

              <!-- Data de Nascimento -->
              <div>
                <label for="dataNascimento" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Data de Nascimento
                </label>
                <input
                  id="dataNascimento"
                  type="date"
                  formControlName="dataNascimento"
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                />
              </div>
            </div>
          </div>

          <!-- Informações Profissionais -->
          <div>
            <h3 class="text-lg font-semibold text-primary-900 dark:text-white mb-4">Informações Profissionais</h3>
            <div class="space-y-4">
              <!-- Áreas de Atuação -->
              <div>
                <label for="areasAtuacao" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Áreas de Atuação
                </label>
                <input
                  id="areasAtuacao"
                  type="text"
                  formControlName="areasAtuacao"
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                  placeholder="Ex: Encanamento, Elétrica, Pintura"
                />
                <p class="mt-1 text-xs text-slate-500 dark:text-slate-400">
                  Separe as áreas por vírgula
                </p>
              </div>

              <!-- Experiência -->
              <div>
                <label for="experiencia" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Experiência
                </label>
                <textarea
                  id="experiencia"
                  formControlName="experiencia"
                  rows="4"
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors resize-none"
                  placeholder="Descreva sua experiência profissional..."
                ></textarea>
              </div>

              <!-- Certificados -->
              <div>
                <label for="certificados" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Certificados
                </label>
                <textarea
                  id="certificados"
                  formControlName="certificados"
                  rows="3"
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors resize-none"
                  placeholder="Liste seus certificados e qualificações..."
                ></textarea>
                <p class="mt-1 text-xs text-slate-500 dark:text-slate-400">
                  Separe os certificados por vírgula ou linha
                </p>
              </div>
            </div>
          </div>

          <!-- Informações de Contato -->
          <div>
            <h3 class="text-lg font-semibold text-primary-900 dark:text-white mb-4">Contato</h3>
            <div class="space-y-4">
              <!-- Email -->
              <div>
                <label for="email" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Email <span class="text-red-500">*</span>
                </label>
                <input
                  id="email"
                  type="email"
                  formControlName="email"
                  autocomplete="email"
                  required
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                  placeholder="seu@email.com"
                />
                <div *ngIf="registerForm.get('email')?.invalid && registerForm.get('email')?.touched" 
                     class="mt-1 text-sm text-red-600 dark:text-red-400">
                  Email inválido
                </div>
              </div>

              <!-- Telefone -->
              <div>
                <label for="telefone" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Telefone <span class="text-red-500">*</span>
                </label>
                <input
                  id="telefone"
                  type="tel"
                  formControlName="telefone"
                  maxlength="15"
                  (input)="formatPhone($event)"
                  required
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                  placeholder="(00) 00000-0000"
                />
                <div *ngIf="registerForm.get('telefone')?.invalid && registerForm.get('telefone')?.touched" 
                     class="mt-1 text-sm text-red-600 dark:text-red-400">
                  Telefone inválido
                </div>
              </div>
            </div>
          </div>

          <!-- Informações de Endereço -->
          <div>
            <h3 class="text-lg font-semibold text-primary-900 dark:text-white mb-4">Endereço</h3>
            <div class="space-y-4">
              <!-- CEP -->
              <div>
                <label for="cep" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  CEP
                </label>
                <input
                  id="cep"
                  type="text"
                  formControlName="cep"
                  maxlength="9"
                  (input)="formatCEP($event)"
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                  placeholder="00000-000"
                />
              </div>

              <!-- Logradouro -->
              <div>
                <label for="logradouro" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Logradouro
                </label>
                <input
                  id="logradouro"
                  type="text"
                  formControlName="logradouro"
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                  placeholder="Rua, Avenida, etc."
                />
              </div>

              <div class="grid grid-cols-2 gap-4">
                <!-- Número -->
                <div>
                  <label for="numero" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                    Número
                  </label>
                  <input
                    id="numero"
                    type="text"
                    formControlName="numero"
                    class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                    placeholder="123"
                  />
                </div>

                <!-- Complemento -->
                <div>
                  <label for="complemento" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                    Complemento
                  </label>
                  <input
                    id="complemento"
                    type="text"
                    formControlName="complemento"
                    class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                    placeholder="Apto, Bloco, etc."
                  />
                </div>
              </div>

              <div class="grid grid-cols-2 gap-4">
                <!-- Bairro -->
                <div>
                  <label for="bairro" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                    Bairro
                  </label>
                  <input
                    id="bairro"
                    type="text"
                    formControlName="bairro"
                    class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                    placeholder="Bairro"
                  />
                </div>

                <!-- Cidade -->
                <div>
                  <label for="cidade" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                    Cidade
                  </label>
                  <input
                    id="cidade"
                    type="text"
                    formControlName="cidade"
                    class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                    placeholder="Cidade"
                  />
                </div>
              </div>

              <!-- UF -->
              <div>
                <label for="uf" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Estado (UF)
                </label>
                <select
                  id="uf"
                  formControlName="uf"
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                >
                  <option value="">Selecione</option>
                  <option value="AC">AC</option>
                  <option value="AL">AL</option>
                  <option value="AP">AP</option>
                  <option value="AM">AM</option>
                  <option value="BA">BA</option>
                  <option value="CE">CE</option>
                  <option value="DF">DF</option>
                  <option value="ES">ES</option>
                  <option value="GO">GO</option>
                  <option value="MA">MA</option>
                  <option value="MT">MT</option>
                  <option value="MS">MS</option>
                  <option value="MG">MG</option>
                  <option value="PA">PA</option>
                  <option value="PB">PB</option>
                  <option value="PR">PR</option>
                  <option value="PE">PE</option>
                  <option value="PI">PI</option>
                  <option value="RJ">RJ</option>
                  <option value="RN">RN</option>
                  <option value="RS">RS</option>
                  <option value="RO">RO</option>
                  <option value="RR">RR</option>
                  <option value="SC">SC</option>
                  <option value="SP">SP</option>
                  <option value="SE">SE</option>
                  <option value="TO">TO</option>
                </select>
              </div>
            </div>
          </div>

          <!-- Informações da Conta -->
          <div>
            <h3 class="text-lg font-semibold text-primary-900 dark:text-white mb-4">Dados da Conta</h3>
            <div class="space-y-4">
              <!-- Senha -->
              <div>
                <label for="senha" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Senha <span class="text-red-500">*</span>
                </label>
                <input
                  id="senha"
                  type="password"
                  formControlName="senha"
                  autocomplete="new-password"
                  required
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                  placeholder="Mínimo 6 caracteres"
                />
                <div *ngIf="registerForm.get('senha')?.invalid && registerForm.get('senha')?.touched" 
                     class="mt-1 text-sm text-red-600 dark:text-red-400">
                  A senha deve ter pelo menos 6 caracteres
                </div>
              </div>

              <!-- Confirmar Senha -->
              <div>
                <label for="confirmarSenha" class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                  Confirmar Senha <span class="text-red-500">*</span>
                </label>
                <input
                  id="confirmarSenha"
                  type="password"
                  formControlName="confirmarSenha"
                  autocomplete="new-password"
                  required
                  class="appearance-none relative block w-full px-4 py-3 border border-slate-300 dark:border-dark-border placeholder-slate-500 dark:placeholder-slate-400 text-slate-900 dark:text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 focus:border-accent-500 dark:bg-dark-input dark:focus:ring-accent-400 transition-colors"
                  placeholder="Digite a senha novamente"
                />
                <div *ngIf="registerForm.get('confirmarSenha')?.invalid && registerForm.get('confirmarSenha')?.touched" 
                     class="mt-1 text-sm text-red-600 dark:text-red-400">
                  As senhas não coincidem
                </div>
              </div>
            </div>
          </div>

          <!-- Mensagem de Erro -->
          <div *ngIf="errorMessage" class="rounded-lg bg-red-50 dark:bg-red-900/20 p-4">
            <p class="text-sm text-red-800 dark:text-red-300">{{ errorMessage }}</p>
          </div>

          <!-- Botão de Envio -->
          <div class="flex gap-4">
            <button
              type="button"
              routerLink="/register"
              class="flex-1 px-6 py-3 border border-slate-300 dark:border-dark-border text-slate-700 dark:text-slate-300 font-semibold rounded-lg hover:bg-slate-50 dark:hover:bg-primary-800 transition-colors"
            >
              Voltar
            </button>
            <button
              type="submit"
              [disabled]="registerForm.invalid || isLoading"
              class="flex-1 px-6 py-3 bg-accent-500 text-white font-semibold rounded-lg hover:bg-accent-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-accent-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
            >
              <span *ngIf="!isLoading">Criar Conta</span>
              <span *ngIf="isLoading" class="flex items-center justify-center">
                <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647A7.962 7.962 0 0112 20c4.418 0 8-3.582 8-8h-4a4 4 0 00-4 4v4z"></path>
                </svg>
                Criando...
              </span>
            </button>
          </div>
        </form>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class RegisterPrestadorComponent implements OnInit {
  registerForm!: FormGroup;
  isLoading = false;
  errorMessage = '';
  
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private prestadorService = inject(PrestadorService);

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      nomeCompleto: ['', [Validators.required]],
      cpf: ['', [Validators.required, this.cpfValidator]],
      dataNascimento: [''],
      areasAtuacao: [''],
      experiencia: [''],
      certificados: [''],
      email: ['', [Validators.required, Validators.email]],
      telefone: ['', [Validators.required]],
      cep: [''],
      logradouro: [''],
      numero: [''],
      complemento: [''],
      bairro: [''],
      cidade: [''],
      uf: [''],
      senha: ['', [Validators.required, Validators.minLength(6)]],
      confirmarSenha: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  cpfValidator(control: any) {
    const cpf = control.value?.replace(/\D/g, '');
    if (!cpf || cpf.length !== 11) {
      return { invalidCpf: true };
    }
    return null;
  }

  passwordMatchValidator(form: FormGroup) {
    const senha = form.get('senha');
    const confirmarSenha = form.get('confirmarSenha');
    if (senha && confirmarSenha && senha.value !== confirmarSenha.value) {
      confirmarSenha.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    }
    return null;
  }

  formatCPF(event: any): void {
    let value = event.target.value.replace(/\D/g, '');
    if (value.length <= 11) {
      value = value.replace(/(\d{3})(\d)/, '$1.$2');
      value = value.replace(/(\d{3})(\d)/, '$1.$2');
      value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
      this.registerForm.patchValue({ cpf: value }, { emitEvent: false });
    }
  }

  formatPhone(event: any): void {
    let value = event.target.value.replace(/\D/g, '');
    if (value.length <= 11) {
      value = value.replace(/(\d{2})(\d)/, '($1) $2');
      value = value.replace(/(\d{5})(\d)/, '$1-$2');
      this.registerForm.patchValue({ telefone: value }, { emitEvent: false });
    }
  }

  formatCEP(event: any): void {
    let value = event.target.value.replace(/\D/g, '');
    if (value.length <= 8) {
      value = value.replace(/(\d{5})(\d)/, '$1-$2');
      this.registerForm.patchValue({ cep: value }, { emitEvent: false });
    }
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    const formValue = this.registerForm.value;
    
    this.prestadorService.cadastrar(formValue).subscribe({
      next: (response) => {
        this.isLoading = false;
        if (response.success) {
          alert('Conta criada com sucesso!');
          this.router.navigate(['/login']);
        } else {
          this.errorMessage = response.message || 'Erro ao criar conta';
        }
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMessage = error.error?.message || 'Erro ao criar conta. Tente novamente.';
        console.error('Erro ao cadastrar:', error);
      }
    });
  }
}

