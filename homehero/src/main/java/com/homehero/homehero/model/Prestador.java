package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    
    @Column(name = "pre_nome_completo", nullable = false)
    private String nomeCompleto;
    
    @Column(name = "pre_cpf", length = 14, unique = true)
    private String cpf;
    
    @Column(name = "pre_data_nascimento")
    private LocalDate dataNascimento;
    
    @Column(name = "pre_areas_atuacao", length = 120)
    private String areasAtuacao;
    
    @Column(name = "pre_experiencia", length = 255)
    private String experiencia;
    
    @Column(name = "pre_certificados", length = 255)
    private String certificados;
    
    @Column(name = "pre_senha", length = 60)
    private String senha; // Será criptografada com BCrypt
    
    @ManyToOne
    @JoinColumn(name = "pre_endereco_id")
    private Endereco endereco;
    
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<Email> emails;
    
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<Telefone> telefones;
    
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<AgendamentoServico> agendamentos;
    
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<RegistroRegiao> registrosRegiao;
    
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<PrestadorServico> prestadoresServicos;
    
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<CertificacaoPrestador> certificacoes;
    
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<DisponibilidadePrestador> disponibilidades;
    
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes;
    
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<Notificacao> notificacoes;
    
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<DisputaReembolso> disputas;
}

