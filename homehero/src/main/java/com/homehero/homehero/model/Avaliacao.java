package com.homehero.homehero.model;

import com.homehero.homehero.listener.AvaliacaoListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "avaliacao")
@EntityListeners(AvaliacaoListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ava_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "ava_age_id", nullable = false)
    private AgendamentoServico agendamento;
    
    @ManyToOne
    @JoinColumn(name = "ava_cli_id", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "ava_pre_id", nullable = false)
    private Prestador prestador;
    
    @Column(name = "ava_nota")
    private Integer nota;
    
    @Column(name = "ava_comentario", length = 400)
    private String comentario;
    
    @Column(name = "ava_data")
    private LocalDate data;
}

