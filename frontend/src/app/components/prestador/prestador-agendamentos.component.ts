import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';

type AgendamentoStatus = 'confirmado' | 'em-andamento' | 'pendente' | 'cancelado';

interface ResumoCard {
  titulo: string;
  valor: string;
  detalhe: string;
  tendencia: 'positivo' | 'neutro' | 'negativo';
}

interface Agendamento {
  horario: string;
  data: string;
  cliente: string;
  servico: string;
  endereco: string;
  status: AgendamentoStatus;
  notas?: string;
  valor: string;
  canal: 'App' | 'Web' | 'Recomendação';
}

@Component({
  selector: 'app-prestador-agendamentos',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent],
  template: `
    <div class="min-h-screen bg-white dark:bg-dark-bg">
      <app-navbar></app-navbar>

      <main class="pt-24 pb-16 px-4 sm:px-6 lg:px-8">
        <div class="max-w-6xl mx-auto space-y-10">
          <section class="flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
            <div>
              <p class="text-xs font-semibold uppercase tracking-[0.2em] text-slate-500 dark:text-slate-400">
                Painel do prestador
              </p>
              <h1 class="text-3xl md:text-4xl font-display font-semibold text-primary-900 dark:text-white mt-2">
                Agenda da semana
              </h1>
              <p class="text-slate-500 dark:text-slate-400 mt-2 max-w-2xl">
                Visualize seus próximos serviços, confirme horários e mantenha seus clientes atualizados.
              </p>
            </div>
            <div class="flex flex-wrap gap-3">
              <a
                routerLink="/prestador/chat"
                class="inline-flex items-center gap-2 rounded-full border border-slate-200 dark:border-dark-border px-5 py-2.5 text-sm font-medium text-slate-700 dark:text-slate-200 hover:border-primary-300 dark:hover:border-slate-500 transition-colors"
              >
                <span class="h-2 w-2 rounded-full bg-green-400 animate-pulse"></span>
                Conversar com cliente
              </a>
              <button
                type="button"
                class="inline-flex items-center gap-2 rounded-full bg-accent-500 px-6 py-2.5 text-sm font-semibold text-white shadow-lg shadow-accent-500/20 hover:bg-accent-600 transition-colors"
              >
                Nova disponibilidade
              </button>
            </div>
          </section>

          <section class="grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
            <article
              *ngFor="let card of resumoCards"
              class="rounded-3xl border border-slate-100 dark:border-dark-border bg-white dark:bg-dark-surface p-6 shadow-sm"
            >
              <p class="text-xs uppercase tracking-[0.2em] text-slate-400 dark:text-slate-500">{{ card.titulo }}</p>
              <div class="flex items-baseline gap-2 mt-4">
                <span class="text-3xl font-display font-semibold text-primary-900 dark:text-white">{{ card.valor }}</span>
                <span
                  class="text-xs font-medium"
                  [ngClass]="{
                    'text-green-500 dark:text-green-400': card.tendencia === 'positivo',
                    'text-slate-500 dark:text-slate-400': card.tendencia === 'neutro',
                    'text-red-500 dark:text-red-400': card.tendencia === 'negativo'
                  }"
                >
                  {{ card.detalhe }}
                </span>
              </div>
            </article>
          </section>

          <section class="grid gap-6 lg:grid-cols-[1.3fr_0.7fr]">
            <article class="rounded-3xl border border-slate-100 dark:border-dark-border bg-white dark:bg-dark-surface shadow-sm">
              <header class="flex flex-col gap-2 border-b border-slate-100 dark:border-dark-border px-6 py-5 md:flex-row md:items-center md:justify-between">
                <div>
                  <p class="text-xs uppercase tracking-[0.3em] text-slate-400 dark:text-slate-500">Hoje</p>
                  <h2 class="text-xl font-display font-semibold text-primary-900 dark:text-white">Agendamentos do dia</h2>
                </div>
                <div class="flex items-center gap-3 text-sm">
                  <button class="rounded-full px-4 py-2 border border-slate-200 dark:border-slate-700 text-slate-600 dark:text-slate-300 hover:border-primary-300">Filtrar</button>
                  <button class="rounded-full px-4 py-2 border border-slate-200 dark:border-slate-700 text-slate-600 dark:text-slate-300 hover:border-primary-300">Exportar</button>
                </div>
              </header>

              <div class="divide-y divide-slate-100 dark:divide-dark-border">
                <div
                  *ngFor="let agendamento of agendamentosHoje; trackBy: trackByCliente"
                  class="flex flex-col gap-4 px-6 py-5 md:flex-row md:items-center md:justify-between"
                >
                  <div class="flex items-start gap-4">
                    <div class="flex h-14 w-14 flex-col items-center justify-center rounded-2xl border border-slate-100 dark:border-slate-700 bg-slate-50 dark:bg-dark-bg/40 text-sm font-semibold text-primary-900 dark:text-white">
                      <span>{{ agendamento.horario }}</span>
                      <span class="text-xs text-slate-500 dark:text-slate-400">{{ agendamento.data }}</span>
                    </div>
                    <div>
                      <div class="flex flex-wrap items-center gap-2">
                        <p class="text-base font-semibold text-primary-900 dark:text-white">{{ agendamento.servico }}</p>
                        <span
                          class="inline-flex items-center rounded-full px-3 py-1 text-xs font-medium"
                          [ngClass]="statusClasses[agendamento.status]"
                        >
                          {{ statusLabels[agendamento.status] }}
                        </span>
                      </div>
                      <p class="text-sm text-slate-500 dark:text-slate-400 mt-1">{{ agendamento.cliente }} • {{ agendamento.endereco }}</p>
                      <p *ngIf="agendamento.notas" class="text-sm text-slate-500 dark:text-slate-400 mt-1">
                        {{ agendamento.notas }}
                      </p>
                    </div>
                  </div>

                  <div class="flex flex-col gap-3 text-sm text-slate-600 dark:text-slate-300 md:text-right">
                    <p class="font-semibold">{{ agendamento.valor }}</p>
                    <p class="text-xs uppercase tracking-[0.25em]">{{ agendamento.canal }}</p>
                    <div class="flex gap-2 md:justify-end">
                      <button class="rounded-full border border-slate-200 dark:border-slate-700 px-4 py-2 text-xs font-medium hover:border-primary-300">Reagendar</button>
                      <button class="rounded-full bg-primary-900 text-white px-4 py-2 text-xs font-semibold hover:bg-primary-800">Iniciar</button>
                    </div>
                  </div>
                </div>
              </div>
            </article>

            <article class="rounded-3xl border border-slate-100 dark:border-dark-border bg-white dark:bg-dark-surface shadow-sm">
              <header class="border-b border-slate-100 dark:border-dark-border px-6 py-5">
                <p class="text-xs uppercase tracking-[0.3em] text-slate-400 dark:text-slate-500">Semana</p>
                <h2 class="text-xl font-display font-semibold text-primary-900 dark:text-white">Próximos serviços</h2>
              </header>

              <div class="px-6 py-5 space-y-4">
                <div
                  *ngFor="let agendamento of agendamentosSemana; trackBy: trackByCliente"
                  class="rounded-2xl border border-slate-100 dark:border-slate-700 p-4"
                >
                  <div class="flex items-center justify-between text-sm">
                    <p class="font-semibold text-primary-900 dark:text-white">{{ agendamento.data }} • {{ agendamento.horario }}</p>
                    <span
                      class="inline-flex items-center rounded-full px-3 py-1 text-[11px] font-semibold uppercase tracking-[0.2em]"
                      [ngClass]="statusClasses[agendamento.status]"
                    >
                      {{ statusLabels[agendamento.status] }}
                    </span>
                  </div>
                  <p class="mt-2 text-sm text-slate-500 dark:text-slate-400">{{ agendamento.servico }}</p>
                  <p class="text-sm font-medium text-primary-900 dark:text-white">{{ agendamento.cliente }}</p>
                  <p class="text-xs text-slate-400 mt-1">{{ agendamento.endereco }}</p>
                </div>
              </div>
            </article>
          </section>

          <section class="rounded-3xl border border-slate-100 dark:border-dark-border bg-white dark:bg-dark-surface p-6 shadow-sm">
            <div class="flex flex-col gap-3 md:flex-row md:items-center md:justify-between">
              <div>
                <p class="text-xs uppercase tracking-[0.3em] text-slate-400 dark:text-slate-500">Disponibilidades</p>
                <h2 class="text-xl font-display font-semibold text-primary-900 dark:text-white">Bloqueie e abra horários</h2>
              </div>
              <div class="flex items-center gap-3 text-sm">
                <button class="rounded-full border border-slate-200 dark:border-slate-700 px-4 py-2 font-medium hover:border-primary-300">
                  Sincronizar calendário
                </button>
                <button class="rounded-full bg-primary-900 text-white px-4 py-2 font-semibold hover:bg-primary-800">
                  Editar disponibilidade
                </button>
              </div>
            </div>

            <div class="mt-6 grid gap-4 md:grid-cols-3">
              <div
                *ngFor="let disponibilidade of disponibilidades"
                class="rounded-2xl border border-slate-100 dark:border-slate-700 p-5"
              >
                <p class="text-sm font-semibold text-primary-900 dark:text-white">{{ disponibilidade.dia }}</p>
                <p class="text-sm text-slate-500 dark:text-slate-400">{{ disponibilidade.periodo }}</p>
                <div class="mt-4 flex items-center justify-between text-xs font-semibold uppercase tracking-[0.2em]">
                  <span class="text-slate-500 dark:text-slate-400">{{ disponibilidade.demanda }}</span>
                  <span
                    class="rounded-full px-3 py-1"
                    [ngClass]="{
                      'bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-300': disponibilidade.status === 'Disponível',
                      'bg-yellow-50 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-200': disponibilidade.status === 'Limitado'
                    }"
                  >
                    {{ disponibilidade.status }}
                  </span>
                </div>
              </div>
            </div>
          </section>
        </div>
      </main>
    </div>
  `,
  styles: []
})
export class PrestadorAgendamentosComponent {
  resumoCards: ResumoCard[] = [
    { titulo: 'Hoje', valor: '04 serviços', detalhe: '+1 inserido', tendencia: 'positivo' },
    { titulo: 'Em andamento', valor: '02', detalhe: 'Atualize status', tendencia: 'neutro' },
    { titulo: 'Semana', valor: '12 visitas', detalhe: 'Agenda completa', tendencia: 'neutro' },
    { titulo: 'Satisfação', valor: '4.9 ★', detalhe: 'Últimos 30 dias', tendencia: 'positivo' }
  ];

