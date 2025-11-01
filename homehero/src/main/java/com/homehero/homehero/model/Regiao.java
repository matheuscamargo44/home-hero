package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "regiao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Regiao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_id")
    private Integer id;
    
    @Column(name = "reg_nome", nullable = false, length = 80)
    private String nome;
    
    @Column(name = "reg_cidade", length = 60)
    private String cidade;
    
    @Column(name = "reg_uf", length = 2)
    private String uf;
}

