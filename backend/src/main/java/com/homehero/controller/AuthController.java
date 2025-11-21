package com.homehero.controller;

import com.homehero.model.Admin;
import com.homehero.model.Cliente;
import com.homehero.model.Prestador;
import com.homehero.repository.AdminRepository;
import com.homehero.repository.ClienteRepository;
import com.homehero.repository.PrestadorRepository;
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

    @Autowired
    private PrestadorRepository prestadorRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        try {
            String identifier = credentials.get("identifier");
            String senha = credentials.get("senha");

            if (identifier == null || senha == null) {
                return errorResponse("Email/CPF e senha são obrigatórios", HttpStatus.BAD_REQUEST);
            }

            Optional<Admin> adminOpt = adminRepository.findByEmail(identifier);
            if (adminOpt.isPresent() && adminOpt.get().getSenha().equals(senha)) {
                return successLogin("admin", adminOpt.get(), adminOpt.get().getId());
            }

            String cpfLimpo = identifier.replaceAll("[^0-9]", "");
            if (cpfLimpo.length() == 11) {
                Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpfLimpo);
                if (clienteOpt.isPresent() && clienteOpt.get().getSenha().equals(senha)) {
                    return successLogin("cliente", clienteOpt.get(), clienteOpt.get().getId());
                }

                Optional<Prestador> prestadorOpt = prestadorRepository.findByCpf(cpfLimpo);
                if (prestadorOpt.isPresent() && prestadorOpt.get().getSenha().equals(senha)) {
                    return successLogin("prestador", prestadorOpt.get(), prestadorOpt.get().getId());
                }
            }

            return errorResponse("Email/CPF ou senha incorretos", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return errorResponse("Erro ao processar login: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Map<String, Object>> successLogin(String userType, Object user, Integer id) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("userType", userType);
        response.put(userType, user);
        response.put("token", userType + "-token-" + id);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Map<String, Object>> errorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}

