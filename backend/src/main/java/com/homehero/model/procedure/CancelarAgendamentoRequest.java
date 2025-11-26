package com.homehero.model.procedure; // Pacote de DTOs usados nas procedures.

import jakarta.validation.constraints.NotBlank; // Validação para strings obrigatórias.
import jakarta.validation.constraints.NotNull; // Validação para campos obrigatórios.

/**
 * Corpo para chamar cancelar_agendamento_de_servico.
 */
public record CancelarAgendamentoRequest( // Record que representa o payload enviado ao endpoint.
    @NotNull(message = "ageId é obrigatório") // Exige que o ID do agendamento seja informado.
    Integer ageId, // Identificador do agendamento que será cancelado.

    @NotBlank(message = "motivo é obrigatório") // Exige um texto explicando o cancelamento.
    String motivo // Motivo enviado para registro no banco.
) { // Fim do record.
} // Fim do arquivo.

