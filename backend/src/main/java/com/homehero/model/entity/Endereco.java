package com.homehero.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidade que representa a tabela endereco.
 * Usada para armazenar informações de endereço de clientes e prestadores.
 */
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

    @Column(name = "end_logradouro", length = 100)
    private String logradouro;

    @Column(name = "end_numero", length = 15)
    private String numero;

    @Column(name = "end_complemento", length = 60)
    private String complemento;

    @Column(name = "end_bairro", length = 60)
    private String bairro;

    @Column(name = "end_cidade", length = 60)
    private String cidade;

    @Column(name = "end_uf", length = 2)
    private String uf;

    @Column(name = "end_cep", length = 10)
    private String cep;

    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL)
    private List<Cliente> clientes;

    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL)
    private List<Prestador> prestadores;
}

