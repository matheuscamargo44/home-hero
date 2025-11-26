package com.homehero.controller; // Define o pacote em que o controlador está localizado.

import com.homehero.model.procedure.ClienteProcedureRequest; // DTO usado para enviar parâmetros à procedure inserir_cliente.
import com.homehero.model.view.ClienteView; // Record que representa cada linha da view_dados_de_clientes.
import com.homehero.repository.jdbc.ClienteJdbcRepository; // Repositório JDBC simples responsável por falar com o banco.
import jakarta.validation.Valid; // Faz a validação automática do corpo da requisição.
import org.springframework.dao.DataAccessException; // Classe de erro usada para capturar falhas do JDBC.
import org.springframework.http.HttpStatus; // Enum com os status HTTP que serão retornados.
import org.springframework.http.ResponseEntity; // Wrapper para montar respostas HTTP completas.
import org.springframework.web.bind.annotation.CrossOrigin; // Anotação que habilita CORS.
import org.springframework.web.bind.annotation.GetMapping; // Indica que um método responde a GET.
import org.springframework.web.bind.annotation.PostMapping; // Indica que um método responde a POST.
import org.springframework.web.bind.annotation.RequestBody; // Diz ao Spring para desserializar o corpo da requisição.
import org.springframework.web.bind.annotation.RequestMapping; // Define o prefixo das rotas do controlador.
import org.springframework.web.bind.annotation.RestController; // Torna a classe um controlador REST.

import java.util.List; // Representa uma lista de elementos.
import java.util.Map; // Estrutura chave-valor usada para respostas simples.

@RestController // Informa ao Spring que esta classe trata requisições HTTP.
@RequestMapping("/api/clientes") // Define que todos os endpoints começam com /api/clientes.
@CrossOrigin(origins = "*") // Permite que qualquer domínio chame estas rotas (útil para demos).
public class ClienteController { // Início da classe ClienteController.

    private final ClienteJdbcRepository clienteJdbcRepository; // Guarda a referência do repositório JDBC usado pelos métodos.

    public ClienteController(ClienteJdbcRepository clienteJdbcRepository) { // Construtor que recebe o repositório por injeção de dependência.
        this.clienteJdbcRepository = clienteJdbcRepository; // Armazena o repositório em um campo para uso posterior.
    } // Fim do construtor.

    @PostMapping("/procedure") // Define o endpoint POST /api/clientes/procedure.
    public ResponseEntity<Map<String, Object>> inserirViaProcedure( // Assinatura do método que retornará um ResponseEntity com um Map.
        @Valid @RequestBody ClienteProcedureRequest request // Desserializa e valida o JSON enviado pelo cliente.
    ) { // Início do método inserirViaProcedure.
        try { // Inicia o bloco protegido contra exceções do banco.
            clienteJdbcRepository.inserirCliente(request); // Delegação direta para a procedure inserir_cliente.
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of( // Retorna HTTP 201 com um corpo simples.
                "success", true, // Chave que indica sucesso.
                "message", "Cliente inserido via procedure." // Mensagem exibida ao usuário.
            )); // Fim da construção da resposta de sucesso.
        } catch (DataAccessException e) { // Captura qualquer problema vindo da camada JDBC/MySQL.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( // Retorna HTTP 500 caso haja falha.
                "success", false, // Indica que a operação falhou.
                "message", "Erro ao executar inserir_cliente: " + e.getMessage() // Apresenta a mensagem de erro para facilitar o diagnóstico.
            )); // Fim da resposta de erro.
        } // Fim do bloco try/catch.
    } // Fim do método inserirViaProcedure.

    @GetMapping("/view") // Define o endpoint GET /api/clientes/view.
    public ResponseEntity<?> listarViaView() { // Método que devolverá a lista de registros da view ou um erro.
        try { // Inicia o bloco que captura exceções.
            List<ClienteView> clientes = clienteJdbcRepository.listarClientesView(); // Executa a consulta na view utilizando o repositório.
            return ResponseEntity.ok(clientes); // Retorna HTTP 200 com a lista de registros.
        } catch (DataAccessException e) { // Captura erros de acesso ao banco.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( // Retorna HTTP 500 em caso de falha.
                "success", false, // Indica falha para o frontend.
                "message", "Erro ao consultar view_dados_de_clientes: " + e.getMessage() // Descreve qual operação falhou.
            )); // Fim da resposta de erro.
        } // Fim do bloco try/catch.
    } // Fim do método listarViaView.
} // Fim da classe ClienteController.




