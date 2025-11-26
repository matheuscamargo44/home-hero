package com.homehero.controller; // Pacote onde o controlador está localizado.

import com.homehero.model.Admin; // Entidade Admin usada para persistir o usuário padrão.
import com.homehero.repository.AdminRepository; // Repositório Spring Data que fala com a tabela admin.
import org.springframework.http.HttpStatus; // Enum de códigos HTTP.
import org.springframework.http.ResponseEntity; // Wrapper para montar respostas HTTP.
import org.springframework.web.bind.annotation.CrossOrigin; // Anotação que libera CORS.
import org.springframework.web.bind.annotation.PostMapping; // Indica métodos que respondem a POST.
import org.springframework.web.bind.annotation.RequestHeader; // Permite ler cabeçalhos da requisição.
import org.springframework.web.bind.annotation.RequestMapping; // Define o prefixo padrão de rota.
import org.springframework.web.bind.annotation.RestController; // Marca esta classe como controlador REST.

import java.util.Map; // Estrutura chave-valor simples para respostas JSON.

@RestController // Expõe os métodos desta classe como endpoints REST.
@RequestMapping("/api/admin") // Todas as rotas começam com /api/admin.
@CrossOrigin(origins = "*") // Libera chamadas de qualquer domínio (útil para apresentação).
public class AdminController { // Início da classe AdminController.

    private final AdminRepository adminRepository; // Repositório injetado via construtor.

    public AdminController(AdminRepository adminRepository) { // Construtor chamado pelo Spring.
        this.adminRepository = adminRepository; // Guarda o repositório para uso nos métodos.
    } // Fim do construtor.

    @PostMapping("/create-default") // Endpoint POST /api/admin/create-default.
    public ResponseEntity<Map<String, Object>> createDefaultAdmin() { // Método que cria um admin padrão se não existir.
        try { // Bloco que captura erros inesperados.
            if (!adminRepository.existsByEmail("admin@homehero.com")) { // Verifica se já existe um admin com o e-mail fixo.
                Admin admin = new Admin(); // Cria uma nova instância da entidade.
                admin.setNome("Administrador"); // Define o nome padrão.
                admin.setEmail("admin@homehero.com"); // Define o e-mail padrão.
                admin.setSenha("admin123"); // Define a senha simples para demonstração.
                adminRepository.save(admin); // Persiste o usuário no banco.
                return successResponse("Admin padrão criado com sucesso"); // Retorna mensagem de sucesso.
            } // Fim do if.
            return successResponse("Admin já existe"); // Caso o usuário já exista, apenas informa.
        } catch (Exception e) { // Captura qualquer erro inesperado.
            return errorResponse("Erro ao criar admin: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // Retorna HTTP 500 com o erro.
        } // Fim do bloco try/catch.
    } // Fim do método createDefaultAdmin.

    @PostMapping("/verify") // Endpoint POST /api/admin/verify.
    public ResponseEntity<Map<String, Object>> verifyAdmin( // Método que verifica se o token recebido representa um admin.
        @RequestHeader(value = "Authorization", required = false) String token // Lê o header Authorization, se existir.
    ) { // Início do método.
        boolean isAdmin = token != null && token.startsWith("admin-token-"); // Regra simples para saber se o token é válido.
        return ResponseEntity // Monta a resposta HTTP.
            .status(isAdmin ? HttpStatus.OK : HttpStatus.UNAUTHORIZED) // Retorna 200 se for admin ou 401 caso contrário.
            .body(Map.of( // Corpo JSON com duas chaves.
                "success", isAdmin, // success indica o mesmo valor de isAdmin.
                "isAdmin", isAdmin // isAdmin repete o resultado para facilitar a leitura no front.
            )); // Final da resposta.
    } // Fim do método verifyAdmin.

    private ResponseEntity<Map<String, Object>> successResponse(String message) { // Método utilitário para respostas de sucesso.
        return ResponseEntity.ok(Map.of( // Retorna HTTP 200.
            "success", true, // Flag indicando que deu tudo certo.
            "message", message // Mensagem exibida ao usuário.
        )); // Fim do corpo de sucesso.
    } // Fim do método successResponse.

    private ResponseEntity<Map<String, Object>> errorResponse(String message, HttpStatus status) { // Método utilitário para respostas de erro.
        return ResponseEntity.status(status).body(Map.of( // Define o status informado.
            "success", false, // Flag indicando falha.
            "message", message // Mensagem explicando o problema.
        )); // Fim do corpo de erro.
    } // Fim do método errorResponse.
} // Fim da classe AdminController.






