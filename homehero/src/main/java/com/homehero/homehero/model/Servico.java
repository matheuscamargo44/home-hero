package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ser_id")
    private Integer id;
    
    @Column(name = "ser_nome", nullable = false, length = 80)
    private String nome;
    
    @Column(name = "ser_descricao", length = 255)
    private String descricao;
    
    @Column(name = "ser_preco_base")
    private Float precoBase;
    
    @Column(name = "ser_ativo")
    private Boolean ativo = true;
    
    @ManyToOne
    @JoinColumn(name = "ser_cat_id")
    private CategoriaServico categoria;
    
    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrestadorServico> prestadoresServicos;
    
    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmpresaServico> empresasServicos;
    
    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AgendamentoServico> agendamentos;
}

