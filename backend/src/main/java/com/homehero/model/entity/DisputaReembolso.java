package com.homehero.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidade que representa a tabela disputa_reembolso.
 * Disputas de reembolso abertas por clientes ou prestadores.
 */
@Entity
@Table(name = "disputa_reembolso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisputaReembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dsp_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_id", nullable = false)
    private AgendamentoServico agendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cli_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_id")
    private Prestador prestador;

    @Column(name = "dsp_motivo", length = 255)
    private String motivo;

    @Column(name = "dsp_status", length = 20)
    private String status; // Aberta, Resolvida

    @Column(name = "dsp_valor")
    private Float valor;

    @Column(name = "dsp_abertura")
    private LocalDate abertura;

    @Column(name = "dsp_fechamento")
    private LocalDate fechamento;
}
