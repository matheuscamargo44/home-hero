package com.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um serviço oferecido na plataforma
 * Mapeia a tabela 'servico' do banco de dados
 */
@Entity
@Table(name = "servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servico {
    /**
     * ID único do serviço (chave primária)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ser_id")
    private Integer id;

    /**
     * Nome do serviço
     */
    @Column(name = "ser_nome", nullable = false, length = 80)
    private String nome;

    /**
     * Descrição detalhada do serviço
     */
    @Column(name = "ser_descricao", length = 255)
    private String descricao;

    /**
     * Preço base do serviço
     */
    @Column(name = "ser_preco_base")
    private Float precoBase;

    /**
     * Indica se o serviço está ativo no sistema
     */
    @Column(name = "ser_ativo")
    private Boolean ativo;

    /**
     * ID da categoria do serviço (chave estrangeira)
     */
    @Column(name = "ser_cat_id")
    private Integer categoriaId;
}