  agendamentosHoje: Agendamento[] = [
    {
      horario: '09:30',
      data: 'Terça',
      cliente: 'Ana Souza',
      servico: 'Instalação de luminária inteligente',
      endereco: 'Vila Mariana • São Paulo',
      status: 'confirmado',
      notas: 'Cliente pediu confirmação 30min antes.',
      valor: 'R$ 280,00',
      canal: 'App'
    },
    {
      horario: '13:15',
      data: 'Terça',
      cliente: 'Felipe Ramos',
      servico: 'Reparo em disjuntor',
      endereco: 'Pinheiros • São Paulo',
      status: 'em-andamento',
      notas: 'Equipe está a caminho.',
      valor: 'R$ 450,00',
      canal: 'Web'
    },
    {
      horario: '16:40',
      data: 'Terça',
      cliente: 'Carla Menezes',
      servico: 'Instalação de tomada 220v',
      endereco: 'Brooklin • São Paulo',
      status: 'pendente',
      valor: 'R$ 320,00',
      canal: 'Recomendação'
    },
    {
      horario: '19:00',
      data: 'Terça',
      cliente: 'Rafael Lima',
      servico: 'Vistoria rápida',
      endereco: 'Moema • São Paulo',
      status: 'confirmado',
      valor: 'R$ 180,00',
      canal: 'App'
    }
  ];

