package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id")
    private Integer id;
    
    @Column(name = "end_logradouro")
    private String logradouro;
    
    @Column(name = "end_numero")
    private String numero;
    
    @Column(name = "end_complemento")
    private String complemento;
    
    @Column(name = "end_bairro")
    private String bairro;
    
    @Column(name = "end_cidade")
    private String cidade;
    
    @Column(name = "end_uf")
    private String uf;
    
    @Column(name = "end_cep")
    private String cep;
}

