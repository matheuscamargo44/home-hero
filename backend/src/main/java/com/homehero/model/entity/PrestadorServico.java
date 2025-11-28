package com.homehero.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidade que representa a tabela prestador_servico.
 * Relacionamento entre prestadores e serviços que eles oferecem.
 */
@Entity
@Table(name = "prestador_servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestadorServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prs_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_id", nullable = false)
    private Prestador prestador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ser_id", nullable = false)
    private Servico servico;

    @Column(name = "prs_preco")
    private Float preco;

    @Column(name = "prs_ativo")
    private Boolean ativo;

    @Column(name = "prs_data")
    private LocalDate data;
}
