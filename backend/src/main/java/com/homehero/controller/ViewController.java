package com.homehero.controller;

import com.homehero.service.ViewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/views")
@CrossOrigin(origins = "*")
public class ViewController {

    private final ViewService viewService;

    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("/categorias")
    public ResponseEntity<Map<String, Object>> listarCategorias() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> categorias = viewService.listarCategorias();
            response.put("success", true);
            response.put("data", categorias);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao listar categorias: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/servicos")
    public ResponseEntity<Map<String, Object>> listarServicos() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> servicos = viewService.listarServicos();
            response.put("success", true);
            response.put("data", servicos);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao listar serviços: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

