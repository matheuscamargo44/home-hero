package com.homehero.controller;

import com.homehero.model.Prestador;
import com.homehero.repository.PrestadorRepository;
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
import java.util.Optional;

/**
 * Controller responsável pelas operações relacionadas a prestadores
 */
@RestController
@RequestMapping("/api/prestadores")
@CrossOrigin(origins = "*")
public class PrestadorController {

    @Autowired
    private PrestadorRepository prestadorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Endpoint para buscar um prestador pelo CPF
     * Remove caracteres não numéricos do CPF antes de buscar
     * 
     * @param cpf CPF do prestador (pode conter pontos e traços)
     * @return ResponseEntity com dados do prestador ou mensagem de erro
     */
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Map<String, Object>> getByCpf(@PathVariable String cpf) {
        try {
            // Remove caracteres não numéricos do CPF
            String cpfLimpo = cpf.replaceAll("[^0-9]", "");
            
            Optional<Prestador> prestadorOpt = prestadorRepository.findByCpf(cpfLimpo);
            
            if (prestadorOpt.isPresent()) {
                Prestador prestador = prestadorOpt.get();
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("prestador", prestador);
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
            response.put("message", "Erro ao buscar prestador: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Endpoint para criar um novo prestador
     * Cria primeiro o endereço e depois o prestador
     * 
     * @param dados Mapa contendo os dados do prestador e endereço
     * @return ResponseEntity com o prestador criado ou mensagem de erro
     */
    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<Map<String, Object>> cadastrar(@RequestBody Map<String, Object> dados) {
        try {
            // Validação de campos obrigatórios
            if (dados.get("nomeCompleto") == null || dados.get("cpf") == null || 
                dados.get("email") == null || dados.get("telefone") == null || 
                dados.get("senha") == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Campos obrigatórios: nomeCompleto, cpf, email, telefone, senha");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Verificar se CPF já existe
            String cpf = dados.get("cpf").toString().replaceAll("[^0-9]", "");
            Optional<Prestador> prestadorExistente = prestadorRepository.findByCpf(cpf);
            if (prestadorExistente.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "CPF já cadastrado");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Verificar se email já existe
            String email = dados.get("email").toString();
            Query emailQuery = entityManager.createNativeQuery(
                "SELECT COUNT(*) FROM prestador WHERE pre_email = ?"
            );
            emailQuery.setParameter(1, email);
            Long emailCount = ((Number) emailQuery.getSingleResult()).longValue();
            if (emailCount > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Email já cadastrado");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Criar endereço primeiro
            String sqlEndereco = "INSERT INTO endereco (end_logradouro, end_numero, end_complemento, " +
                                "end_bairro, end_cidade, end_uf, end_cep) VALUES (?, ?, ?, ?, ?, ?, ?)";
            Query queryEndereco = entityManager.createNativeQuery(sqlEndereco);
            queryEndereco.setParameter(1, dados.getOrDefault("logradouro", "").toString());
            queryEndereco.setParameter(2, dados.getOrDefault("numero", "").toString());
            queryEndereco.setParameter(3, dados.getOrDefault("complemento", "").toString());
            queryEndereco.setParameter(4, dados.getOrDefault("bairro", "").toString());
            queryEndereco.setParameter(5, dados.getOrDefault("cidade", "").toString());
            queryEndereco.setParameter(6, dados.getOrDefault("uf", "").toString());
            queryEndereco.setParameter(7, dados.getOrDefault("cep", "").toString().replaceAll("[^0-9-]", ""));
            queryEndereco.executeUpdate();

            // Obter o ID do endereço criado
            Query idQuery = entityManager.createNativeQuery("SELECT LAST_INSERT_ID()");
            Integer enderecoId = ((Number) idQuery.getSingleResult()).intValue();

            // Converter data de nascimento
            LocalDate dataNascimento = null;
            if (dados.get("dataNascimento") != null && !dados.get("dataNascimento").toString().isEmpty()) {
                try {
                    dataNascimento = LocalDate.parse(dados.get("dataNascimento").toString());
                } catch (Exception e) {
                    // Se falhar, usa data padrão
                    dataNascimento = LocalDate.of(1990, 1, 1);
                }
            } else {
                dataNascimento = LocalDate.of(1990, 1, 1);
            }

            // Criar prestador
            Prestador prestador = new Prestador();
            prestador.setNome(dados.get("nomeCompleto").toString());
            prestador.setCpf(cpf);
            prestador.setNascimento(dataNascimento);
            prestador.setAreas(dados.getOrDefault("areasAtuacao", dados.getOrDefault("areas", "")).toString());
            prestador.setExperiencia(dados.getOrDefault("experiencia", "").toString());
            prestador.setCertificados(dados.getOrDefault("certificados", "").toString());
            prestador.setSenha(dados.get("senha").toString());
            prestador.setEnderecoId(enderecoId);
            prestador.setEmail(email);
            prestador.setTelefone(dados.get("telefone").toString().replaceAll("[^0-9() -]", ""));

            Prestador prestadorSalvo = prestadorRepository.save(prestador);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Prestador cadastrado com sucesso");
            response.put("prestador", prestadorSalvo);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao cadastrar prestador: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

