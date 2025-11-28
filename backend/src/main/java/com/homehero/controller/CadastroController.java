package com.homehero.controller;

import com.homehero.service.CadastroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cadastro")
@CrossOrigin(origins = "*")
public class CadastroController {

    private final CadastroService cadastroService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CadastroController(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @PostMapping("/cliente")
    public ResponseEntity<Map<String, Object>> cadastrarCliente(@RequestBody Map<String, String> dados) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            LocalDate nascimento = LocalDate.parse(dados.get("nascimento"), DATE_FORMATTER);
            
            cadastroService.inserirCliente(
                dados.get("nome"),
                dados.get("cpf"),
                nascimento,
                dados.get("senha"),
                dados.get("endLogradouro"),
                dados.get("endNumero"),
                dados.get("endComplemento"),
                dados.get("endBairro"),
                dados.get("endCidade"),
                dados.get("endUf"),
                dados.get("endCep"),
                dados.get("email"),
                dados.get("telefone")
            );
            
            response.put("success", true);
            response.put("message", "Cliente cadastrado com sucesso!");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao cadastrar cliente: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/prestador")
    public ResponseEntity<Map<String, Object>> cadastrarPrestador(@RequestBody Map<String, String> dados) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            LocalDate nascimento = LocalDate.parse(dados.get("nascimento"), DATE_FORMATTER);
            
            cadastroService.inserirPrestador(
                dados.get("nome"),
                dados.get("cpf"),
                nascimento,
                dados.get("areas"),
                dados.get("experiencia"),
                dados.get("certificados"),
                dados.get("senha"),
                dados.get("endLogradouro"),
                dados.get("endNumero"),
                dados.get("endComplemento"),
                dados.get("endBairro"),
                dados.get("endCidade"),
                dados.get("endUf"),
                dados.get("endCep"),
                dados.get("email"),
                dados.get("telefone")
            );
            
            response.put("success", true);
            response.put("message", "Prestador cadastrado com sucesso!");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao cadastrar prestador: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

