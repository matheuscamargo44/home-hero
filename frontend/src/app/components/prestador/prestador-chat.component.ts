import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';

type AutorMensagem = 'prestador' | 'cliente' | 'sistema';

interface Conversa {
  id: number;
  cliente: string;
  servico: string;
  status: 'confirmado' | 'em-andamento' | 'pendente';
  ultimaMensagem: string;
  tempo: string;
  unread?: number;
  bairro: string;
}

interface Mensagem {
  id: number;
  autor: AutorMensagem;
  conteudo: string;
  horario: string;
}

@Component({
  selector: 'app-prestador-chat',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent],
  template: `
    <div class="min-h-screen bg-white dark:bg-dark-bg">
      <app-navbar></app-navbar>

      <main class="pt-24 pb-12 px-4 sm:px-6 lg:px-8">
        <div class="max-w-6xl mx-auto space-y-8">
          <section class="flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
            <div>
              <p class="text-xs font-semibold uppercase tracking-[0.2em] text-slate-500 dark:text-slate-400">
                Conversas com clientes
              </p>
              <h1 class="text-3xl md:text-4xl font-display font-semibold text-primary-900 dark:text-white mt-2">
                Central de mensagens
              </h1>
              <p class="text-slate-500 dark:text-slate-400 mt-2 max-w-2xl">
                Tire dúvidas, confirme detalhes e envie atualizações em tempo real.
              </p>
            </div>
            <div class="flex flex-wrap gap-3 text-sm">
              <a
                routerLink="/prestador/agendamentos"
                class="inline-flex items-center gap-2 rounded-full border border-slate-200 dark:border-dark-border px-5 py-2.5 text-sm font-medium text-slate-700 dark:text-slate-200 hover:border-primary-300 dark:hover:border-slate-500 transition-colors"
              >
                <span class="h-2 w-2 rounded-full bg-primary-500"></span>
                Ver agenda
              </a>
              <button
                type="button"
                class="inline-flex items-center gap-2 rounded-full bg-accent-500 px-6 py-2.5 text-sm font-semibold text-white shadow-lg shadow-accent-500/20 hover:bg-accent-600 transition-colors"
              >
                Nova conversa
              </button>
            </div>
          </section>

          <section class="rounded-3xl border border-slate-100 dark:border-dark-border bg-white dark:bg-dark-surface shadow-sm overflow-hidden">
            <div class="flex flex-col lg:flex-row">
              <aside class="w-full lg:w-80 border-b lg:border-b-0 lg:border-r border-slate-100 dark:border-dark-border p-4 space-y-4">
                <div class="rounded-2xl border border-slate-100 dark:border-slate-700 bg-slate-50 dark:bg-dark-bg/40 px-4 py-3">
                  <p class="text-xs uppercase tracking-[0.3em] text-slate-400 dark:text-slate-500">Status</p>
                  <div class="flex items-center justify-between mt-2">
                    <span class="text-3xl font-display font-semibold text-primary-900 dark:text-white">{{ conversas.length }}</span>
                    <span class="text-xs font-semibold text-green-500">3 ativos</span>
                  </div>
                </div>

                <div class="relative">
                  <input
                    type="search"
                    placeholder="Buscar cliente ou serviço..."
                    class="w-full rounded-2xl border border-slate-200 dark:border-slate-700 bg-transparent px-4 py-2.5 text-sm text-slate-600 dark:text-slate-200 placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-primary-100 dark:focus:ring-primary-900/30"
                  />
                  <span class="absolute right-4 top-1/2 -translate-y-1/2 text-slate-400 text-xs uppercase tracking-[0.3em]">CMD K</span>
                </div>

                <div class="space-y-3">
                  <button
                    *ngFor="let conversa of conversas"
                    type="button"
                    (click)="setConversaAtiva(conversa.id)"
                    class="w-full text-left rounded-2xl border px-4 py-3 transition-all"
                    [ngClass]="{
                      'border-primary-200 bg-primary-50/70 dark:bg-primary-900/20 shadow-sm': conversa.id === conversaAtiva?.id,
                      'border-slate-100 dark:border-slate-700 hover:border-primary-200': conversa.id !== conversaAtiva?.id
                    }"
                  >
                    <div class="flex items-center justify-between">
                      <div>
                        <p class="text-sm font-semibold text-primary-900 dark:text-white">{{ conversa.cliente }}</p>
                        <p class="text-xs text-slate-500 dark:text-slate-400">{{ conversa.servico }}</p>
                      </div>
                      <span class="text-[11px] uppercase tracking-[0.2em] text-slate-400">{{ conversa.tempo }}</span>
                    </div>
                    <p class="mt-2 text-xs text-slate-500 dark:text-slate-300 line-clamp-2">
                      {{ conversa.ultimaMensagem }}
                    </p>
                    <div class="mt-2 flex items-center justify-between text-xs">
                      <span class="text-slate-400">{{ conversa.bairro }}</span>
                      <span
                        *ngIf="conversa.unread"
                        class="inline-flex h-5 min-w-[20px] items-center justify-center rounded-full bg-primary-900 text-white text-[10px] font-semibold"
                      >
                        {{ conversa.unread }}
                      </span>
                    </div>
                  </button>
                </div>
              </aside>

              <section class="flex-1 flex flex-col">
                <header class="border-b border-slate-100 dark:border-dark-border px-6 py-5 flex flex-col gap-3 md:flex-row md:items-center md:justify-between">
                  <div>
                    <p class="text-xs uppercase tracking-[0.3em] text-slate-400 dark:text-slate-500">Chat com</p>
                    <h2 class="text-2xl font-display font-semibold text-primary-900 dark:text-white">
                      {{ conversaAtiva?.cliente }}
                    </h2>
                    <p class="text-sm text-slate-500 dark:text-slate-400">
                      {{ conversaAtiva?.servico }} • {{ conversaAtiva?.bairro }}
                    </p>
                  </div>
                  <div class="flex flex-wrap gap-3 text-sm">
                    <a
                      routerLink="/prestador/agendamentos"
                      class="inline-flex items-center rounded-full border border-slate-200 dark:border-slate-700 px-4 py-2 font-medium text-slate-600 dark:text-slate-200 hover:border-primary-300"
                    >
                      Ver agendamento
                    </a>
                    <button class="inline-flex items-center rounded-full border border-slate-200 dark:border-slate-700 px-4 py-2 font-medium text-slate-600 dark:text-slate-200 hover:border-primary-300">
                      Enviar orçamento
                    </button>
                  </div>
                </header>

                <div class="flex-1 overflow-y-auto bg-slate-50 dark:bg-dark-bg/40 px-6 py-6 space-y-4">
                  <ng-container *ngFor="let mensagem of mensagensAtivas">
                    <div
                      *ngIf="mensagem.autor === 'sistema'; else bubble"
                      class="text-center text-[11px] uppercase tracking-[0.3em] text-slate-400 dark:text-slate-500"
                    >
                      {{ mensagem.conteudo }} • {{ mensagem.horario }}
                    </div>
                    <ng-template #bubble>
                      <div
                        class="flex"
                        [ngClass]="{
                          'justify-end': mensagem.autor === 'prestador',
                          'justify-start': mensagem.autor === 'cliente'
                        }"
                      >
                        <div
                          class="max-w-md rounded-2xl px-4 py-3 text-sm shadow-sm"
                          [ngClass]="mensagem.autor === 'prestador'
                            ? 'bg-primary-900 text-white rounded-br-none'
                            : 'bg-white text-primary-900 border border-slate-100 dark:bg-dark-surface dark:border-dark-border rounded-bl-none text-slate-800 dark:text-slate-100'"
                        >
                          <p class="leading-relaxed">
                            {{ mensagem.conteudo }}
                          </p>
                          <span
                            class="mt-2 block text-[11px] uppercase tracking-[0.2em]"
                            [ngClass]="mensagem.autor === 'prestador'
                              ? 'text-primary-200'
                              : 'text-slate-400 dark:text-slate-500'"
                          >
                            {{ mensagem.horario }}
                          </span>
                        </div>
                      </div>
                    </ng-template>
                  </ng-container>
                </div>

                <footer class="border-t border-slate-100 dark:border-dark-border px-6 py-5 space-y-4">
                  <div class="flex flex-wrap gap-2 text-xs">
                    <span class="inline-flex items-center gap-2 rounded-full border border-slate-200 dark:border-slate-700 px-3 py-1 text-slate-500 dark:text-slate-300">
                      📎 Anexar fotos
                    </span>
                    <span class="inline-flex items-center gap-2 rounded-full border border-slate-200 dark:border-slate-700 px-3 py-1 text-slate-500 dark:text-slate-300">
                      ✅ Atualizar status
                    </span>
                    <span class="inline-flex items-center gap-2 rounded-full border border-slate-200 dark:border-slate-700 px-3 py-1 text-slate-500 dark:text-slate-300">
                      🔁 Mensagem rápida
                    </span>
                  </div>
                  <div class="flex flex-col md:flex-row md:items-center md:gap-3">
                    <textarea
                      rows="2"
                      placeholder="Escreva uma resposta gentil..."
                      class="flex-1 rounded-2xl border border-slate-200 dark:border-slate-700 bg-transparent px-4 py-3 text-sm text-slate-600 dark:text-slate-100 placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-primary-100 dark:focus:ring-primary-900/30 resize-none"
                    ></textarea>
                    <button
                      type="button"
                      class="mt-3 md:mt-0 rounded-2xl bg-accent-500 px-8 py-3 text-sm font-semibold text-white shadow-lg shadow-accent-500/20 hover:bg-accent-600 transition-colors"
                    >
                      Enviar
                    </button>
                  </div>
                </footer>
              </section>
            </div>
          </section>
        </div>
      </main>
    </div>
  `,
  styles: []
})
export class PrestadorChatComponent {
  conversas: Conversa[] = [
    {
      id: 1,
      cliente: 'Ana Souza',
      servico: 'Instalação de luminária inteligente',
      status: 'confirmado',
      ultimaMensagem: 'Perfeito! Te aviso quando estiver chegando.',
      tempo: '2 min',
      unread: 1,
      bairro: 'Vila Mariana'
    },
    {
      id: 2,
      cliente: 'Felipe Ramos',
      servico: 'Reparo em disjuntor',
      status: 'em-andamento',
      ultimaMensagem: 'Tudo certo, podem subir direto?',
      tempo: '15 min',
      bairro: 'Pinheiros'
    },
    {
      id: 3,
      cliente: 'Marina Costa',
      servico: 'Revisão elétrica',
      status: 'pendente',
      ultimaMensagem: 'Enviei algumas fotos da área que precisa de atenção.',
      tempo: '1h',
      unread: 2,
      bairro: 'Jardins'
    }
  ];

