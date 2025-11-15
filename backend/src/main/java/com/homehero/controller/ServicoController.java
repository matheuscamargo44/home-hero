package com.homehero.controller;

import com.homehero.model.Servico;
import com.homehero.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller responsável pelas operações relacionadas a serviços
 */
@RestController
@RequestMapping("/api/servicos")
@CrossOrigin(origins = "*")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    /**
     * Endpoint para listar todos os serviços ativos
     * 
     * @return ResponseEntity com lista de serviços ativos
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        try {
            List<Servico> servicos = servicoRepository.findByAtivoTrue();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("servicos", servicos);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao buscar serviços: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}






