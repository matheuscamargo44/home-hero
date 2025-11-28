package com.homehero.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidade que representa a tabela servico.
 * Serviços oferecidos na plataforma (ex: Pintura Interna, Mesa de Jantar, etc).
 */
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
    private Boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private CategoriaServico categoria;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
    private List<AgendamentoServico> agendamentos;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
    private List<PrestadorServico> prestadores;
}
