package com.homehero.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ViewService {

    private final JdbcTemplate jdbcTemplate;

    public ViewService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> listarCategorias() {
        return jdbcTemplate.queryForList(
            "SELECT * FROM view_lista_categorias ORDER BY cat_nome"
        );
    }

    public List<Map<String, Object>> listarServicos() {
        return jdbcTemplate.queryForList(
            "SELECT * FROM view_lista_servicos ORDER BY ser_nome"
        );
    }
}

