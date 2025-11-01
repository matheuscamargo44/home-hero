package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "prestador_servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestadorServico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prs_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "prs_pre_id", nullable = false)
    private Prestador prestador;
    
    @ManyToOne
    @JoinColumn(name = "prs_ser_id", nullable = false)
    private Servico servico;
    
    @Column(name = "prs_preco_oferta")
    private Float precoOferta;
    
    @Column(name = "prs_ativo")
    private Boolean ativo = true;
    
    @Column(name = "prs_data_cadastro")
    private LocalDate dataCadastro;
}

