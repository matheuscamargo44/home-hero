package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "notificacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "not_cli_id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "not_pre_id")
    private Prestador prestador;
    
    @ManyToOne
    @JoinColumn(name = "not_age_id")
    private AgendamentoServico agendamento;
    
    @Column(name = "not_tipo", length = 30)
    private String tipo; // Ex: Confirmação, Lembrete, Cancelamento, Avaliação, Disputa
    
    @Column(name = "not_mensagem", length = 255)
    private String mensagem;
    
    @Column(name = "not_enviado")
    private Boolean enviado = false;
    
    @Column(name = "not_data")
    private LocalDate data;
}

