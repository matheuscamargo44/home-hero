package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ema_id")
    private Integer id;
    
    @Column(name = "ema_endereco_email", nullable = false, length = 120)
    private String enderecoEmail;
    
    @ManyToOne
    @JoinColumn(name = "ema_cli_id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "ema_pre_id")
    private Prestador prestador;
    
    @ManyToOne
    @JoinColumn(name = "ema_emp_id")
    private Empresa empresa;
}

