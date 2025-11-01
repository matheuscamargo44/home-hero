package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "registro_regiao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRegiao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rre_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "rre_reg_id", nullable = false)
    private Regiao regiao;
    
    @ManyToOne
    @JoinColumn(name = "rre_cli_id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "rre_pre_id")
    private Prestador prestador;
    
    @Column(name = "rre_data_registro")
    private LocalDate dataRegistro;
}

