package com.homehero.controller;

import com.homehero.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin(origins = "*")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("servicos", servicoRepository.findByAtivoTrue());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao buscar serviços: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}






