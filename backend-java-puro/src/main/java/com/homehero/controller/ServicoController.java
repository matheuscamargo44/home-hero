package com.homehero.controller;

import com.homehero.dao.ServicoDAO;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ServicoController {
    private ServicoDAO servicoDAO = new ServicoDAO();
    private Gson gson = new Gson();

    public String listar() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("success", true);
            response.put("servicos", servicoDAO.listarAtivos());
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao buscar serviços: " + e.getMessage());
        }
        return gson.toJson(response);
    }
}

