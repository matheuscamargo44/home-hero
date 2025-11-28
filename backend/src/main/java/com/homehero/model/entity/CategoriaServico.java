package com.homehero.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidade que representa a tabela categoria_servico.
 * Categorias de serviços oferecidos na plataforma (ex: Pintura, Marcenaria, etc).
 */
@Entity
@Table(name = "categoria_servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Integer id;

    @Column(name = "cat_nome", nullable = false, length = 60)
    private String nome;

    @Column(name = "cat_descricao", length = 255)
    private String descricao;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Servico> servicos;
}