  mensagensPorConversa: Record<number, Mensagem[]> = {
    1: [
      { id: 1, autor: 'sistema', conteudo: 'Agendamento confirmado para 09:30', horario: '08:02' },
      { id: 2, autor: 'cliente', conteudo: 'Bom dia! Tudo certo para mais tarde?', horario: '08:05' },
      { id: 3, autor: 'prestador', conteudo: 'Bom dia Ana! Equipe separada e materiais revisados. Chegamos 15min antes.', horario: '08:07' },
      { id: 4, autor: 'cliente', conteudo: 'Perfeito! Te aviso quando estiver chegando.', horario: '08:11' }
    ],
    2: [
      { id: 5, autor: 'cliente', conteudo: 'Lembra de checar o quadro principal também?', horario: 'Ontem • 18:30' },
      { id: 6, autor: 'prestador', conteudo: 'Sim! Já deixei anotado para essa visita.', horario: 'Ontem • 18:34' }
    ],
    3: [
      { id: 7, autor: 'cliente', conteudo: 'Enviei algumas fotos para você avaliar antes.', horario: 'Ontem • 10:12' },
      { id: 8, autor: 'prestador', conteudo: 'Recebido! Já já te retorno com um orçamento estimado.', horario: 'Ontem • 10:18' }
    ]
  };

  conversaAtivaId = this.conversas[0].id;

  get conversaAtiva(): Conversa | undefined {
    return this.conversas.find((c) => c.id === this.conversaAtivaId);
  }

  get mensagensAtivas(): Mensagem[] {
    return this.mensagensPorConversa[this.conversaAtivaId] ?? [];
  }

  setConversaAtiva(id: number): void {
    this.conversaAtivaId = id;
  }
}




