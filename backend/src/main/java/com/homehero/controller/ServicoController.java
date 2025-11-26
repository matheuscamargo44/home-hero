package com.homehero.controller; // Pacote onde o controlador está localizado.

import com.homehero.repository.ServicoRepository; // Repositório responsável por consultar os serviços.
import org.springframework.http.ResponseEntity; // Classe usada para montar respostas HTTP.
import org.springframework.web.bind.annotation.CrossOrigin; // Libera chamadas de qualquer origem.
import org.springframework.web.bind.annotation.GetMapping; // Marca métodos que respondem a GET.
import org.springframework.web.bind.annotation.RequestMapping; // Define o prefixo das rotas deste controlador.
import org.springframework.web.bind.annotation.RestController; // Indica que esta classe é um controlador REST.

import java.util.Map; // Estrutura simples usada para retornar dados JSON.

@RestController // Expõe os endpoints desta classe via HTTP.
@RequestMapping("/api/servicos") // Todas as rotas começam com /api/servicos.
@CrossOrigin(origins = "*") // Permite chamadas de qualquer domínio.
public class ServicoController { // Início da classe ServicoController.

    private final ServicoRepository servicoRepository; // Repositório injetado via construtor.

    public ServicoController(ServicoRepository servicoRepository) { // Construtor chamado pelo Spring.
        this.servicoRepository = servicoRepository; // Armazena o repositório para uso nos métodos.
    } // Fim do construtor.

    @GetMapping // Endpoint GET /api/servicos.
    public ResponseEntity<Map<String, Object>> getAll() { // Método que lista todos os serviços ativos.
        try { // Bloco protegido contra erros.
            return ResponseEntity.ok(Map.of( // Monta resposta HTTP 200.
                "success", true, // Indica sucesso.
                "servicos", servicoRepository.findByAtivoTrue() // Retorna apenas serviços marcados como ativos.
            )); // Fim do corpo de sucesso.
        } catch (Exception e) { // Captura falhas inesperadas.
            return ResponseEntity.status(500).body(Map.of( // Retorna HTTP 500 em caso de erro.
                "success", false, // Indica falha.
                "message", "Erro ao buscar serviços: " + e.getMessage() // Mensagem detalhando o problema.
            )); // Fim do corpo de erro.
        } // Fim do bloco try/catch.
    } // Fim do método getAll.
} // Fim da classe ServicoController.






