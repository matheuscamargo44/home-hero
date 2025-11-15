package com.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidade que representa um cliente do sistema
 * Mapeia a tabela 'cliente' do banco de dados
 */
@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    /**
     * ID único do cliente (chave primária)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cli_id")
    private Integer id;

    /**
     * Nome completo do cliente
     */
    @Column(name = "cli_nome_completo", nullable = false, length = 80)
    private String nomeCompleto;

    /**
     * CPF do cliente (único no sistema, apenas números)
     */
    @Column(name = "cli_cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    /**
     * Data de nascimento do cliente
     */
    @Column(name = "cli_data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    /**
     * Senha do cliente
     */
    @Column(name = "cli_senha", nullable = false, length = 60)
    private String senha;

    /**
     * ID do endereço do cliente (chave estrangeira)
     */
    @Column(name = "cli_endereco_id", nullable = false)
    private Integer enderecoId;

    /**
     * Email do cliente (único no sistema)
     */
    @Column(name = "cli_email", nullable = false, unique = true, length = 120)
    private String email;

    /**
     * Telefone do cliente (único no sistema)
     */
    @Column(name = "cli_telefone", nullable = false, unique = true, length = 20)
    private String telefone;
}






