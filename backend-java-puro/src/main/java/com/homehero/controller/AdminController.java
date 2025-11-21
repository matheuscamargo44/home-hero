package com.homehero.controller;

import com.homehero.dao.AdminDAO;
import com.homehero.model.Admin;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AdminController {
    private AdminDAO adminDAO = new AdminDAO();
    private Gson gson = new Gson();

    public String criarAdminPadrao() {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!adminDAO.existePorEmail("admin@homehero.com")) {
                Admin admin = new Admin();
                admin.setNome("Administrador");
                admin.setEmail("admin@homehero.com");
                admin.setSenha("admin123");
                adminDAO.criar(admin);
                response.put("success", true);
                response.put("message", "Admin padrão criado com sucesso");
            } else {
                response.put("success", false);
                response.put("message", "Admin já existe");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao criar admin: " + e.getMessage());
        }
        return gson.toJson(response);
    }

    public String verificarToken(String token) {
        Map<String, Object> response = new HashMap<>();
        boolean isAdmin = token != null && token.startsWith("admin-token-");
        response.put("success", isAdmin);
        response.put("isAdmin", isAdmin);
        return gson.toJson(response);
    }
}

