package com.homehero.controller; // Pacote do controlador de autenticação.

import com.homehero.model.Admin; // Entidade Admin usada no login.
import com.homehero.model.Cliente; // Entidade Cliente usada no login.
import com.homehero.model.Prestador; // Entidade Prestador usada no login.
import com.homehero.repository.AdminRepository; // Repositório de admins.
import com.homehero.repository.ClienteRepository; // Repositório de clientes.
import com.homehero.repository.PrestadorRepository; // Repositório de prestadores.
import org.springframework.http.HttpStatus; // Enum de códigos HTTP.
import org.springframework.http.ResponseEntity; // Classe para criar respostas HTTP.
import org.springframework.web.bind.annotation.*; // Importa anotações REST do Spring.

import java.util.Map; // Estrutura chave-valor para respostas simples.
import java.util.Optional; // Tipo que evita NullPointer ao lidar com resultados ausentes.

@RestController // Indica que esta classe expõe endpoints REST.
@RequestMapping("/api/auth") // Prefixo das rotas de autenticação.
@CrossOrigin(origins = "*") // Libera chamadas de qualquer domínio.
public class AuthController { // Início da classe AuthController.

    private final AdminRepository adminRepository; // Repositório para buscar admins.
    private final ClienteRepository clienteRepository; // Repositório para buscar clientes.
    private final PrestadorRepository prestadorRepository; // Repositório para buscar prestadores.

    public AuthController( // Construtor com injeção das dependências.
        AdminRepository adminRepository, // Recebe o repositório de admins.
        ClienteRepository clienteRepository, // Recebe o repositório de clientes.
        PrestadorRepository prestadorRepository // Recebe o repositório de prestadores.
    ) { // Início do construtor.
        this.adminRepository = adminRepository; // Armazena o repositório de admins.
        this.clienteRepository = clienteRepository; // Armazena o repositório de clientes.
        this.prestadorRepository = prestadorRepository; // Armazena o repositório de prestadores.
    } // Fim do construtor.

    @PostMapping("/login") // Endpoint POST /api/auth/login.
    public ResponseEntity<Map<String, Object>> login( // Método que processa o login.
        @RequestBody Map<String, String> credentials // Corpo JSON contendo identifier e senha.
    ) { // Início do método.
        try { // Protege a lógica contra erros inesperados.
            String identifier = credentials.get("identifier"); // Lê o e-mail/CPF enviado.
            String senha = credentials.get("senha"); // Lê a senha enviada.

            if (identifier == null || senha == null) { // Valida se ambos foram enviados.
                return errorResponse("Email/CPF e senha são obrigatórios", HttpStatus.BAD_REQUEST); // Retorna 400 se faltou algo.
            } // Fim da validação básica.

            Optional<Admin> adminOpt = adminRepository.findByEmail(identifier); // Procura um admin com o e-mail informado.
            if (adminOpt.isPresent() && adminOpt.get().getSenha().equals(senha)) { // Confirma se encontrou e se a senha confere.
                return successLogin("admin", adminOpt.get(), adminOpt.get().getId()); // Retorna token de admin.
            } // Fim da validação para admin.

            String cpfLimpo = identifier.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos do identificador.
            if (cpfLimpo.length() == 11) { // Só prossegue se tiver 11 dígitos (CPF válido).
                Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpfLimpo); // Procura o cliente pelo CPF.
                if (clienteOpt.isPresent() && clienteOpt.get().getSenha().equals(senha)) { // Verifica a senha.
                    return successLogin("cliente", clienteOpt.get(), clienteOpt.get().getId()); // Retorna token de cliente.
                } // Fim do bloco cliente.

                Optional<Prestador> prestadorOpt = prestadorRepository.findByCpf(cpfLimpo); // Procura prestador pelo CPF.
                if (prestadorOpt.isPresent() && prestadorOpt.get().getSenha().equals(senha)) { // Verifica a senha.
                    return successLogin("prestador", prestadorOpt.get(), prestadorOpt.get().getId()); // Retorna token de prestador.
                } // Fim do bloco prestador.
            } // Fim da verificação de CPF.

            return errorResponse("Email/CPF ou senha incorretos", HttpStatus.UNAUTHORIZED); // Se nada deu certo, devolve 401.
        } catch (Exception e) { // Captura qualquer erro inesperado.
            return errorResponse("Erro ao processar login: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // Retorna 500.
        } // Fim do bloco try/catch.
    } // Fim do método login.

    private ResponseEntity<Map<String, Object>> successLogin(String userType, Object user, Integer id) { // Constrói a resposta de sucesso.
        return ResponseEntity.ok(Map.of( // Retorna HTTP 200.
            "success", true, // Indica sucesso.
            "userType", userType, // Informa o tipo do usuário autenticado.
            userType, user, // Inclui o objeto completo (admin, cliente ou prestador).
            "token", userType + "-token-" + id // Gera um token simples apenas para demonstração.
        )); // Fim da resposta.
    } // Fim do método successLogin.

    private ResponseEntity<Map<String, Object>> errorResponse(String message, HttpStatus status) { // Gera respostas de erro padronizadas.
        return ResponseEntity.status(status).body(Map.of( // Usa o status recebido.
            "success", false, // Indica falha.
            "message", message // Mensagem descritiva do erro.
        )); // Fim da resposta de erro.
    } // Fim do método errorResponse.
} // Fim da classe AuthController.

