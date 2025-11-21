package com.homehero.controller;

import com.homehero.model.Admin;
import com.homehero.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/create-default")
    public ResponseEntity<Map<String, Object>> createDefaultAdmin() {
        try {
            if (!adminRepository.existsByEmail("admin@homehero.com")) {
                Admin admin = new Admin();
                admin.setNome("Administrador");
                admin.setEmail("admin@homehero.com");
                admin.setSenha("admin123");
                adminRepository.save(admin);
                return successResponse("Admin padrão criado com sucesso");
            }
            return successResponse("Admin já existe");
        } catch (Exception e) {
            return errorResponse("Erro ao criar admin: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyAdmin(@RequestHeader(value = "Authorization", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        boolean isAdmin = token != null && token.startsWith("admin-token-");
        response.put("success", isAdmin);
        response.put("isAdmin", isAdmin);
        return ResponseEntity.status(isAdmin ? HttpStatus.OK : HttpStatus.UNAUTHORIZED).body(response);
    }

    private ResponseEntity<Map<String, Object>> successResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Map<String, Object>> errorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}






