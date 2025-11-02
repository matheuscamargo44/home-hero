package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "chat_mensagem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMensagem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cha_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "cha_age_id")
    private AgendamentoServico agendamento;
    
    @Column(name = "cha_remetente_tipo", length = 20)
    private String remetenteTipo;
    
    @Column(name = "cha_remetente_id")
    private Integer remetenteId;
    
    @Column(name = "cha_destinatario_tipo", length = 20)
    private String destinatarioTipo;
    
    @Column(name = "cha_destinatario_id")
    private Integer destinatarioId;
    
    @Column(name = "cha_mensagem", length = 500)
    private String mensagem;
    
    @Column(name = "cha_data")
    private LocalDate data;
}

