package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Integer id;
    
    @Column(name = "emp_razao_social", nullable = false)
    private String razaoSocial;
    
    @Column(name = "emp_cnpj", length = 18, unique = true)
    private String cnpj;
    
    @Column(name = "emp_area_atuacao", length = 120)
    private String areaAtuacao;
    
    @Column(name = "emp_representante_responsavel", length = 80)
    private String representanteResponsavel;
    
    @Column(name = "emp_senha", length = 60)
    private String senha; // Será criptografada com BCrypt
    
    @ManyToOne
    @JoinColumn(name = "emp_endereco_id")
    private Endereco endereco;
    
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Email> emails;
    
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Telefone> telefones;
    
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<AgendamentoServico> agendamentos;
    
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<FuncionarioEmpresa> funcionarios;
}

