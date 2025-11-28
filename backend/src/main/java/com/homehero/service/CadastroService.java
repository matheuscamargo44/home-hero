package com.homehero.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class CadastroService {

    private final JdbcTemplate jdbcTemplate;

    public CadastroService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void inserirCliente(String nome, String cpf, LocalDate nascimento, String senha,
                               String endLogradouro, String endNumero, String endComplemento,
                               String endBairro, String endCidade, String endUf, String endCep,
                               String email, String telefone) {
        jdbcTemplate.update(
            "CALL inserir_cliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            nome, cpf, Date.valueOf(nascimento), senha,
            endLogradouro, endNumero, endComplemento, endBairro, endCidade, endUf, endCep,
            email, telefone
        );
    }

    public void inserirPrestador(String nome, String cpf, LocalDate nascimento, String areas,
                                  String experiencia, String certificados, String senha,
                                  String endLogradouro, String endNumero, String endComplemento,
                                  String endBairro, String endCidade, String endUf, String endCep,
                                  String email, String telefone) {
        jdbcTemplate.update("CALL inserir_prestador(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                nome, cpf, Date.valueOf(nascimento), areas, experiencia, certificados, senha,
                endLogradouro, endNumero, endComplemento, endBairro, endCidade, endUf, endCep,
                email, telefone);
    }
}

