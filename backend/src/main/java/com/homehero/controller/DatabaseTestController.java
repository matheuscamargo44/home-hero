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
        return response(databaseTestService.getAvailableViews(), "Erro ao listar views");
    }

    @GetMapping("/procedures")
    public ResponseEntity<Map<String, Object>> getProcedures() {
        return response(databaseTestService.getAvailableProcedures(), "Erro ao listar procedures");
    }

    @GetMapping("/triggers")
    public ResponseEntity<Map<String, Object>> getTriggers() {
        return response(databaseTestService.getAvailableTriggers(), "Erro ao listar triggers");
    }

    @PostMapping("/view/{viewName}")
    public ResponseEntity<Map<String, Object>> testView(@PathVariable String viewName) {
        try {
            var results = databaseTestService.executeView(viewName);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", results);
            response.put("count", results.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return errorResponse("Erro ao executar view: " + e.getMessage());
        }
    }

    @PostMapping("/procedure/{procedureName}")
    public ResponseEntity<Map<String, Object>> testProcedure(
            @PathVariable String procedureName,
            @RequestBody(required = false) Map<String, Object> parameters) {
        try {
            return ResponseEntity.ok(databaseTestService.executeProcedure(procedureName, parameters));
        } catch (Exception e) {
            return errorResponse("Erro ao executar procedure: " + e.getMessage());
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
            return ResponseEntity.ok(databaseTestService.executeTriggerAction(triggerName, params != null ? params : new HashMap<>()));
        } catch (Exception e) {
            return errorResponse("Erro ao executar ação do trigger: " + e.getMessage());
        }
    }

    private ResponseEntity<Map<String, Object>> response(Object data, String errorMsg) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return errorResponse(errorMsg + ": " + e.getMessage());
        }
    }

    private ResponseEntity<Map<String, Object>> errorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
}

