package com.homehero.controller; // Pacote onde o controlador reside.

import com.homehero.model.procedure.PrestadorProcedureRequest; // DTO para enviar parâmetros à procedure inserir_prestador.
import com.homehero.model.view.PrestadorView; // Record que representa uma linha da view_dados_de_prestadores.
import com.homehero.repository.jdbc.PrestadorJdbcRepository; // Repositório JDBC que encapsula as chamadas de banco.
import jakarta.validation.Valid; // Suporte à validação automática dos dados de entrada.
import org.springframework.dao.DataAccessException; // Exceção padrão de acesso a dados.
import org.springframework.http.HttpStatus; // Enum para representar status HTTP.
import org.springframework.http.ResponseEntity; // Tipo usado para montar respostas HTTP completas.
import org.springframework.web.bind.annotation.CrossOrigin; // Permite configurar CORS.
import org.springframework.web.bind.annotation.GetMapping; // Marca métodos que respondem a GET.
import org.springframework.web.bind.annotation.PostMapping; // Marca métodos que respondem a POST.
import org.springframework.web.bind.annotation.RequestBody; // Indica que os dados vêm no corpo da requisição.
import org.springframework.web.bind.annotation.RequestMapping; // Define o prefixo das rotas deste controlador.
import org.springframework.web.bind.annotation.RestController; // Indica que esta classe é um controlador REST.

import java.util.List; // Representa listas em Java.
import java.util.Map; // Estrutura chave-valor utilizada nas respostas simples.

@RestController // Habilita o comportamento REST no Spring.
@RequestMapping("/api/prestadores") // Todos os endpoints começam com /api/prestadores.
@CrossOrigin(origins = "*") // Permite chamadas de qualquer origem (importante para ambientes de demo).
public class PrestadorController { // Início da classe PrestadorController.

    private final PrestadorJdbcRepository prestadorJdbcRepository; // Campo que guarda o repositório JDBC.

    public PrestadorController(PrestadorJdbcRepository prestadorJdbcRepository) { // Construtor usado pelo Spring para injetar a dependência.
        this.prestadorJdbcRepository = prestadorJdbcRepository; // Salva a referência recebida para uso nos métodos.
    } // Fim do construtor.

    @PostMapping("/procedure") // Endpoint POST /api/prestadores/procedure.
    public ResponseEntity<Map<String, Object>> inserirViaProcedure( // Método responsável por acionar inserir_prestador.
        @Valid @RequestBody PrestadorProcedureRequest request // Desserializa e valida o corpo da requisição.
    ) { // Início do método.
        try { // Bloco de código protegido contra falhas de banco.
            prestadorJdbcRepository.inserirPrestador(request); // Chama a procedure inserir_prestador com os dados recebidos.
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of( // Retorna HTTP 201 e um corpo mínimo.
                "success", true, // Informa sucesso ao cliente.
                "message", "Prestador inserido via procedure." // Mensagem amigável sobre o resultado.
            )); // Fim do corpo de sucesso.
        } catch (DataAccessException e) { // Captura erros lançados pelo JDBC.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( // Retorna HTTP 500 para indicar falha.
                "success", false, // Informa ao cliente que houve erro.
                "message", "Erro ao executar inserir_prestador: " + e.getMessage() // Adiciona detalhes para facilitar o diagnóstico.
            )); // Fim da resposta de erro.
        } // Fim do bloco try/catch.
    } // Fim do método inserirViaProcedure.

    @GetMapping("/view") // Endpoint GET /api/prestadores/view.
    public ResponseEntity<?> listarViaView() { // Método que devolve a lista da view ou um erro.
        try { // Bloco protegido contra exceções do banco.
            List<PrestadorView> prestadores = prestadorJdbcRepository.listarPrestadoresView(); // Busca todos os registros da view via JDBC.
            return ResponseEntity.ok(prestadores); // Retorna os dados com status 200.
        } catch (DataAccessException e) { // Captura quaisquer falhas ao consultar o banco.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( // Retorna status 500 com uma mensagem clara.
                "success", false, // Flag indicando falha.
                "message", "Erro ao consultar view_dados_de_prestadores: " + e.getMessage() // Explica qual operação falhou.
            )); // Fim da resposta de erro.
        } // Fim do bloco try/catch.
    } // Fim do método listarViaView.
} // Fim da classe PrestadorController.

