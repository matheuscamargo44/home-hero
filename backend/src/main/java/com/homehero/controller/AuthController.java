package com.homehero.controller;

import com.homehero.model.Admin;
import com.homehero.model.Cliente;
import com.homehero.repository.AdminRepository;
import com.homehero.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        try {
            String identifier = credentials.get("identifier");
            String senha = credentials.get("senha");

            if (identifier == null || senha == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Email/CPF e senha são obrigatórios");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Primeiro, tenta verificar se é um admin (por email)
            Optional<Admin> adminOpt = adminRepository.findByEmail(identifier);
            if (adminOpt.isPresent()) {
                Admin admin = adminOpt.get();
                if (admin.getSenha().equals(senha)) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("userType", "admin");
                    response.put("admin", admin);
                    response.put("token", "admin-token-" + admin.getId());
                    return ResponseEntity.ok(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Senha incorreta");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            }

            // Se não é admin, tenta verificar se é cliente (por CPF)
            String cpfLimpo = identifier.replaceAll("[^0-9]", "");
            if (cpfLimpo.length() == 11) {
                Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpfLimpo);
                if (clienteOpt.isPresent()) {
                    Cliente cliente = clienteOpt.get();
                    if (cliente.getSenha().equals(senha)) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("userType", "cliente");
                        response.put("cliente", cliente);
                        response.put("token", "cliente-token-" + cliente.getId());
                        return ResponseEntity.ok(response);
                    } else {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "Senha incorreta");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                    }
                }
            }

            // Se não encontrou nem admin nem cliente
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Email/CPF ou senha incorretos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao processar login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

