package com.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cli_id")
    private Integer id;

    @Column(name = "cli_nome_completo", nullable = false, length = 80)
    private String nomeCompleto;

    @Column(name = "cli_cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "cli_data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "cli_senha", nullable = false, length = 60)
    private String senha;

    @Column(name = "cli_endereco_id", nullable = false)
    private Integer enderecoId;

    @Column(name = "cli_email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "cli_telefone", nullable = false, unique = true, length = 20)
    private String telefone;
}





