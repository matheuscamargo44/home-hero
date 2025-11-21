package com.homehero.model;

import java.time.LocalDate;

public class Cliente {
    private Integer id;
    private String nomeCompleto;
    private String cpf;
    private LocalDate dataNascimento;
    private String senha;
    private Integer enderecoId;
    private String email;
    private String telefone;

    public Cliente() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Integer getEnderecoId() { return enderecoId; }
    public void setEnderecoId(Integer enderecoId) { this.enderecoId = enderecoId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}

