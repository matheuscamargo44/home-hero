package com.homehero.model.procedure; // Pacote de DTOs ligados a procedures.

import com.fasterxml.jackson.annotation.JsonFormat; // Controla o formato da data recebida.
import jakarta.validation.constraints.NotBlank; // Validação para strings obrigatórias.
import jakarta.validation.constraints.NotNull; // Validação para tipos obrigatórios.

import java.time.LocalDate; // Representa a data do agendamento.

/**
 * Corpo para chamar inserir_agendamento_de_servico.
 */
public record AgendamentoProcedureRequest( // Record que descreve o payload esperado.
    @NotNull(message = "cliId é obrigatório") // Cliente precisa ser informado.
    Integer cliId, // ID do cliente.

    @NotNull(message = "serId é obrigatório") // Serviço precisa ser informado.
    Integer serId, // ID do serviço.

    @NotNull(message = "preId é obrigatório") // Prestador precisa ser informado.
    Integer preId, // ID do prestador.

    @NotNull(message = "data é obrigatória") // A data precisa ser enviada.
    @JsonFormat(pattern = "yyyy-MM-dd") // Define o formato da data no JSON.
    LocalDate data, // Data do agendamento.

    @NotBlank(message = "periodo é obrigatório") // Período precisa ser informado.
    String periodo, // Janela do atendimento (Manhã/Tarde/Noite).

    @NotNull(message = "enderecoId é obrigatório") // Endereço precisa existir previamente.
    Integer enderecoId, // ID do endereço onde o serviço será executado.

    @NotBlank(message = "statusInicial é obrigatório") // Define o status inicial.
    String statusInicial, // Ex.: "Criado" ou "Pendente".

    @NotNull(message = "valor é obrigatório") // Valor não pode ser nulo.
    Double valor // Valor negociado para o agendamento.
) { // Fim do record.
} // Fim do arquivo.

