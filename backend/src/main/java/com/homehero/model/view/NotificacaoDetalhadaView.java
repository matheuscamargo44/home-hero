package com.homehero.model.view; // Pacote destinado aos DTOs baseados em views.

import java.time.LocalDate; // Utilizado para datas retornadas pelo banco.

/**
 * Representa uma linha da view_notificacoes_detalhadas.
 */
public record NotificacaoDetalhadaView( // Record com todas as colunas da view.
    Integer notificacaoId, // ID da notificação.
    String tipo, // Tipo (Agendamento, Pagamento, etc.).
    String mensagem, // Mensagem gerada pela trigger.
    Boolean enviado, // Indica se a notificação já foi enviada.
    LocalDate data, // Data da notificação.
    Integer clienteId, // ID do cliente (pode ser nulo).
    String clienteNome, // Nome do cliente.
    String clienteEmail, // Email do cliente.
    String clienteTelefone, // Telefone do cliente.
    Integer prestadorId, // ID do prestador (pode ser nulo).
    String prestadorNome, // Nome do prestador.
    String prestadorEmail, // Email do prestador.
    String prestadorTelefone, // Telefone do prestador.
    Integer agendamentoId, // ID do agendamento relacionado.
    LocalDate dataAgendamento, // Data do agendamento.
    String statusAgendamento, // Status atual do agendamento.
    Double valorAgendamento, // Valor registrado no agendamento.
    String servicoNome, // Nome do serviço.
    String servicoDescricao // Descrição do serviço.
) { // Fim do record.
} // Fim do arquivo.

