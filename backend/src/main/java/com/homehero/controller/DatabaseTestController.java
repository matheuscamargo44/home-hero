package com.homehero.controller;

import com.homehero.service.DatabaseTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller responsável por testes e consultas ao banco de dados
 * Permite testar views, procedures e triggers do MySQL
 */
@RestController
@RequestMapping("/api/database-test")
@CrossOrigin(origins = "*")
public class DatabaseTestController {

    @Autowired
    private DatabaseTestService databaseTestService;

    /**
     * Lista todas as views disponíveis no banco de dados
     * @return Lista de nomes das views
     */
    @GetMapping("/views")
    public ResponseEntity<Map<String, Object>> getViews() {
        try {
            var views = databaseTestService.getAvailableViews();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", views);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao listar views: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Lista todas as stored procedures disponíveis no banco de dados
     * @return Lista de nomes das procedures
     */
    @GetMapping("/procedures")
    public ResponseEntity<Map<String, Object>> getProcedures() {
        try {
            var procedures = databaseTestService.getAvailableProcedures();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", procedures);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao listar procedures: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Lista todos os triggers disponíveis no banco de dados
     * @return Lista de nomes dos triggers
     */
    @GetMapping("/triggers")
    public ResponseEntity<Map<String, Object>> getTriggers() {
        try {
            var triggers = databaseTestService.getAvailableTriggers();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", triggers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao listar triggers: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Executa uma view e retorna os resultados
     * @param viewName Nome da view a ser executada
     * @return Resultados da view executada
     */
    @PostMapping("/view/{viewName}")
    public ResponseEntity<Map<String, Object>> testView(@PathVariable String viewName) {
        try {
            var results = databaseTestService.executeView(viewName);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", results);
            response.put("count", results.size());
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao executar view: " + e.getMessage());
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(response);
        }
    }

    /**
     * Executa uma stored procedure com parâmetros opcionais
     * @param procedureName Nome da procedure a ser executada
     * @param parameters Parâmetros para a procedure (opcional)
     * @return Resultados da procedure executada
     */
    @PostMapping("/procedure/{procedureName}")
    public ResponseEntity<Map<String, Object>> testProcedure(
            @PathVariable String procedureName,
            @RequestBody(required = false) Map<String, Object> parameters) {
        try {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(databaseTestService.executeProcedure(procedureName, parameters));
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao executar procedure: " + e.getMessage());
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(response);
        }
    }

    /**
     * Obtém informações detalhadas sobre um trigger
     * @param triggerName Nome do trigger
     * @return Informações do trigger (evento, tabela, ação, etc)
     */
    @GetMapping("/trigger/{triggerName}")
    public ResponseEntity<Map<String, Object>> getTriggerInfo(@PathVariable String triggerName) {
        return ResponseEntity.ok(databaseTestService.getTriggerInfo(triggerName));
    }

    /**
     * Testa um trigger de uma tabela específica
     * @param tableName Nome da tabela
     * @param operation Operação a ser testada (SELECT, INSERT, UPDATE, DELETE)
     * @return Resultado do teste do trigger
     */
    @PostMapping("/trigger/{tableName}")
    public ResponseEntity<Map<String, Object>> testTrigger(
            @PathVariable String tableName,
            @RequestParam(required = false, defaultValue = "SELECT") String operation) {
        return ResponseEntity.ok(databaseTestService.testTrigger(tableName, operation));
    }

    /**
     * Executa uma ação específica de um trigger
     * @param triggerName Nome do trigger
     * @param params Parâmetros para a execução (opcional)
     * @return Resultado da execução do trigger
     */
    @PostMapping("/trigger/{triggerName}/execute")
    public ResponseEntity<Map<String, Object>> executeTriggerAction(
            @PathVariable String triggerName,
            @RequestBody(required = false) Map<String, Object> params) {
        try {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(databaseTestService.executeTriggerAction(triggerName, params != null ? params : new HashMap<>()));
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao executar ação do trigger: " + e.getMessage());
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(response);
        }
    }
}

