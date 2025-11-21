package com.homehero.model;

public class Servico {
    private Integer id;
    private String nome;
    private String descricao;
    private Float precoBase;
    private Boolean ativo;
    private Integer categoriaId;

    public Servico() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Float getPrecoBase() { return precoBase; }
    public void setPrecoBase(Float precoBase) { this.precoBase = precoBase; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }
}

