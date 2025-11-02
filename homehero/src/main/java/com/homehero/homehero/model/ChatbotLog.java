package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "chatbot_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatbotLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cbt_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "cbt_cli_id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "cbt_pre_id")
    private Prestador prestador;
    
    @ManyToOne
    @JoinColumn(name = "cbt_emp_id")
    private Empresa empresa;
    
    @ManyToOne
    @JoinColumn(name = "cbt_age_id")
    private AgendamentoServico agendamento;
    
    @Column(name = "cbt_pergunta", length = 300)
    private String pergunta;
    
    @Column(name = "cbt_resposta", length = 500)
    private String resposta;
    
    @Column(name = "cbt_data")
    private LocalDate data;
}

