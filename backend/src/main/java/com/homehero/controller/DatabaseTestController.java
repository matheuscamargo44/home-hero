package com.homehero.controller;

import com.homehero.service.DatabaseTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/database-test")
@CrossOrigin(origins = "*")
public class DatabaseTestController {

    @Autowired
    private DatabaseTestService databaseTestService;

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

    @GetMapping("/trigger/{triggerName}")
    public ResponseEntity<Map<String, Object>> getTriggerInfo(@PathVariable String triggerName) {
        return ResponseEntity.ok(databaseTestService.getTriggerInfo(triggerName));
    }

    @PostMapping("/trigger/{tableName}")
    public ResponseEntity<Map<String, Object>> testTrigger(
            @PathVariable String tableName,
            @RequestParam(required = false, defaultValue = "SELECT") String operation) {
        return ResponseEntity.ok(databaseTestService.testTrigger(tableName, operation));
    }

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

