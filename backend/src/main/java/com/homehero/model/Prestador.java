package com.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "prestador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pre_id")
    private Integer id;

    @Column(name = "pre_nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "pre_cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "pre_nascimento", nullable = false)
    private LocalDate nascimento;

    @Column(name = "pre_areas", length = 120)
    private String areas;

    @Column(name = "pre_experiencia", length = 255)
    private String experiencia;

    @Column(name = "pre_certificados", length = 255)
    private String certificados;

    @Column(name = "pre_senha", nullable = false, length = 60)
    private String senha;

    @Column(name = "end_id", nullable = false)
    private Integer enderecoId;

    @Column(name = "pre_email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "pre_telefone", nullable = false, unique = true, length = 20)
    private String telefone;
}


