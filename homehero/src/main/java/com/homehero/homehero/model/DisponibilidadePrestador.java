package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "disponibilidade_prestador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadePrestador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dis_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "dis_pre_id", nullable = false)
    private Prestador prestador;
    
    @Column(name = "dis_dia_semana", length = 10)
    private String diaSemana; // Ex: Segunda, Terça, etc.
    
    @Column(name = "dis_janela", length = 20)
    private String janela; // Ex: Manhã, Tarde, Noite, 08:00-12:00
    
    @Column(name = "dis_ativo")
    private Boolean ativo = true;
}

