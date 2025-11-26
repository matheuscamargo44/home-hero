package com.homehero.model.procedure; // Pacote reservado para DTOs ligados às procedures.

import com.fasterxml.jackson.annotation.JsonFormat; // Controla o formato da data recebida no JSON.
import jakarta.validation.constraints.Email; // Validação para campos de e-mail.
import jakarta.validation.constraints.NotBlank; // Validação que garante string não vazia.
import jakarta.validation.constraints.NotNull; // Validação que garante valor não nulo.

import java.time.LocalDate; // Representa a data de nascimento.

/**
 * Estrutura usada para chamar a procedure inserir_cliente.
 */
public record ClienteProcedureRequest( // Record imutável que mapeia o corpo do POST.
    @NotBlank(message = "Nome é obrigatório") // Validação amigável para o campo nome.
    String nome, // Nome completo enviado ao banco.

    @NotBlank(message = "CPF é obrigatório") // Garante que o CPF foi informado.
    String cpf, // Campo com o documento do cliente.

    @NotNull(message = "Data de nascimento é obrigatória") // Data não pode ser nula.
    @JsonFormat(pattern = "yyyy-MM-dd") // Define o formato aceito pelo JSON.
    LocalDate nascimento, // Data de nascimento enviada à procedure.

    @NotBlank(message = "Senha é obrigatória") // Exige o envio da senha.
    String senha, // Senha do cliente.

    @NotNull(message = "enderecoId é obrigatório") // Garante que o ID do endereço foi informado.
    Integer enderecoId, // Identificador de endereço já existente.

    @NotBlank(message = "E-mail é obrigatório") // Valida presença do e-mail.
    @Email(message = "E-mail inválido") // E verifica se o formato é válido.
    String email, // E-mail do cliente.

    @NotBlank(message = "Telefone é obrigatório") // Garante que o telefone foi enviado.
    String telefone // Número de telefone do cliente.
) { // Fim da definição do record.
} // Fim do arquivo.

