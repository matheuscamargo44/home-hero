package com.homehero.model.procedure; // Pacote das estruturas usadas pelas procedures.

import com.fasterxml.jackson.annotation.JsonFormat; // Define o formato da data fornecida no JSON.
import jakarta.validation.constraints.Email; // Validação de formato de e-mail.
import jakarta.validation.constraints.NotBlank; // Garante strings não vazias.
import jakarta.validation.constraints.NotNull; // Garante valores não nulos.

import java.time.LocalDate; // Representa a data de nascimento.

/**
 * Corpo usado para acionar a procedure inserir_prestador.
 */
public record PrestadorProcedureRequest( // Record imutável que descreve o payload esperado.
    @NotBlank(message = "Nome é obrigatório") // Obriga o envio do nome.
    String nome, // Nome completo do prestador.

    @NotBlank(message = "CPF é obrigatório") // Obriga o envio do CPF.
    String cpf, // Documento nacional do prestador.

    @NotNull(message = "Data de nascimento é obrigatória") // Garante a presença da data.
    @JsonFormat(pattern = "yyyy-MM-dd") // Define o formato aceito para datas.
    LocalDate nascimento, // Data de nascimento do prestador.

    String areas, // Áreas de atuação (campo opcional, portanto sem validação).
    String experiencia, // Texto com resumo da experiência profissional.
    String certificados, // Lista ou descrição dos certificados.

    @NotBlank(message = "Senha é obrigatória") // Exige o envio da senha.
    String senha, // Senha em texto simples (para demonstração).

    @NotNull(message = "enderecoId é obrigatório") // Garante a referência ao endereço.
    Integer enderecoId, // ID da tabela endereco já existente.

    @NotBlank(message = "E-mail é obrigatório") // Exige a presença de e-mail.
    @Email(message = "E-mail inválido") // Valida o formato do e-mail.
    String email, // E-mail do prestador.

    @NotBlank(message = "Telefone é obrigatório") // Exige o telefone.
    String telefone // Telefone principal.
) { // Fim do record.
} // Fim do arquivo.

