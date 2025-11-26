package com.homehero.repository.jdbc; // Pacote dedicado aos repositórios JDBC.

import com.homehero.model.procedure.ClienteProcedureRequest; // DTO com os parâmetros da procedure inserir_cliente.
import com.homehero.model.view.ClienteView; // Record que representa uma linha da view_dados_de_clientes.
import org.springframework.jdbc.core.JdbcTemplate; // Classe utilitária do Spring para executar SQL simples.
import org.springframework.stereotype.Repository; // Marca esta classe como componente do Spring.

import java.sql.Date; // Usado para converter LocalDate em um tipo aceito pelo JDBC.
import java.util.List; // Lista usada para retornar os registros da view.

@Repository // Permite que o Spring injete esta classe onde necessário.
public class ClienteJdbcRepository { // Início da classe responsável por operações JDBC de clientes.

    private static final String CLIENTE_VIEW_SQL = // SQL que lê a view com alias amigáveis.
        """
        SELECT
            `ID do cliente`       AS cli_id,
            `Nome do cliente`     AS cli_nome,
            `CPF do cliente`      AS cli_cpf,
            `Data de nascimento`  AS cli_nascimento,
            `Senha`               AS cli_senha,
            `ID do endereço`      AS end_id,
            `E-mail do cliente`   AS cli_email,
            `Telefone do cliente` AS cli_tel,
            `Logradouro`          AS end_logradouro,
            `Número`              AS end_numero,
            `Complemento`         AS end_complemento,
            `Bairro`              AS end_bairro,
            `Cidade`              AS end_cidade,
            `UF`                  AS end_uf,
            `CEP`                 AS end_cep
        FROM view_dados_de_clientes
        ORDER BY `Nome do cliente`
        """; // Fim do texto SQL multilinha.

    private final JdbcTemplate jdbcTemplate; // Instância reutilizada para executar SQLs.

    public ClienteJdbcRepository(JdbcTemplate jdbcTemplate) { // Construtor chamado pelo Spring.
        this.jdbcTemplate = jdbcTemplate; // Guarda a referência do JdbcTemplate.
    } // Fim do construtor.

    public void inserirCliente(ClienteProcedureRequest request) { // Executa a procedure inserir_cliente.
        jdbcTemplate.update( // Chama a procedure passando todos os parâmetros na ordem correta.
            "CALL inserir_cliente(?, ?, ?, ?, ?, ?, ?)", // Chamada à procedure do MySQL.
            request.nome().trim(), // Nome sem espaços extras.
            limparNumero(request.cpf()), // CPF somente com dígitos.
            Date.valueOf(request.nascimento()), // Data convertida para java.sql.Date.
            request.senha(), // Senha recebida.
            request.enderecoId(), // ID do endereço existente.
            request.email().trim(), // Email sem espaços.
            limparTelefone(request.telefone()) // Telefone apenas com dígitos.
        ); // Fim da chamada ao banco.
    } // Fim do método inserirCliente.

    public List<ClienteView> listarClientesView() { // Consulta a view de clientes e retorna records.
        return jdbcTemplate.query( // Executa a consulta usando o JdbcTemplate.
            CLIENTE_VIEW_SQL, // SQL definido acima.
            (rs, rowNum) -> new ClienteView( // Converte cada linha do ResultSet em um record ClienteView.
                rs.getInt("cli_id"), // ID do cliente.
                rs.getString("cli_nome"), // Nome do cliente.
                rs.getString("cli_cpf"), // CPF.
                rs.getDate("cli_nascimento").toLocalDate(), // Data de nascimento convertida.
                rs.getString("cli_senha"), // Senha.
                rs.getInt("end_id"), // ID do endereço.
                rs.getString("cli_email"), // Email.
                rs.getString("cli_tel"), // Telefone.
                rs.getString("end_logradouro"), // Logradouro.
                rs.getString("end_numero"), // Número.
                rs.getString("end_complemento"), // Complemento.
                rs.getString("end_bairro"), // Bairro.
                rs.getString("end_cidade"), // Cidade.
                rs.getString("end_uf"), // Estado.
                rs.getString("end_cep") // CEP.
            ) // Fim do lambda que cria ClienteView.
        ); // Fim da execução da query.
    } // Fim do método listarClientesView.

    private String limparNumero(String valor) { // Remove tudo o que não for dígito.
        return valor == null ? null : valor.replaceAll("[^0-9]", ""); // Retorna apenas números.
    } // Fim do método limparNumero.

    private String limparTelefone(String valor) { // Remove caracteres não numéricos do telefone.
        return valor == null ? null : valor.replaceAll("[^0-9+]", ""); // Mantém dígitos e sinal de +.
    } // Fim do método limparTelefone.
} // Fim da classe ClienteJdbcRepository.

