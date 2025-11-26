package com.homehero.repository.jdbc; // Pacote das integrações JDBC.

import com.homehero.model.procedure.PrestadorProcedureRequest; // DTO com os parâmetros de inserir_prestador.
import com.homehero.model.view.PrestadorView; // Record que representa a view de prestadores.
import org.springframework.jdbc.core.JdbcTemplate; // Classe do Spring para executar SQL.
import org.springframework.stereotype.Repository; // Marca o bean como repositório.

import java.sql.Date; // Usado para converter LocalDate para o JDBC.
import java.util.List; // Estrutura usada para retornar vários registros.

@Repository // Permite a detecção automática pelo Spring.
public class PrestadorJdbcRepository { // Classe responsável pelas operações JDBC de prestadores.

    private static final String PRESTADOR_VIEW_SQL = // SQL que consulta a view completa.
        """
        SELECT
            `ID do prestador`      AS pre_id,
            `Nome do prestador`    AS pre_nome,
            `CPF do prestador`     AS pre_cpf,
            `Data de nascimento`   AS pre_nascimento,
            `Áreas de atuação`     AS pre_areas,
            `Experiência`          AS pre_experiencia,
            `Certificados`         AS pre_certificados,
            `Senha`                AS pre_senha,
            `ID do endereço`       AS end_id,
            `E-mail do prestador`  AS pre_email,
            `Telefone do prestador` AS pre_tel,
            `Logradouro`           AS end_logradouro,
            `Número`               AS end_numero,
            `Complemento`          AS end_complemento,
            `Bairro`               AS end_bairro,
            `Cidade`               AS end_cidade,
            `UF`                   AS end_uf,
            `CEP`                  AS end_cep
        FROM view_dados_de_prestadores
        ORDER BY `Nome do prestador`
        """; // Fim da string SQL.

    private final JdbcTemplate jdbcTemplate; // Dependência usada para executar as queries.

    public PrestadorJdbcRepository(JdbcTemplate jdbcTemplate) { // Construtor injetado pelo Spring.
        this.jdbcTemplate = jdbcTemplate; // Guarda a referência do JdbcTemplate.
    } // Fim do construtor.

    public void inserirPrestador(PrestadorProcedureRequest request) { // Executa a procedure inserir_prestador.
        jdbcTemplate.update( // Chama a procedure passando os 10 parâmetros esperados.
            "CALL inserir_prestador(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", // Chamada à procedure.
            request.nome().trim(), // Nome do prestador sem espaços extras.
            limparNumero(request.cpf()), // CPF contendo somente dígitos.
            Date.valueOf(request.nascimento()), // Data convertida para java.sql.Date.
            request.areas(), // Áreas de atuação.
            request.experiencia(), // Experiência.
            request.certificados(), // Certificados.
            request.senha(), // Senha.
            request.enderecoId(), // ID do endereço.
            request.email().trim(), // Email sem espaços.
            limparTelefone(request.telefone()) // Telefone apenas com números.
        ); // Fim da chamada ao banco.
    } // Fim do método inserirPrestador.

    public List<PrestadorView> listarPrestadoresView() { // Consulta a view e devolve uma lista de records.
        return jdbcTemplate.query( // Executa a query usando o JdbcTemplate.
            PRESTADOR_VIEW_SQL, // SQL definido acima.
            (rs, rowNum) -> new PrestadorView( // Converte cada linha em um PrestadorView.
                rs.getInt("pre_id"), // ID do prestador.
                rs.getString("pre_nome"), // Nome.
                rs.getString("pre_cpf"), // CPF.
                rs.getDate("pre_nascimento").toLocalDate(), // Data de nascimento.
                rs.getString("pre_areas"), // Áreas.
                rs.getString("pre_experiencia"), // Experiência.
                rs.getString("pre_certificados"), // Certificados.
                rs.getString("pre_senha"), // Senha.
                rs.getInt("end_id"), // ID do endereço.
                rs.getString("pre_email"), // Email.
                rs.getString("pre_tel"), // Telefone.
                rs.getString("end_logradouro"), // Logradouro.
                rs.getString("end_numero"), // Número.
                rs.getString("end_complemento"), // Complemento.
                rs.getString("end_bairro"), // Bairro.
                rs.getString("end_cidade"), // Cidade.
                rs.getString("end_uf"), // Estado.
                rs.getString("end_cep") // CEP.
            ) // Fim do lambda.
        ); // Fim da execução da query.
    } // Fim do método listarPrestadoresView.

    private String limparNumero(String valor) { // Remove caracteres não numéricos do CPF.
        return valor == null ? null : valor.replaceAll("[^0-9]", ""); // Mantém apenas dígitos.
    } // Fim do método limparNumero.

    private String limparTelefone(String valor) { // Remove tudo que não for dígito ou + do telefone.
        return valor == null ? null : valor.replaceAll("[^0-9+]", ""); // Retorna apenas os caracteres válidos.
    } // Fim do método limparTelefone.
} // Fim da classe PrestadorJdbcRepository.

