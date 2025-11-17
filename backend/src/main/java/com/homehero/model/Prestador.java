package com.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidade que representa um prestador de serviços do sistema
 * Mapeia a tabela 'prestador' do banco de dados
 */
@Entity
@Table(name = "prestador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestador {
    /**
     * ID único do prestador (chave primária)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pre_id")
    private Integer id;

    /**
     * Nome completo do prestador
     */
    @Column(name = "pre_nome", nullable = false, length = 80)
    private String nome;

    /**
     * CPF do prestador (único no sistema, apenas números)
     */
    @Column(name = "pre_cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    /**
     * Data de nascimento do prestador
     */
    @Column(name = "pre_nascimento", nullable = false)
    private LocalDate nascimento;

    /**
     * Áreas de atuação do prestador
     */
    @Column(name = "pre_areas", length = 120)
    private String areas;

    /**
     * Experiência do prestador
     */
    @Column(name = "pre_experiencia", length = 255)
    private String experiencia;

    /**
     * Certificados do prestador
     */
    @Column(name = "pre_certificados", length = 255)
    private String certificados;

    /**
     * Senha do prestador
     */
    @Column(name = "pre_senha", nullable = false, length = 60)
    private String senha;

    /**
     * ID do endereço do prestador (chave estrangeira)
     */
    @Column(name = "end_id", nullable = false)
    private Integer enderecoId;

    /**
     * Email do prestador (único no sistema)
     */
    @Column(name = "pre_email", nullable = false, unique = true, length = 120)
    private String email;

    /**
     * Telefone do prestador (único no sistema)
     */
    @Column(name = "pre_telefone", nullable = false, unique = true, length = 20)
    private String telefone;
}


