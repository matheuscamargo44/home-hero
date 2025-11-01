package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telefone")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tel_id")
    private Integer id;
    
    @Column(name = "tel_numero_telefone", nullable = false, length = 20)
    private String numeroTelefone;
    
    @Column(name = "tel_tipo_telefone", length = 20)
    private String tipoTelefone; // Ex: Celular, Residencial, Comercial
    
    @ManyToOne
    @JoinColumn(name = "tel_cli_id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "tel_pre_id")
    private Prestador prestador;
    
    @ManyToOne
    @JoinColumn(name = "tel_emp_id")
    private Empresa empresa;
}

