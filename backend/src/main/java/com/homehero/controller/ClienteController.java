package com.homehero.controller;

import com.homehero.model.Cliente;
import com.homehero.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Map<String, Object>> getByCpf(@PathVariable String cpf) {
        try {
            String cpfLimpo = cpf.replaceAll("[^0-9]", "");
            
            Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpfLimpo);
            
            if (clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("cliente", cliente);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "CPF não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao buscar cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}




