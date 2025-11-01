package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "atribuicao_servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtribuicaoServico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atr_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "atr_age_id", nullable = false)
    private AgendamentoServico agendamento;
    
    @ManyToOne
    @JoinColumn(name = "atr_fun_id", nullable = false)
    private FuncionarioEmpresa funcionario;
    
    @Column(name = "atr_data")
    private LocalDate data;
    
    @Column(name = "atr_status", length = 20)
    private String status; // Ex: Atribuído, Aceito, Recusado, Concluído
}

