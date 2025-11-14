package com.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "ser_cat_id")
    private Integer categoriaId;
}




