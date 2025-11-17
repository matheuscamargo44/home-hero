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

/**
 * Controller responsável pela autenticação unificada do sistema
 * Permite login tanto para administradores (por email) quanto para clientes (por CPF)
 */
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

    /**
     * Endpoint de login unificado
     * Aceita email (para admin) ou CPF (para cliente) no campo 'identifier'
     * 
     * @param credentials Mapa contendo 'identifier' (email ou CPF) e 'senha'
     * @return ResponseEntity com dados do usuário autenticado ou mensagem de erro
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        try {
            String identifier = credentials.get("identifier");
            String senha = credentials.get("senha");

            // Validação dos campos obrigatórios
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

            // Se não é admin, tenta verificar se é cliente ou prestador (por CPF)
            // Remove caracteres não numéricos do CPF
            String cpfLimpo = identifier.replaceAll("[^0-9]", "");
            if (cpfLimpo.length() == 11) {
                // Tenta cliente primeiro
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

                // Se não é cliente, tenta prestador
                Optional<Prestador> prestadorOpt = prestadorRepository.findByCpf(cpfLimpo);
                if (prestadorOpt.isPresent()) {
                    Prestador prestador = prestadorOpt.get();
                    if (prestador.getSenha().equals(senha)) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("userType", "prestador");
                        response.put("prestador", prestador);
                        response.put("token", "prestador-token-" + prestador.getId());
                        return ResponseEntity.ok(response);
                    } else {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "Senha incorreta");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                    }
                }
            }

            // Se não encontrou admin, cliente nem prestador
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

