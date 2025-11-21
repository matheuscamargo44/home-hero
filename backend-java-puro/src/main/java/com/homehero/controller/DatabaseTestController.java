package com.homehero.controller;

import com.homehero.service.DatabaseTestService;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseTestController {
    private DatabaseTestService databaseTestService = new DatabaseTestService();
    private Gson gson = new Gson();

    public String getViews() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("success", true);
            response.put("data", databaseTestService.getAvailableViews());
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao listar views: " + e.getMessage());
        }
        return gson.toJson(response);
    }

    public String getProcedures() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("success", true);
            response.put("data", databaseTestService.getAvailableProcedures());
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao listar procedures: " + e.getMessage());
        }
        return gson.toJson(response);
    }

    public String getTriggers() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("success", true);
            response.put("data", databaseTestService.getAvailableTriggers());
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao listar triggers: " + e.getMessage());
        }
        return gson.toJson(response);
    }

    public String testView(String viewName) {
        Map<String, Object> response = new HashMap<>();
        try {
            var results = databaseTestService.executeView(viewName);
            response.put("success", true);
            response.put("data", results);
            response.put("count", results.size());
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao executar view: " + e.getMessage());
        }
        return gson.toJson(response);
    }

    public String testProcedure(String procedureName, String body) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> parameters = null;
            if (body != null && !body.trim().isEmpty()) {
                parameters = gson.fromJson(body, Map.class);
            }
            response = databaseTestService.executeProcedure(procedureName, parameters);
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao executar procedure: " + e.getMessage());
            response.put("data", new java.util.ArrayList<>());
        }
        return gson.toJson(response);
    }

    public String getTriggerInfo(String triggerName) {
        Map<String, Object> response = new HashMap<>();
        try {
            response = databaseTestService.getTriggerInfo(triggerName);
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao obter informações do trigger: " + e.getMessage());
        }
        return gson.toJson(response);
    }

    public String testTrigger(String tableName, String operation) {
        Map<String, Object> response = new HashMap<>();
        try {
            response = databaseTestService.testTrigger(tableName, operation);
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao testar trigger: " + e.getMessage());
        }
        return gson.toJson(response);
    }

    public String executeTriggerAction(String triggerName, String body) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> params = null;
            if (body != null && !body.trim().isEmpty()) {
                params = gson.fromJson(body, Map.class);
            }
            response = databaseTestService.executeTriggerAction(triggerName, params);
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao executar ação do trigger: " + e.getMessage());
        }
        return gson.toJson(response);
    }
}

