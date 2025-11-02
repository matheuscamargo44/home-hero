package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "empresa_servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaServico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ems_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "ems_emp_id", nullable = false)
    private Empresa empresa;
    
    @ManyToOne
    @JoinColumn(name = "ems_ser_id", nullable = false)
    private Servico servico;
    
    @Column(name = "ems_preco_oferta")
    private Float precoOferta;
    
    @Column(name = "ems_ativo")
    private Boolean ativo = true;
    
    @Column(name = "ems_data_cadastro")
    private LocalDate dataCadastro;
}

