package com.homehero.model;

import java.time.LocalDate;

public class Prestador {
    private Integer id;
    private String nome;
    private String cpf;
    private LocalDate nascimento;
    private String areas;
    private String experiencia;
    private String certificados;
    private String senha;
    private Integer enderecoId;
    private String email;
    private String telefone;

    public Prestador() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public LocalDate getNascimento() { return nascimento; }
    public void setNascimento(LocalDate nascimento) { this.nascimento = nascimento; }

    public String getAreas() { return areas; }
    public void setAreas(String areas) { this.areas = areas; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public String getCertificados() { return certificados; }
    public void setCertificados(String certificados) { this.certificados = certificados; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Integer getEnderecoId() { return enderecoId; }
    public void setEnderecoId(Integer enderecoId) { this.enderecoId = enderecoId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}

