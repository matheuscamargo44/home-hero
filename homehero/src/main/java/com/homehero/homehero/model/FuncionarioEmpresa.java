package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "funcionario_empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioEmpresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fun_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fun_emp_id", nullable = false)
    private Empresa empresa;
    
    @Column(name = "fun_nome", nullable = false, length = 80)
    private String nome;
    
    @Column(name = "fun_especialidade", length = 80)
    private String especialidade;
    
    @Column(name = "fun_ativo")
    private Boolean ativo = true;
    
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private java.util.List<AtribuicaoServico> atribuicoes;
}

