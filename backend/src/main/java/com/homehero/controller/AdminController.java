package com.homehero.controller;

import com.homehero.model.Admin;
import com.homehero.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controller responsável pelas operações relacionadas a administradores
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Endpoint para login de administrador
     * 
     * @param credentials Mapa contendo 'email' e 'senha'
     * @return ResponseEntity com dados do admin autenticado ou mensagem de erro
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String senha = credentials.get("senha");

            Optional<Admin> adminOpt = adminRepository.findByEmail(email);

            if (adminOpt.isPresent() && adminOpt.get().getSenha().equals(senha)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("admin", adminOpt.get());
                response.put("token", "admin-token-" + adminOpt.get().getId());
                return ResponseEntity.ok(response);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Email ou senha incorretos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao processar login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Endpoint para criar o administrador padrão do sistema
     * Cria o admin apenas se ainda não existir
     * 
     * @return ResponseEntity informando se o admin foi criado ou já existia
     */
    @PostMapping("/create-default")
    public ResponseEntity<Map<String, Object>> createDefaultAdmin() {
        try {
            if (!adminRepository.existsByEmail("admin@homehero.com")) {
                Admin admin = new Admin();
                admin.setNome("Administrador");
                admin.setEmail("admin@homehero.com");
                admin.setSenha("admin123");
                adminRepository.save(admin);

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Admin padrão criado com sucesso");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Admin já existe");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao criar admin: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Endpoint para verificar se um token pertence a um administrador
     * 
     * @param token Token de autorização do header
     * @return ResponseEntity indicando se o token é válido e pertence a um admin
     */
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyAdmin(@RequestHeader(value = "Authorization", required = false) String token) {
        Map<String, Object> response = new HashMap<>();

        if (token != null && token.startsWith("admin-token-")) {
            response.put("success", true);
            response.put("isAdmin", true);
            return ResponseEntity.ok(response);
        }

        response.put("success", false);
        response.put("isAdmin", false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}






