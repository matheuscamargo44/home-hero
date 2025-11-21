package com.homehero.controller;

import com.homehero.model.Cliente;
import com.homehero.repository.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Map<String, Object>> getByCpf(@PathVariable String cpf) {
        try {
            String cpfLimpo = cpf.replaceAll("[^0-9]", "");
            return clienteRepository.findByCpf(cpfLimpo)
                .map(cliente -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("cliente", cliente);
                    return ResponseEntity.ok(response);
                })
                .orElse(errorResponse("CPF não encontrado", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return errorResponse("Erro ao buscar cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<Map<String, Object>> cadastrar(@RequestBody Map<String, Object> dados) {
        try {
            if (!validarCampos(dados)) {
                return errorResponse("Campos obrigatórios: nomeCompleto, cpf, email, telefone, senha", HttpStatus.BAD_REQUEST);
            }

            String cpf = dados.get("cpf").toString().replaceAll("[^0-9]", "");
            if (clienteRepository.findByCpf(cpf).isPresent()) {
                return errorResponse("CPF já cadastrado", HttpStatus.BAD_REQUEST);
            }

            String email = dados.get("email").toString();
            if (emailExiste(email)) {
                return errorResponse("Email já cadastrado", HttpStatus.BAD_REQUEST);
            }

            Integer enderecoId = criarEndereco(dados);
            Cliente cliente = criarCliente(dados, cpf, email, enderecoId);
            Cliente clienteSalvo = clienteRepository.save(cliente);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cliente cadastrado com sucesso");
            response.put("cliente", clienteSalvo);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return errorResponse("Erro ao cadastrar cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validarCampos(Map<String, Object> dados) {
        return dados.get("nomeCompleto") != null && dados.get("cpf") != null &&
               dados.get("email") != null && dados.get("telefone") != null && dados.get("senha") != null;
    }

    private boolean emailExiste(String email) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM cliente WHERE cli_email = ?");
        query.setParameter(1, email);
        return ((Number) query.getSingleResult()).longValue() > 0;
    }

    private Integer criarEndereco(Map<String, Object> dados) {
        String sql = "INSERT INTO endereco (end_logradouro, end_numero, end_complemento, end_bairro, end_cidade, end_uf, end_cep) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, dados.getOrDefault("logradouro", "").toString());
        query.setParameter(2, dados.getOrDefault("numero", "").toString());
        query.setParameter(3, dados.getOrDefault("complemento", "").toString());
        query.setParameter(4, dados.getOrDefault("bairro", "").toString());
        query.setParameter(5, dados.getOrDefault("cidade", "").toString());
        query.setParameter(6, dados.getOrDefault("uf", "").toString());
        query.setParameter(7, dados.getOrDefault("cep", "").toString().replaceAll("[^0-9-]", ""));
        query.executeUpdate();

        Query idQuery = entityManager.createNativeQuery("SELECT LAST_INSERT_ID()");
        return ((Number) idQuery.getSingleResult()).intValue();
    }

    private Cliente criarCliente(Map<String, Object> dados, String cpf, String email, Integer enderecoId) {
        LocalDate dataNascimento = parseDataNascimento(dados.get("dataNascimento"));
        
        Cliente cliente = new Cliente();
        cliente.setNomeCompleto(dados.get("nomeCompleto").toString());
        cliente.setCpf(cpf);
        cliente.setDataNascimento(dataNascimento);
        cliente.setSenha(dados.get("senha").toString());
        cliente.setEnderecoId(enderecoId);
        cliente.setEmail(email);
        cliente.setTelefone(dados.get("telefone").toString().replaceAll("[^0-9() -]", ""));
        return cliente;
    }

    private LocalDate parseDataNascimento(Object data) {
        if (data != null && !data.toString().isEmpty()) {
            try {
                return LocalDate.parse(data.toString());
            } catch (Exception e) {
                return LocalDate.of(1990, 1, 1);
            }
        }
        return LocalDate.of(1990, 1, 1);
    }

    private ResponseEntity<Map<String, Object>> errorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}




