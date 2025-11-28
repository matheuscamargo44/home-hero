package com.homehero.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa a tabela admin.
 * Administradores da plataforma HomeHero.
 */
@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adm_id")
    private Integer id;

    @Column(name = "adm_nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "adm_email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "adm_senha", nullable = false, length = 60)
    private String senha;
}
