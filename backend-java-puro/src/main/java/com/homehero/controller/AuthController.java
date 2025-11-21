package com.homehero.controller;

import com.homehero.service.AuthService;
import com.google.gson.Gson;

import java.util.Map;

public class AuthController {
    private AuthService authService = new AuthService();
    private Gson gson = new Gson();

    public String login(String body) {
        Map<String, String> credentials = gson.fromJson(body, Map.class);
        String identifier = credentials.get("identifier");
        String senha = credentials.get("senha");
        
        Map<String, Object> response = authService.login(identifier, senha);
        return gson.toJson(response);
    }
}

