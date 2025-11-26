package com.homehero.model.view; // Pacote que agrupa as estruturas baseadas em views.

import java.time.LocalDate; // Utilizado para a data de nascimento.

/**
 * Representa uma linha da view_dados_de_prestadores.
 */
public record PrestadorView( // Record que descreve todas as colunas retornadas pela view.
    Integer prestadorId, // Coluna "ID do prestador".
    String nome, // Nome do prestador.
    String cpf, // CPF.
    LocalDate nascimento, // Data de nascimento.
    String areas, // Áreas de atuação.
    String experiencia, // Resumo da experiência.
    String certificados, // Certificações cadastradas.
    String senha, // Senha armazenada na tabela original.
    Integer enderecoId, // ID do endereço.
    String email, // Email do prestador.
    String telefone, // Telefone principal.
    String logradouro, // Logradouro do endereço.
    String numero, // Número do endereço.
    String complemento, // Complemento (opcional).
    String bairro, // Bairro.
    String cidade, // Cidade.
    String uf, // Estado.
    String cep // CEP formatado.
) { // Fim do record.
} // Fim do arquivo.

