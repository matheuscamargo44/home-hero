package com.homehero.model.view; // Pacote reservado para DTOs baseados em views SQL.

import java.time.LocalDate; // Tipo usado para datas provenientes da view.

/**
 * Representa uma linha de view_dados_de_clientes.
 */
public record ClienteView( // Record imutável que descreve as colunas da view.
    Integer clienteId, // ID do cliente (coluna "ID do cliente").
    String nome, // Nome completo.
    String cpf, // CPF formatado.
    LocalDate nascimento, // Data de nascimento.
    String senha, // Senha (conforme armazenada na tabela original).
    Integer enderecoId, // ID do endereço associado.
    String email, // Email do cliente.
    String telefone, // Telefone.
    String logradouro, // Logradouro do endereço.
    String numero, // Número.
    String complemento, // Complemento (pode ser nulo).
    String bairro, // Bairro.
    String cidade, // Cidade.
    String uf, // Unidade federativa (estado).
    String cep // CEP formatado.
) { // Fim do record.
} // Fim do arquivo.

