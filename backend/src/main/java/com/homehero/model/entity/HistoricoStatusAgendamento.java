package com.homehero.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidade que representa a tabela historico_status_agendamento.
 * Histórico de mudanças de status dos agendamentos (criado por triggers).
 */
@Entity
@Table(name = "historico_status_agendamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoStatusAgendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "his_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_id", nullable = false)
    private AgendamentoServico agendamento;

    @Column(name = "his_status_ant", length = 20)
    private String statusAnterior;

    @Column(name = "his_status_novo", length = 20)
    private String statusNovo;

    @Column(name = "his_data")
    private LocalDate data;
}
