package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    
    @ManyToOne
    @JoinColumn(name = "dsp_age_id", nullable = false)
    private AgendamentoServico agendamento;
    
    @ManyToOne
    @JoinColumn(name = "dsp_cli_id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "dsp_pre_id")
    private Prestador prestador;
    
    @Column(name = "dsp_motivo", length = 255)
    private String motivo;
    
    @Column(name = "dsp_status", length = 20)
    private String status; // Ex: Aberta, Em Mediação, Resolvida
    
    @Column(name = "dsp_valor_reembolsar")
    private Float valorReembolsar;
    
    @Column(name = "dsp_data_abertura")
    private LocalDate dataAbertura;
    
    @Column(name = "dsp_data_fechamento")
    private LocalDate dataFechamento;
}

