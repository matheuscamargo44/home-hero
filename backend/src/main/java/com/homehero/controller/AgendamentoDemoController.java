package com.homehero.controller; // Pacote onde este controlador está localizado.

import com.homehero.model.procedure.AgendamentoProcedureRequest; // DTO com os parâmetros da procedure inserir_agendamento_de_servico.
import com.homehero.model.procedure.CancelarAgendamentoRequest; // DTO com os parâmetros da procedure cancelar_agendamento_de_servico.
import com.homehero.model.view.AgendamentoCompletoView; // Record que representa a view de agendamentos completos.
import com.homehero.model.view.HistoricoStatusView; // Record que representa a view de histórico de status.
import com.homehero.model.view.NotificacaoDetalhadaView; // Record que representa a view de notificações detalhadas.
import com.homehero.repository.jdbc.AgendamentoJdbcRepository; // Repositório JDBC responsável por falar com o banco.
import jakarta.validation.Valid; // Suporte à validação automática do corpo da requisição.
import org.springframework.dao.DataAccessException; // Exceção usada para capturar erros de banco.
import org.springframework.http.HttpStatus; // Enum para status HTTP.
import org.springframework.http.ResponseEntity; // Classe usada para montar respostas HTTP.
import org.springframework.web.bind.annotation.CrossOrigin; // Anotação para habilitar CORS.
import org.springframework.web.bind.annotation.GetMapping; // Indica que o método atende GET.
import org.springframework.web.bind.annotation.PathVariable; // Permite ler parâmetros da URL.
import org.springframework.web.bind.annotation.PostMapping; // Indica que o método atende POST.
import org.springframework.web.bind.annotation.RequestBody; // Diz ao Spring para ler o JSON do corpo da requisição.
import org.springframework.web.bind.annotation.RequestMapping; // Define o prefixo das rotas deste controlador.
import org.springframework.web.bind.annotation.RestController; // Marca a classe como controlador REST.

import java.util.List; // Representa listas em Java.
import java.util.Map; // Estrutura chave-valor usada nas respostas.

@RestController // Habilita o comportamento REST.
@RequestMapping("/api/demo/agendamentos") // Define o prefixo das rotas de demonstração.
@CrossOrigin(origins = "*") // Permite chamadas de qualquer origem.
public class AgendamentoDemoController { // Início da classe do controlador de agendamentos (modo demo).

    private final AgendamentoJdbcRepository agendamentoJdbcRepository; // Campo que armazena o repositório JDBC.

    public AgendamentoDemoController(AgendamentoJdbcRepository agendamentoJdbcRepository) { // Construtor com injeção.
        this.agendamentoJdbcRepository = agendamentoJdbcRepository; // Guarda o repositório recebido.
    } // Fim do construtor.

