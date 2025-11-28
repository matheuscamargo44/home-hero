package com.homehero.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidade que representa a tabela avaliacao.
 * Avaliações de prestadores feitas por clientes após o serviço.
 */
@Entity
@Table(name = "avaliacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ava_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_id", nullable = false)
    private AgendamentoServico agendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cli_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_id")
    private Prestador prestador;

    @Column(name = "ava_nota")
    private Integer nota; // 1 a 5

    @Column(name = "ava_coment", length = 400)
    private String comentario;

    @Column(name = "ava_data")
    private LocalDate data;
}
