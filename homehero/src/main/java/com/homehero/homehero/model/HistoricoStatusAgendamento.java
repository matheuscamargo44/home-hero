package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    
    @ManyToOne
    @JoinColumn(name = "his_age_id", nullable = false)
    private AgendamentoServico agendamento;
    
    @Column(name = "his_status_anterior", length = 20)
    private String statusAnterior;
    
    @Column(name = "his_status_novo", length = 20)
    private String statusNovo;
    
    @Column(name = "his_data_alteracao")
    private LocalDate dataAlteracao;
}