    @PostMapping("/criar") // Endpoint POST /api/demo/agendamentos/criar.
    public ResponseEntity<Map<String, Object>> criar( // Método responsável por criar agendamentos.
        @Valid @RequestBody AgendamentoProcedureRequest request // Lê e valida os dados enviados pelo cliente.
    ) { // Início do método.
        try { // Bloco protegido contra exceções de banco.
            agendamentoJdbcRepository.inserirAgendamento(request); // Dispara a procedure inserir_agendamento_de_servico.
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of( // Retorna 201 para indicar criação.
                "success", true, // Marca a operação como bem-sucedida.
                "message", "Agendamento criado com sucesso (via procedure)." // Mensagem amigável.
            )); // Fim do corpo da resposta.
        } catch (DataAccessException e) { // Captura falhas de banco.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( // Retorna 500 em caso de erro.
                "success", false, // Indica falha.
                "message", "Erro ao executar inserir_agendamento_de_servico: " + e.getMessage() // Explica o problema.
            )); // Fim do corpo de erro.
        } // Fim do bloco try/catch.
    } // Fim do método criar.

    @PostMapping("/cancelar") // Endpoint POST /api/demo/agendamentos/cancelar.
    public ResponseEntity<Map<String, Object>> cancelar( // Método que aciona a procedure de cancelamento.
        @Valid @RequestBody CancelarAgendamentoRequest request // Lê e valida os dados de cancelamento.
    ) { // Início do método.
        try { // Bloco protegido contra erros do banco.
            agendamentoJdbcRepository.cancelarAgendamento(request); // Dispara a procedure cancelar_agendamento_de_servico.
            return ResponseEntity.ok(Map.of( // Retorna HTTP 200 em caso de sucesso.
                "success", true, // Marca o resultado como positivo.
                "message", "Agendamento cancelado com sucesso (via procedure)." // Mensagem exibida ao usuário.
            )); // Fim do corpo de sucesso.
        } catch (DataAccessException e) { // Captura erros vindos do banco.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( // Retorna 500 se algo falhar.
                "success", false, // Indica ao cliente que houve erro.
                "message", "Erro ao executar cancelar_agendamento_de_servico: " + e.getMessage() // Explica o motivo.
            )); // Fim do corpo de erro.
        } // Fim do bloco try/catch.
    } // Fim do método cancelar.

    @GetMapping("/cliente/{cliId}") // Endpoint GET /api/demo/agendamentos/cliente/{cliId}.
    public ResponseEntity<?> listarPorCliente( // Método que lista agendamentos completos de um cliente.
        @PathVariable Integer cliId // Captura o ID do cliente informado na URL.
    ) { // Início do método.
        try { // Bloco protegido contra erros de banco.
            List<AgendamentoCompletoView> agendamentos = agendamentoJdbcRepository.listarPorCliente(cliId); // Consulta a view view_agendamentos_completo filtrada pelo cliente.
            return ResponseEntity.ok(agendamentos); // Retorna a lista com HTTP 200.
        } catch (DataAccessException e) { // Captura falhas ao consultar a view.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( // Retorna HTTP 500 e uma mensagem clara.
                "success", false, // Indica erro.
                "message", "Erro ao consultar view_agendamentos_completo: " + e.getMessage() // Explica qual operação falhou.
            )); // Fim do corpo de erro.
        } // Fim do bloco try/catch.
    } // Fim do método listarPorCliente.

    @GetMapping("/{ageId}/historico") // Endpoint GET /api/demo/agendamentos/{ageId}/historico.
    public ResponseEntity<?> historico( // Método que retorna o histórico completo de status de um agendamento.
        @PathVariable Integer ageId // Captura o ID do agendamento informado na URL.
    ) { // Início do método.
        try { // Bloco protegido contra erros.
            List<HistoricoStatusView> historico = agendamentoJdbcRepository.historicoPorAgendamento(ageId); // Consulta a view view_historico_status_completo.
            return ResponseEntity.ok(historico); // Retorna os dados com HTTP 200.
        } catch (DataAccessException e) { // Captura erros lançados ao acessar o banco.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( // Retorna mensagem amigável ao usuário.
                "success", false, // Indica falha.
                "message", "Erro ao consultar view_historico_status_completo: " + e.getMessage() // Explica a causa da falha.
            )); // Fim do corpo de erro.
        } // Fim do bloco try/catch.
    } // Fim do método historico.

    @GetMapping("/{ageId}/notificacoes") // Endpoint GET /api/demo/agendamentos/{ageId}/notificacoes.
    public ResponseEntity<?> notificacoes( // Método que retorna as notificações ligadas a um agendamento.
        @PathVariable Integer ageId // Captura o ID do agendamento.
    ) { // Início do método.
        try { // Bloco protegido.
            List<NotificacaoDetalhadaView> notificacoes = agendamentoJdbcRepository.notificacoesPorAgendamento(ageId); // Consulta a view view_notificacoes_detalhadas.
            return ResponseEntity.ok(notificacoes); // Retorna os dados com HTTP 200.
        } catch (DataAccessException e) { // Captura falhas de banco.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( // Retorna um erro detalhado.
                "success", false, // Informa que houve problema.
                "message", "Erro ao consultar view_notificacoes_detalhadas: " + e.getMessage() // Indica qual operação falhou.
            )); // Fim da resposta de erro.
        } // Fim do bloco try/catch.
    } // Fim do método notificacoes.
} // Fim da classe AgendamentoDemoController.

