import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { DatabaseTestService } from '../../services/database-test.service';
import { AdminService } from '../../services/admin.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-database-test',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div class="w-full">
          <div class="mb-8">
            <div class="flex items-center mb-4">
              <button
                (click)="goBack()"
                class="mr-4 p-2 text-slate-600 dark:text-slate-400 hover:text-slate-900 dark:hover:text-white hover:bg-slate-100 dark:hover:bg-primary-800 rounded-lg transition-colors"
                title="Voltar ao painel"
              >
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
                </svg>
              </button>
              <div>
                <h1 class="text-4xl font-display font-bold text-primary-900 dark:text-white mb-2">
                  Banco de Dados
                </h1>
                <p class="text-slate-600 dark:text-slate-400">
                  Gerencie e teste visualizações, procedimentos armazenados e gatilhos do banco
                </p>
              </div>
            </div>
          </div>

          <div class="mb-6 border-b border-slate-200 dark:border-primary-800">
            <nav class="flex -mb-px space-x-8">
              <button
                (click)="activeTab = 'views'"
                [class]="'px-1 py-4 border-b-2 font-medium text-sm transition-colors ' + 
                  (activeTab === 'views' 
                    ? 'border-accent-500 text-accent-600 dark:text-accent-400' 
                    : 'border-transparent text-slate-500 hover:text-slate-700 hover:border-slate-300 dark:text-slate-400 dark:hover:text-slate-300')"
              >
                Views
              </button>
              <button
                (click)="activeTab = 'procedures'"
                [class]="'px-1 py-4 border-b-2 font-medium text-sm transition-colors ' + 
                  (activeTab === 'procedures' 
                    ? 'border-accent-500 text-accent-600 dark:text-accent-400' 
                    : 'border-transparent text-slate-500 hover:text-slate-700 hover:border-slate-300 dark:text-slate-400 dark:hover:text-slate-300')"
              >
                Procedures
              </button>
              <button
                (click)="activeTab = 'triggers'"
                [class]="'px-1 py-4 border-b-2 font-medium text-sm transition-colors ' + 
                  (activeTab === 'triggers' 
                    ? 'border-accent-500 text-accent-600 dark:text-accent-400' 
                    : 'border-transparent text-slate-500 hover:text-slate-700 hover:border-slate-300 dark:text-slate-400 dark:hover:text-slate-300')"
              >
                Trigger
              </button>
            </nav>
          </div>

          <div *ngIf="activeTab === 'views'" class="bg-white dark:bg-dark-surface rounded-lg shadow-sm border border-slate-200 dark:border-primary-800 p-6">
            <h2 class="text-2xl font-display font-bold text-primary-900 dark:text-white mb-6">View</h2>
            
            <div *ngIf="loadingViews" class="text-center py-12">
              <svg class="animate-spin h-8 w-8 text-accent-500 mx-auto" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647A7.962 7.962 0 0112 20c4.418 0 8-3.582 8-8h-4a4 4 0 00-4 4v4z"></path>
              </svg>
              <p class="mt-4 text-slate-600 dark:text-slate-400">Carregando visualizações...</p>
            </div>
            
            <div *ngIf="!loadingViews && views.length > 0" class="space-y-4">
              <div *ngFor="let view of views" class="border border-slate-200 dark:border-primary-800 rounded-lg p-4 hover:shadow-md transition-shadow">
                <div class="flex justify-between items-center">
                  <div>
                    <h3 class="font-semibold text-lg text-primary-900 dark:text-white">
                      {{ formatDisplayName(view, 'view_') }}
                    </h3>
                  </div>
                  <button
                    (click)="executeView(view)"
                    [disabled]="loading"
                    class="px-4 py-2 bg-accent-500 text-white font-semibold rounded-lg hover:bg-accent-600 disabled:opacity-50 disabled:cursor-not-allowed transition-colors text-sm"
                  >
                    <span *ngIf="!loading">Executar</span>
                    <span *ngIf="loading" class="flex items-center">
                      <svg class="animate-spin -ml-1 mr-2 h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647A7.962 7.962 0 0112 20c4.418 0 8-3.582 8-8h-4a4 4 0 00-4 4v4z"></path>
                      </svg>
                      Executando...
                    </span>
                  </button>
                </div>
              </div>
            </div>
            <div *ngIf="!loadingViews && views.length === 0" class="text-center py-12 text-slate-500 dark:text-slate-400">
              Nenhuma visualização encontrada
            </div>

            <div *ngIf="viewResults && viewResults.success" class="mt-8" data-results-view>
              <div class="flex justify-between items-center mb-4">
                <h3 class="font-semibold text-lg text-primary-900 dark:text-white">
                  Resultados ({{ viewResults.count || viewResults.data?.length || 0 }} registros)
                </h3>
                <button
                  (click)="viewResults = null"
                  class="px-4 py-2 text-sm text-slate-600 dark:text-slate-400 hover:text-slate-800 dark:hover:text-slate-200 hover:bg-slate-100 dark:hover:bg-primary-800 rounded-lg transition-colors"
                >
                  Limpar Resultados
                </button>
              </div>
              <div *ngIf="viewResults.data && viewResults.data.length > 0" class="overflow-x-auto border border-slate-200 dark:border-primary-800 rounded-lg shadow-sm">
                <table class="min-w-full divide-y divide-slate-200 dark:divide-primary-800">
                  <thead class="bg-slate-50 dark:bg-primary-900">
                    <tr>
                      <th *ngFor="let key of getViewKeys()" class="px-4 py-3 text-left text-xs font-medium text-slate-700 dark:text-slate-300 uppercase tracking-wider">
                        {{ formatColumnName(key) }}
                      </th>
                    </tr>
                  </thead>
                    <tbody class="bg-white dark:bg-dark-surface divide-y divide-slate-200 dark:divide-primary-800">
                    <tr *ngFor="let row of viewResults.data" class="hover:bg-slate-50 dark:hover:bg-primary-900 transition-colors">
                      <td *ngFor="let key of getViewKeys()" class="px-4 py-3 text-sm text-slate-900 dark:text-white">
                        {{ formatCellValue(row[key]) }}
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div *ngIf="!viewResults.data || viewResults.data.length === 0" class="text-center py-8 border border-slate-200 dark:border-primary-800 rounded-lg bg-slate-50 dark:bg-primary-900">
                <p class="text-slate-500 dark:text-slate-400 mb-2 font-medium">Nenhum resultado encontrado</p>
                <p class="text-sm text-slate-400 dark:text-slate-500" *ngIf="viewResults.message">{{ viewResults.message }}</p>
              </div>
            </div>
            <div *ngIf="viewResults && !viewResults.success" class="mt-8 p-4 bg-slate-50 dark:bg-primary-900 border border-slate-200 dark:border-primary-800 rounded-lg">
              <p class="text-slate-800 dark:text-slate-300">{{ viewResults.message || 'Erro ao executar visualização' }}</p>
            </div>
          </div>

          <div *ngIf="activeTab === 'procedures'" class="bg-white dark:bg-dark-surface rounded-lg shadow-sm border border-slate-200 dark:border-primary-800 p-6">
            <h2 class="text-2xl font-display font-bold text-primary-900 dark:text-white mb-6">Procedure</h2>
            
            <div *ngIf="loadingProcedures" class="text-center py-12">
              <svg class="animate-spin h-8 w-8 text-accent-500 mx-auto" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647A7.962 7.962 0 0112 20c4.418 0 8-3.582 8-8h-4a4 4 0 00-4 4v4z"></path>
              </svg>
              <p class="mt-4 text-slate-600 dark:text-slate-400">Carregando procedimentos...</p>
            </div>
            
            <div *ngIf="!loadingProcedures && procedures.length > 0" class="space-y-4">
              <div *ngFor="let procedure of procedures" class="border border-slate-200 dark:border-primary-800 rounded-lg p-4 hover:shadow-md transition-shadow">
                <div class="flex justify-between items-center">
                  <div>
                    <h3 class="font-semibold text-lg text-primary-900 dark:text-white">
                      {{ formatDisplayName(procedure) }}
                    </h3>
                  </div>
                  <button
                    (click)="openProcedureModal(procedure)"
                    class="px-4 py-2 bg-accent-500 text-white font-semibold rounded-lg hover:bg-accent-600 transition-colors text-sm"
                  >
                    Executar
                  </button>
                </div>
              </div>
            </div>
            <div *ngIf="!loadingProcedures && procedures.length === 0" class="text-center py-12 text-slate-500 dark:text-slate-400">
              Nenhum procedimento encontrado
            </div>

            <div *ngIf="procedureResult" class="mt-8" data-results-procedure>
              <div class="flex justify-between items-center mb-4">
                <h3 class="font-semibold text-lg text-primary-900 dark:text-white">Resultado do Procedimento</h3>
                <button
                  (click)="procedureResult = null"
                  class="px-4 py-2 text-sm text-slate-600 dark:text-slate-400 hover:text-slate-800 dark:hover:text-slate-200 hover:bg-slate-100 dark:hover:bg-primary-800 rounded-lg transition-colors"
                >
                  Limpar Resultados
                </button>
              </div>
              <div *ngIf="procedureResult.success === true" class="bg-slate-50 dark:bg-primary-900 border border-slate-200 dark:border-primary-800 rounded-lg p-4 mb-4">
                <p class="text-slate-800 dark:text-slate-300 font-semibold">Procedimento executado com sucesso!</p>
              </div>
              <div *ngIf="procedureResult.success === false" class="bg-slate-50 dark:bg-primary-900 border border-slate-200 dark:border-primary-800 rounded-lg p-4 mb-4">
                <p class="text-slate-800 dark:text-slate-300">{{ procedureResult.message || 'Erro ao executar procedimento' }}</p>
              </div>
              <div *ngIf="procedureResult.data && procedureResult.data.length > 0" class="mb-4">
                <p class="text-sm text-slate-600 dark:text-slate-400 mb-4 font-semibold">Resultados ({{ procedureResult.data.length }} registros):</p>
                <div class="overflow-x-auto border border-slate-200 dark:border-primary-800 rounded-lg">
                  <table class="min-w-full divide-y divide-slate-200 dark:divide-primary-800">
                    <thead class="bg-slate-50 dark:bg-primary-900">
                      <tr>
                        <th *ngFor="let key of getProcedureKeys()" class="px-4 py-3 text-left text-xs font-medium text-slate-700 dark:text-slate-300 uppercase tracking-wider">
                          {{ formatColumnName(key) }}
                        </th>
                      </tr>
                    </thead>
                    <tbody class="bg-white dark:bg-dark-surface divide-y divide-slate-200 dark:divide-primary-800">
                      <tr *ngFor="let row of procedureResult.data" class="hover:bg-slate-50 dark:hover:bg-primary-900">
                        <td *ngFor="let key of getProcedureKeys()" class="px-4 py-3 text-sm text-slate-900 dark:text-white">
                          {{ formatCellValue(row[key]) }}
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <div *ngIf="procedureResult.success === true && (!procedureResult.data || procedureResult.data.length === 0)" class="text-center py-8">
                <p class="text-slate-500 dark:text-slate-400 mb-2">Procedimento executado com sucesso!</p>
                <p class="text-sm text-slate-400 dark:text-slate-500">Nenhum dado retornado.</p>
              </div>
            </div>
          </div>

          <div *ngIf="activeTab === 'triggers'" class="bg-white dark:bg-dark-surface rounded-lg shadow-sm border border-slate-200 dark:border-primary-800 p-6">
            <h2 class="text-2xl font-display font-bold text-primary-900 dark:text-white mb-6">Trigger</h2>
            
            <div *ngIf="loadingTriggers" class="text-center py-12">
              <svg class="animate-spin h-8 w-8 text-accent-500 mx-auto" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647A7.962 7.962 0 0112 20c4.418 0 8-3.582 8-8h-4a4 4 0 00-4 4v4z"></path>
              </svg>
              <p class="mt-4 text-slate-600 dark:text-slate-400">Carregando gatilhos...</p>
            </div>
            
            <div *ngIf="!loadingTriggers && triggers.length > 0" class="space-y-4">
              <div *ngFor="let trigger of triggers" [attr.data-trigger]="trigger" class="border border-slate-200 dark:border-primary-800 rounded-lg p-4 hover:shadow-md transition-shadow">
                <div class="flex justify-between items-center">
                  <div>
                    <h3 class="font-semibold text-lg text-primary-900 dark:text-white">
                      {{ formatDisplayName(trigger, 'trigger_') }}
                    </h3>
                  </div>
                  <button
                    (click)="executeTriggerAction(trigger)"
                    [disabled]="loading"
                    class="px-4 py-2 bg-accent-500 text-white font-semibold rounded-lg hover:bg-accent-600 disabled:opacity-50 disabled:cursor-not-allowed transition-colors text-sm"
                  >
                    <span *ngIf="!loading">Executar</span>
                    <span *ngIf="loading" class="flex items-center">
                      <svg class="animate-spin -ml-1 mr-2 h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647A7.962 7.962 0 0112 20c4.418 0 8-3.582 8-8h-4a4 4 0 00-4 4v4z"></path>
                      </svg>
                      Executando...
                    </span>
                  </button>
                </div>
              </div>
            </div>
            <div *ngIf="!loadingTriggers && triggers.length === 0" class="text-center py-12 text-slate-500 dark:text-slate-400">
              Nenhum gatilho encontrado
            </div>

            <div *ngIf="triggerResult" class="mt-8" data-results-trigger>
              <div class="flex justify-between items-center mb-4">
                <h3 class="font-semibold text-lg text-primary-900 dark:text-white">Resultado do Trigger</h3>
                <button
                  (click)="triggerResult = null"
                  class="px-4 py-2 text-sm text-slate-600 dark:text-slate-400 hover:text-slate-800 dark:hover:text-slate-200 hover:bg-slate-100 dark:hover:bg-primary-800 rounded-lg transition-colors"
                >
                  Limpar Resultados
                </button>
              </div>
              <div *ngIf="triggerResult.success === true" class="bg-slate-50 dark:bg-primary-900 border border-slate-200 dark:border-primary-800 rounded-lg p-4 mb-4">
                <p class="text-slate-800 dark:text-slate-300 font-semibold">{{ triggerResult.message }}</p>
              </div>
              <div *ngIf="triggerResult.success === false" class="bg-slate-50 dark:bg-primary-900 border border-slate-200 dark:border-primary-800 rounded-lg p-4 mb-4">
                <p class="text-slate-800 dark:text-slate-300">{{ triggerResult.message || 'Erro ao executar trigger' }}</p>
              </div>
              
              <div *ngIf="triggerResult.historicoStatus && triggerResult.historicoStatus.length > 0" class="mb-4">
                <p class="text-sm text-slate-600 dark:text-slate-400 mb-4 font-semibold">Histórico de status criado ({{ triggerResult.historicoStatus.length }} registros):</p>
                <div class="overflow-x-auto border border-slate-200 dark:border-primary-800 rounded-lg">
                  <table class="min-w-full divide-y divide-slate-200 dark:divide-primary-800">
                    <thead class="bg-slate-50 dark:bg-primary-900">
                      <tr>
                        <th class="px-4 py-3 text-left text-xs font-medium text-slate-700 dark:text-slate-300 uppercase tracking-wider">Status Anterior</th>
                        <th class="px-4 py-3 text-left text-xs font-medium text-slate-700 dark:text-slate-300 uppercase tracking-wider">Status Novo</th>
                      </tr>
                    </thead>
                    <tbody class="bg-white dark:bg-dark-surface divide-y divide-slate-200 dark:divide-primary-800">
                      <tr *ngFor="let item of triggerResult.historicoStatus" class="hover:bg-slate-50 dark:hover:bg-primary-900">
                        <td class="px-4 py-3 text-sm text-slate-900 dark:text-white">{{ item.column3 || item.his_status_anterior }}</td>
                        <td class="px-4 py-3 text-sm text-slate-900 dark:text-white">{{ item.column4 || item.his_status_novo }}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              
              <div *ngIf="triggerResult.historico && triggerResult.historico.length > 0" class="mb-4">
                <p class="text-sm text-slate-600 dark:text-slate-400 mb-4 font-semibold">Mudança registrada ({{ triggerResult.historico.length }} registros):</p>
                <div class="overflow-x-auto border border-slate-200 dark:border-primary-800 rounded-lg">
                  <table class="min-w-full divide-y divide-slate-200 dark:divide-primary-800">
                    <thead class="bg-slate-50 dark:bg-primary-900">
                      <tr>
                        <th class="px-4 py-3 text-left text-xs font-medium text-slate-700 dark:text-slate-300 uppercase tracking-wider">Status Anterior</th>
                        <th class="px-4 py-3 text-left text-xs font-medium text-slate-700 dark:text-slate-300 uppercase tracking-wider">Status Novo</th>
                      </tr>
                    </thead>
                    <tbody class="bg-white dark:bg-dark-surface divide-y divide-slate-200 dark:divide-primary-800">
                      <tr *ngFor="let item of triggerResult.historico" class="hover:bg-slate-50 dark:hover:bg-primary-900">
                        <td class="px-4 py-3 text-sm text-slate-900 dark:text-white">{{ item.column3 || item.his_status_anterior }}</td>
                        <td class="px-4 py-3 text-sm text-slate-900 dark:text-white">{{ item.column4 || item.his_status_novo }}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              
              <div *ngIf="triggerResult.notificacoes && triggerResult.notificacoes.length > 0" class="mb-4">
                <p class="text-sm text-slate-600 dark:text-slate-400 mb-4 font-semibold">Notificações criadas ({{ triggerResult.notificacoes.length }} registros):</p>
                <div class="overflow-x-auto border border-slate-200 dark:border-primary-800 rounded-lg">
                  <table class="min-w-full divide-y divide-slate-200 dark:divide-primary-800">
                    <thead class="bg-slate-50 dark:bg-primary-900">
                      <tr>
                        <th class="px-4 py-3 text-left text-xs font-medium text-slate-700 dark:text-slate-300 uppercase tracking-wider">Tipo</th>
                        <th class="px-4 py-3 text-left text-xs font-medium text-slate-700 dark:text-slate-300 uppercase tracking-wider">Mensagem</th>
                      </tr>
                    </thead>
                    <tbody class="bg-white dark:bg-dark-surface divide-y divide-slate-200 dark:divide-primary-800">
                      <tr *ngFor="let notif of triggerResult.notificacoes" class="hover:bg-slate-50 dark:hover:bg-primary-900">
                        <td class="px-4 py-3 text-sm text-slate-900 dark:text-white">{{ notif.column5 || notif.not_tipo }}</td>
                        <td class="px-4 py-3 text-sm text-slate-900 dark:text-white">{{ notif.column6 || notif.not_mensagem }}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>

            <div *ngIf="triggerInfo" class="mt-8" data-results-trigger>
              <div class="flex justify-between items-center mb-4">
                <h3 class="font-semibold text-lg text-primary-900 dark:text-white">Informações do Gatilho</h3>
                <button
                  (click)="triggerInfo = null"
                  class="px-4 py-2 text-sm text-slate-600 dark:text-slate-400 hover:text-slate-800 dark:hover:text-slate-200 hover:bg-slate-100 dark:hover:bg-primary-800 rounded-lg transition-colors"
                >
                  Limpar Informações
                </button>
              </div>
              <div *ngIf="triggerInfo.success && triggerInfo.data" class="bg-slate-50 dark:bg-primary-900 p-6 rounded-lg border border-slate-200 dark:border-primary-800 shadow-sm">
                <div class="space-y-3">
                  <div *ngFor="let key of getTriggerKeys()" class="flex items-start border-b border-slate-200 dark:border-primary-800 pb-3 last:border-0 last:pb-0">
                    <span class="font-semibold text-slate-700 dark:text-slate-300 w-48 flex-shrink-0">{{ formatColumnName(key) }}:</span>
                    <span class="text-slate-900 dark:text-white flex-1 break-words">{{ triggerInfo.data[key] || '-' }}</span>
                  </div>
                </div>
              </div>
              <div *ngIf="!triggerInfo.success" class="bg-slate-50 dark:bg-primary-900 border border-slate-200 dark:border-primary-800 rounded-lg p-4">
                <p class="text-slate-800 dark:text-slate-300">{{ triggerInfo.message || 'Erro ao obter informações do gatilho' }}</p>
              </div>
            </div>
          </div>

          <div *ngIf="showProcedureModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4" (click)="closeProcedureModal()">
            <div class="bg-white dark:bg-dark-surface rounded-lg p-6 max-w-md w-full shadow-xl" (click)="$event.stopPropagation()">
              <h3 class="text-xl font-display font-bold text-primary-900 dark:text-white mb-4">
                Executar Procedure: {{ selectedProcedure }}
              </h3>
              
              <div class="space-y-4">
                <div *ngFor="let param of procedureParams">
                  <label class="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-1">{{ getParamDisplayName(param) }}</label>
                  <input
                    type="text"
                    [(ngModel)]="procedureParamValues[param]"
                    class="w-full px-4 py-2 border border-slate-300 dark:border-dark-border rounded-lg focus:outline-none focus:ring-2 focus:ring-accent-500 dark:bg-dark-input text-slate-900 dark:text-white"
                    [placeholder]="getParamPlaceholder(param)"
                  />
                </div>
              </div>
              
              <div class="mt-6 flex gap-2">
                <button
                  (click)="executeProcedure()"
                  [disabled]="loading"
                  class="flex-1 bg-accent-500 text-white px-4 py-2 rounded-lg hover:bg-accent-600 disabled:opacity-50 transition-colors font-semibold"
                >
                  Executar
                </button>
                <button
                  (click)="closeProcedureModal()"
                  class="flex-1 bg-slate-200 dark:bg-primary-800 text-slate-700 dark:text-slate-300 px-4 py-2 rounded-lg hover:bg-slate-300 dark:hover:bg-primary-700 transition-colors font-semibold"
                >
                  Cancelar
                </button>
              </div>
            </div>
          </div>
    </div>
  `
})
export class DatabaseTestComponent implements OnInit {
  activeTab: 'views' | 'procedures' | 'triggers' = 'views';
  
  views: string[] = [];
  procedures: string[] = [];
  triggers: string[] = [];
  
  loadingViews = false;
  loadingProcedures = false;
  loadingTriggers = false;
  loading = false;
  
  viewResults: any = null;
  procedureResult: any = null;
  triggerResult: any = null;
  triggerInfo: any = null;
  
  showProcedureModal = false;
  selectedProcedure = '';
  procedureParams: string[] = [];
  procedureParamValues: any = {};

  constructor(
    private dbTestService: DatabaseTestService,
    private adminService: AdminService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    if (!this.authService.isAdminLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }
    try {
      this.loadViews();
      this.loadProcedures();
      this.loadTriggers();
    } catch (error) {
    }
  }

  loadViews() {
    this.loadingViews = true;
    this.dbTestService.getAvailableViews().subscribe({
      next: (views) => {
        this.views = views || [];
        this.loadingViews = false;
      },
      error: (err) => {
        this.views = [];
        this.loadingViews = false;
      }
    });
  }

  loadProcedures() {
    this.loadingProcedures = true;
    this.dbTestService.getAvailableProcedures().subscribe({
      next: (procedures) => {
        this.procedures = procedures || [];
        this.loadingProcedures = false;
      },
      error: (err) => {
        this.procedures = [];
        this.loadingProcedures = false;
      }
    });
  }

  loadTriggers() {
    this.loadingTriggers = true;
    this.dbTestService.getAvailableTriggers().subscribe({
      next: (triggers) => {
        this.triggers = triggers || [];
        this.loadingTriggers = false;
      },
      error: (err) => {
        this.triggers = [];
        this.loadingTriggers = false;
      }
    });
  }

  executeView(viewName: string) {
    this.loading = true;
    this.viewResults = null;
    this.dbTestService.executeView(viewName).subscribe({
      next: (result) => {
        this.viewResults = result;
        this.loading = false;
        
        if (result.success && (!result.data || result.data.length === 0)) {
          this.viewResults.message = 'A visualização foi executada com sucesso, mas não retornou nenhum registro. Verifique se há dados no banco de dados.';
        }
        
        setTimeout(() => {
          const resultsElement = document.querySelector('[data-results-view]');
          if (resultsElement) {
            resultsElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          }
        }, 100);
      },
      error: (err) => {
        this.viewResults = {
          success: false,
          message: err.error?.message || err.message || 'Erro ao executar visualização. Verifique se o banco de dados está acessível e se a visualização existe.'
        };
        this.loading = false;
      }
    });
  }

  openProcedureModal(procedureName: string) {
    this.selectedProcedure = procedureName;
    this.procedureParams = this.guessProcedureParams(procedureName);
    this.procedureParamValues = {};
    this.showProcedureModal = true;
  }

  // Mapeia os nomes técnicos dos parâmetros para descrições amigáveis em português
  getParamDisplayName(param: string): string {
    const paramNames: { [key: string]: string } = {
      'p_nome': 'Nome Completo',
      'p_cpf': 'CPF',
      'p_nasc': 'Data de Nascimento',
      'p_senha': 'Senha',
      'p_end_id': 'ID do Endereço',
      'p_email': 'E-mail',
      'p_telefone': 'Telefone',
      'p_cli_id': 'ID do Cliente',
      'p_ser_id': 'ID do Serviço',
      'p_pre_id': 'ID do Prestador',
      'p_data': 'Data',
      'p_periodo': 'Período',
      'p_status_inicial': 'Status Inicial',
      'p_valor': 'Valor',
      'p_age_id': 'ID do Agendamento',
      'p_motivo': 'Motivo',
      'p_nota': 'Nota (1 a 5)',
      'p_coment': 'Comentário',
      'p_areas': 'Áreas de Atuação',
      'p_data_inicio': 'Data de Início',
      'p_data_fim': 'Data de Fim',
      'p_ini': 'Data de Início',
      'p_fim': 'Data de Fim',
      'p_status': 'Status',
      'p_dsp_id': 'ID da Disputa',
      'p_forma': 'Forma de Pagamento',
      'p_valor_total': 'Valor Total',
      'p_exp': 'Experiência',
      'p_certs': 'Certificados',
      'p_ref': 'Referência do Gateway'
    };
    
    return paramNames[param.toLowerCase()] || param;
  }

  // Retorna um placeholder amigável para cada parâmetro
  getParamPlaceholder(param: string): string {
    const placeholders: { [key: string]: string } = {
      'p_nome': 'Digite o nome completo',
      'p_cpf': 'Digite o CPF (ex: 123.456.789-00)',
      'p_nasc': 'Digite a data de nascimento (ex: 1990-01-15)',
      'p_senha': 'Digite a senha',
      'p_end_id': 'Digite o ID do endereço',
      'p_email': 'Digite o e-mail (ex: exemplo@email.com)',
      'p_telefone': 'Digite o telefone (ex: (11) 98765-4321)',
      'p_cli_id': 'Digite o ID do cliente',
      'p_ser_id': 'Digite o ID do serviço',
      'p_pre_id': 'Digite o ID do prestador',
      'p_data': 'Digite a data (ex: 2024-12-25)',
      'p_periodo': 'Digite o período (ex: Manhã, Tarde, Noite)',
      'p_status_inicial': 'Digite o status inicial (ex: Pendente)',
      'p_valor': 'Digite o valor (ex: 150.00)',
      'p_age_id': 'Digite o ID do agendamento',
      'p_motivo': 'Digite o motivo',
      'p_nota': 'Digite a nota de 1 a 5',
      'p_coment': 'Digite o comentário da avaliação',
      'p_areas': 'Digite as áreas de atuação (ex: Encanamento, Elétrica)',
      'p_data_inicio': 'Digite a data de início (ex: 2024-01-01)',
      'p_data_fim': 'Digite a data de fim (ex: 2024-12-31)',
      'p_ini': 'Digite a data de início (ex: 2024-01-01)',
      'p_fim': 'Digite a data de fim (ex: 2024-12-31)',
      'p_status': 'Digite o status (ex: Pendente, Confirmado)',
      'p_dsp_id': 'Digite o ID da disputa',
      'p_forma': 'Digite a forma de pagamento (ex: Cartão, PIX)',
      'p_valor_total': 'Digite o valor total (ex: 250.00)',
      'p_exp': 'Digite a experiência do prestador',
      'p_certs': 'Digite os certificados do prestador',
      'p_ref': 'Digite a referência do gateway de pagamento'
    };
    
    return placeholders[param.toLowerCase()] || `Digite o valor para ${param}`;
  }

  guessProcedureParams(procedureName: string): string[] {
    const paramMap: any = {
      'pesquisar_clientes_por_nome_exato': ['p_nome'],
      'listar_agendamentos_por_id_de_cliente': ['p_cli_id'],
      'inserir_agendamento_de_servico': ['p_cli_id', 'p_ser_id', 'p_pre_id', 'p_data', 'p_periodo', 'p_end_id', 'p_status_inicial', 'p_valor'],
      'cancelar_agendamento_de_servico': ['p_age_id', 'p_motivo'],
      'registrar_avaliacao_de_prestador': ['p_age_id', 'p_cli_id', 'p_pre_id', 'p_nota', 'p_coment'],
      'inserir_cliente': ['p_nome', 'p_cpf', 'p_nasc', 'p_senha', 'p_end_id', 'p_email', 'p_telefone'],
      'inserir_prestador': ['p_nome', 'p_cpf', 'p_nasc', 'p_areas', 'p_exp', 'p_certs', 'p_senha', 'p_end_id', 'p_email', 'p_telefone'],
      'listar_agendamentos_por_periodo_e_status': ['p_ini', 'p_fim', 'p_status'],
      'abrir_disputa': ['p_age_id', 'p_cli_id', 'p_pre_id', 'p_motivo', 'p_valor'],
      'fechar_disputa': ['p_dsp_id'],
      'inserir_pagamento': ['p_age_id', 'p_forma', 'p_valor', 'p_status', 'p_ref', 'p_data']
    };
    
    return paramMap[procedureName.toLowerCase()] || [];
  }

  executeProcedure() {
    this.loading = true;
    this.procedureResult = null;
    
    const params: any = {};
    this.procedureParams.forEach(param => {
      if (this.procedureParamValues[param]) {
        const value = this.procedureParamValues[param];
        if (!isNaN(value) && value !== '') {
          params[param] = Number(value);
        } else {
          params[param] = value;
        }
      }
    });
    
    this.dbTestService.executeProcedure(this.selectedProcedure, params).subscribe({
      next: (result) => {
        this.procedureResult = result;
        this.loading = false;
        this.closeProcedureModal();
        
        setTimeout(() => {
          const resultsElement = document.querySelector('[data-results-procedure]');
          if (resultsElement) {
            resultsElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          }
        }, 100);
      },
      error: (err) => {
        this.procedureResult = {
          success: false,
          message: err.error?.message || err.message || 'Erro ao executar procedimento. Verifique os parâmetros e se o procedimento existe.'
        };
        this.loading = false;
      }
    });
  }

  closeProcedureModal() {
    this.showProcedureModal = false;
    this.selectedProcedure = '';
    this.procedureParams = [];
    this.procedureParamValues = {};
  }

  getTriggerInfo(triggerName: string) {
    this.loading = true;
    this.triggerInfo = null;
    this.dbTestService.getTriggerInfo(triggerName).subscribe({
      next: (result) => {
        this.triggerInfo = result;
        this.loading = false;
        
        setTimeout(() => {
          const resultsElement = document.querySelector('[data-results-trigger]');
          if (resultsElement) {
            resultsElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          }
        }, 100);
      },
      error: (err) => {
        this.triggerInfo = {
          success: false,
          message: err.error?.message || err.message || 'Erro ao obter informações do gatilho. Verifique se o gatilho existe.'
        };
        this.loading = false;
      }
    });
  }

  getViewKeys(): string[] {
    if (!this.viewResults || !this.viewResults.data || this.viewResults.data.length === 0) {
      return [];
    }
    return Object.keys(this.viewResults.data[0]);
  }

  getTriggerKeys(): string[] {
    if (!this.triggerInfo || !this.triggerInfo.data) {
      return [];
    }
    return Object.keys(this.triggerInfo.data);
  }

  executeTriggerAction(trigger: string) {
    this.loading = true;
    this.dbTestService.executeTriggerAction(trigger, {}).subscribe({
      next: (response) => {
        this.triggerResult = {
          ...response,
          triggerName: trigger
        };
        this.loading = false;
        
        setTimeout(() => {
          const element = document.querySelector('[data-results-trigger]');
          if (element) {
            element.scrollIntoView({ behavior: 'smooth', block: 'start' });
          }
        }, 100);
      },
      error: (err) => {
        this.triggerResult = {
          success: false,
          message: err.error?.message || err.message || 'Erro ao executar ação do trigger.',
          triggerName: trigger
        };
        this.loading = false;
      }
    });
  }

  clearTriggerResult(triggerName: string) {
    if (this.triggerResult && this.triggerResult.triggerName === triggerName) {
      this.triggerResult = null;
    }
  }

  getTriggerDescription(trigger: string): string {
    const descriptions: { [key: string]: string } = {
      'trigger_pos_inserir_agendamento_registrar_status_inicial': 'Registra automaticamente o status inicial quando um novo agendamento é criado.',
      'trigger_pos_atualizar_agendamento_registrar_mudanca_de_status': 'Registra automaticamente no histórico quando o status de um agendamento é alterado.',
      'trigger_pos_inserir_avaliacao_criar_notificacao': 'Cria automaticamente uma notificação para o prestador quando uma avaliação é registrada.',
      'trigger_pos_inserir_pagamento_confirmado_criar_notificacao': 'Cria automaticamente uma notificação quando um pagamento é confirmado.',
      'trigger_pos_inserir_agendamento_notificar_prestador': 'Notifica automaticamente o prestador quando um novo agendamento é atribuído a ele.',
      'trigger_pos_inserir_disputa_aberta_criar_notificacao': 'Cria automaticamente uma notificação quando uma disputa é aberta.'
    };
    return descriptions[trigger] || 'Trigger executado automaticamente quando a ação correspondente ocorre no banco de dados.';
  }

  getProcedureKeys(): string[] {
    if (!this.procedureResult || !this.procedureResult.data || this.procedureResult.data.length === 0) {
      return [];
    }
    return Object.keys(this.procedureResult.data[0]);
  }

  formatColumnName(key: string): string {
    const normalizedKey = key.toLowerCase();
    
    // Primeiro, verificar IDs específicos ANTES de qualquer processamento
    const specificIdLabels: { [key: string]: string } = {
      'age_id': 'ID do Agendamento',
      'agendamento_id': 'ID do Agendamento',
      'agendamento_servico_id': 'ID do Agendamento',
      'cli_id': 'ID do Cliente',
      'cliente_id': 'ID do Cliente',
      'clienteid': 'ID do Cliente',
      'pre_id': 'ID do Prestador',
      'prestador_id': 'ID do Prestador',
      'prestadorid': 'ID do Prestador',
      'ser_id': 'ID do Serviço',
      'servico_id': 'ID do Serviço',
      'servicoid': 'ID do Serviço',
      'end_id': 'ID do Endereço',
      'endereco_id': 'ID do Endereço',
      'pag_id': 'ID do Pagamento',
      'pagamento_id': 'ID do Pagamento',
      'pagamentoid': 'ID do Pagamento',
      'dsp_id': 'ID da Disputa',
      'disputa_id': 'ID da Disputa',
      'ava_id': 'ID da Avaliação',
      'avaliacao_id': 'ID da Avaliação',
      'not_id': 'ID da Notificação',
      'notificacao_id': 'ID da Notificação',
      'cat_id': 'ID da Categoria',
      'categoria_id': 'ID da Categoria',
      'reg_id': 'ID da Região',
      'regiao_id': 'ID da Região'
    };
    
    if (specificIdLabels[normalizedKey]) {
      return specificIdLabels[normalizedKey];
    }
    
    // Verificar se termina com _id (fallback genérico)
    if (normalizedKey.endsWith('_id')) {
      const entity = normalizedKey.replace(/_id$/, '').replace(/_/g, ' ').trim();
      if (entity) {
        const entityLabel = entity
          .split(/\s+/)
          .map(word => word ? word.charAt(0).toUpperCase() + word.slice(1) : '')
          .join(' ')
          .trim();
        return `ID de ${entityLabel}`;
      }
    }
    
    // Processar outras colunas (remover prefixos e formatar)
    let formatted = key
      .replace(/^(cli_|pre_|ser_|age_|ava_|pag_|dsp_|not_|cat_|end_|reg_|dis_|cer_|prs_|rre_|his_|his_)/i, '')
      .replace(/_/g, ' ');
    
    formatted = formatted.replace(/\b\w/g, l => l.toUpperCase());
    
    const translations: { [key: string]: string } = {
      'id': 'ID',
      'nome completo': 'Nome Completo',
      'data nascimento': 'Data de Nascimento',
      'data': 'Data',
      'status': 'Status',
      'valor': 'Valor',
      'preco base': 'Preço Base',
      'preco oferta': 'Preço Oferta',
      'ativo': 'Ativo',
      'pago': 'Pago',
      'nota': 'Nota',
      'comentario': 'Comentário',
      'coment': 'Comentário',
      'motivo': 'Motivo',
      'periodo': 'Período',
      'janela': 'Janela',
      'forma': 'Forma de Pagamento',
      'referencia gateway': 'Referência Gateway',
      'areas atuacao': 'Áreas de Atuação',
      'experiencia': 'Experiência',
      'certificados': 'Certificados',
      'logradouro': 'Logradouro',
      'numero': 'Número',
      'complemento': 'Complemento',
      'bairro': 'Bairro',
      'cidade': 'Cidade',
      'uf': 'UF',
      'cep': 'CEP',
      'descricao': 'Descrição',
      'data cadastro': 'Data de Cadastro',
      'data registro': 'Data de Registro',
      'data alteracao': 'Data de Alteração',
      'data abertura': 'Data de Abertura',
      'data fechamento': 'Data de Fechamento',
      'data cancelamento': 'Data de Cancelamento',
      'data conclusao': 'Data de Conclusão',
      'status anterior': 'Status Anterior',
      'status novo': 'Status Novo',
      'tipo': 'Tipo',
      'mensagem': 'Mensagem',
      'enviado': 'Enviado',
      'quantidade avaliacoes': 'Quantidade de Avaliações',
      'media nota': 'Média de Nota',
      'total': 'Total'
    };
    
    const lowerKey = formatted.toLowerCase();
    if (translations[lowerKey]) {
      return translations[lowerKey];
    }
    
    return formatted;
  }

  formatCellValue(value: any): string {
    if (value === null || value === undefined) {
      return '-';
    }
    
    if (typeof value === 'boolean') {
      return value ? 'Sim' : 'Não';
    }
    
    if (typeof value === 'number') {
      if (value % 1 !== 0) {
        return value.toFixed(2).replace('.', ',');
      }
      return value.toString();
    }
    
    if (value instanceof Date) {
      return value.toLocaleDateString('pt-BR');
    }
    
    if (typeof value === 'string' && /^\d{4}-\d{2}-\d{2}/.test(value)) {
      try {
        const date = new Date(value);
        return date.toLocaleDateString('pt-BR');
      } catch {
        return value;
      }
    }
    
    return String(value);
  }

  formatDisplayName(name: string, prefixToRemove: string = ''): string {
    let formatted = name;
    
    if (prefixToRemove && formatted.startsWith(prefixToRemove)) {
      formatted = formatted.substring(prefixToRemove.length);
    }
    formatted = formatted.replace(/^view_/i, '');
    formatted = formatted.replace(/^procedure_/i, '');
    
    let addPosPrefix = false;
    if (formatted.match(/^trigger_pos_/i)) {
      formatted = formatted.replace(/^trigger_pos_/i, '');
      addPosPrefix = true;
    } else {
      formatted = formatted.replace(/^trigger_/i, '');
    }
    
    formatted = formatted.replace(/_/g, ' ');
    
    if (addPosPrefix) {
      formatted = 'pos ' + formatted;
    }
    
    const translations: { [key: string]: string } = {
      'periodo': 'Período',
      'regiao': 'Região',
      'cliente': 'Cliente',
      'prestador': 'Prestador',
      'prestadores': 'Prestadores',
      'agendamento': 'Agendamento',
      'agendamentos': 'Agendamentos',
      'avaliacao': 'Avaliação',
      'avaliacoes': 'Avaliações',
      'disponibilidade': 'Disponibilidade',
      'disputa': 'Disputa',
      'disputas': 'Disputas',
      'servico': 'Serviço',
      'servicos': 'Serviços',
      'pagamento': 'Pagamento',
      'pagamentos': 'Pagamentos',
      'dados': 'Dados',
      'detalhes': 'Detalhes',
      'media': 'Média',
      'aberta': 'Aberta',
      'abertas': 'Abertas',
      'oferecido': 'Oferecido',
      'oferecidos': 'Oferecidos',
      'status': 'Status',
      'pos': 'Pós',
      'inserir': 'Inserir',
      'atualizar': 'Atualizar',
      'registrar': 'Registrar',
      'mudanca': 'Mudança',
      'criar': 'Criar',
      'notificacao': 'Notificação',
      'notificar': 'Notificar',
      'inicial': 'Inicial',
      'confirmado': 'Confirmado'
    };
    
    const lowercaseWords = ['de', 'da', 'do', 'das', 'dos', 'por', 'para', 'com', 'sem', 'em', 'a', 'o', 'e'];
    
    const words = formatted.toLowerCase().split(/\s+/);
    const processedWords = words.map((word, index) => {
      // Primeira palavra sempre capitalizada
      if (index === 0) {
        if (translations[word]) {
          const translated = translations[word];
          return translated.charAt(0).toUpperCase() + translated.slice(1);
        }
        return word.charAt(0).toUpperCase() + word.slice(1);
      }
      
      // Palavras de ligação permanecem em minúsculo
      if (lowercaseWords.includes(word)) {
        return word;
      }
      
      // Outras palavras: usar tradução se existir, senão capitalizar
      if (translations[word]) {
        const translated = translations[word];
        return translated.charAt(0).toUpperCase() + translated.slice(1);
      }
      
      // Capitalizar primeira letra de qualquer palavra
      return word.charAt(0).toUpperCase() + word.slice(1);
    });
    
    return processedWords.join(' ').trim();
  }

  logout() {
    this.authService.logoutAdmin();
  }

  goBack() {
    this.router.navigate(['/admin']);
  }
}

