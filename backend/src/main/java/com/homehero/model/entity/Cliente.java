package com.homehero.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "cli_nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "cli_cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "cli_nascimento", nullable = false)
    private LocalDate nascimento;

    @Column(name = "cli_senha", nullable = false, length = 60)
    private String senha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_id", nullable = false)
    private Endereco endereco;

    @Column(name = "cli_email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "cli_telefone", nullable = false, unique = true, length = 20)
    private String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<AgendamentoServico> agendamentos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes;
}
