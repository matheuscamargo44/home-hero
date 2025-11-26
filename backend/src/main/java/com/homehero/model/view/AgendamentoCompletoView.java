package com.homehero.model.view; // Pacote de DTOs baseados em views.

import java.time.LocalDate; // Representa datas retornadas pela view.

/**
 * Representa uma linha da view_agendamentos_completo.
 */
public record AgendamentoCompletoView( // Record que mapeia todas as colunas da view.
    Integer agendamentoId, // ID do agendamento.
    Integer clienteId, // ID do cliente.
    Integer servicoId, // ID do serviço.
    Integer prestadorId, // ID do prestador.
    LocalDate data, // Data marcada.
    String periodo, // Período (manhã/tarde/noite).
    Integer enderecoId, // ID do endereço usado.
    String status, // Status atual.
    Double valor, // Valor do agendamento.
    Boolean pago, // Flag indicando se foi pago.
    LocalDate dataCancelamento, // Data em que foi cancelado (se houver).
    String motivoCancelamento, // Motivo do cancelamento.
    String clienteNome, // Nome do cliente.
    String clienteEmail, // Email do cliente.
    String clienteTelefone, // Telefone do cliente.
    String prestadorNome, // Nome do prestador.
    String prestadorEmail, // Email do prestador.
    String prestadorTelefone, // Telefone do prestador.
    String servicoNome, // Nome do serviço.
    String servicoDescricao, // Descrição do serviço.
    String logradouro, // Logradouro do endereço.
    String numero, // Número.
    String complemento, // Complemento.
    String bairro, // Bairro.
    String cidade, // Cidade.
    String uf, // Estado (UF).
    String cep // CEP.
) { // Fim do record.
} // Fim do arquivo.