  agendamentosSemana: Agendamento[] = [
    {
      horario: '08:00',
      data: 'Quarta • 27/11',
      cliente: 'Juliana Prado',
      servico: 'Troca de fiação',
      endereco: 'Tatuapé • São Paulo',
      status: 'confirmado',
      valor: 'R$ 820,00',
      canal: 'App'
    },
    {
      horario: '10:30',
      data: 'Quinta • 28/11',
      cliente: 'Eduardo Pires',
      servico: 'Instalação de ventilador',
      endereco: 'Perdizes • São Paulo',
      status: 'pendente',
      valor: 'R$ 250,00',
      canal: 'Web'
    },
    {
      horario: '14:15',
      data: 'Sexta • 29/11',
      cliente: 'Marina Costa',
      servico: 'Revisão elétrica',
      endereco: 'Jardins • São Paulo',
      status: 'confirmado',
      valor: 'R$ 590,00',
      canal: 'App'
    }
  ];

  disponibilidades = [
    { dia: 'Quarta-feira', periodo: '08h - 12h', status: 'Disponível', demanda: 'Alta' },
    { dia: 'Quinta-feira', periodo: '13h - 18h', status: 'Disponível', demanda: 'Moderada' },
    { dia: 'Sexta-feira', periodo: '08h - 17h', status: 'Limitado', demanda: 'Alta' }
  ];

  statusLabels: Record<AgendamentoStatus, string> = {
    confirmado: 'Confirmado',
    'em-andamento': 'Em andamento',
    pendente: 'Pendente',
    cancelado: 'Cancelado'
  };

  statusClasses: Record<AgendamentoStatus, string> = {
    confirmado: 'bg-green-50 text-green-700 dark:bg-green-900/30 dark:text-green-300',
    'em-andamento': 'bg-primary-50 text-primary-700 dark:bg-primary-900/30 dark:text-primary-200',
    pendente: 'bg-yellow-50 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-200',
    cancelado: 'bg-slate-100 text-slate-500 dark:bg-dark-bg dark:text-slate-400'
  };

  trackByCliente(_: number, item: Agendamento): string {
    return `${item.cliente}-${item.horario}`;
  }
}





