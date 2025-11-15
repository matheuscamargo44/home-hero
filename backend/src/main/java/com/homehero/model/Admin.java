package com.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um administrador do sistema
 * Mapeia a tabela 'admin' do banco de dados
 */
@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    /**
     * ID único do administrador (chave primária)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adm_id")
    private Integer id;

    /**
     * Nome completo do administrador
     */
    @Column(name = "adm_nome", nullable = false, length = 80)
    private String nome;

    /**
     * Email do administrador (único no sistema)
     */
    @Column(name = "adm_email", nullable = false, unique = true, length = 120)
    private String email;

    /**
     * Senha do administrador
     */
    @Column(name = "adm_senha", nullable = false, length = 60)
    private String senha;
}






