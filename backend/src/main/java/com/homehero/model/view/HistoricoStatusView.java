package com.homehero.model.view; // Pacote onde vivem os DTOs baseados em views.

import java.time.LocalDate; // Usado para as datas provenientes da view.

/**
 * Representa uma linha da view_historico_status_completo.
 */
public record HistoricoStatusView( // Record que mapeia todas as colunas da view.
    Integer historicoId, // ID do registro de histórico.
    Integer agendamentoId, // ID do agendamento referenciado.
    String statusAnterior, // Status antes da mudança.
    String statusNovo, // Status depois da mudança.
    LocalDate dataStatus, // Data da alteração.
    Integer clienteId, // ID do cliente.
    String clienteNome, // Nome do cliente.
    String clienteEmail, // Email do cliente.
    Integer prestadorId, // ID do prestador.
    String prestadorNome, // Nome do prestador.
    String prestadorEmail, // Email do prestador.
    LocalDate dataAgendamento, // Data original do agendamento.
    String janela, // Janela/Período.
    String statusAtual, // Status atual no momento da view.
    Double valor, // Valor do agendamento.
    Integer servicoId, // ID do serviço.
    String servicoNome, // Nome do serviço.
    String servicoDescricao // Descrição do serviço.
) { // Fim do record.
} // Fim do arquivo.

