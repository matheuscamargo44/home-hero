package com.homehero.repository.jdbc; // Pacote contendo os repositórios com SQL manual.

import com.homehero.model.procedure.AgendamentoProcedureRequest; // DTO da procedure inserir_agendamento_de_servico.
import com.homehero.model.procedure.CancelarAgendamentoRequest; // DTO da procedure cancelar_agendamento_de_servico.
import com.homehero.model.view.AgendamentoCompletoView; // Record para a view view_agendamentos_completo.
import com.homehero.model.view.HistoricoStatusView; // Record para a view view_historico_status_completo.
import com.homehero.model.view.NotificacaoDetalhadaView; // Record para a view view_notificacoes_detalhadas.
import org.springframework.jdbc.core.JdbcTemplate; // Classe do Spring para executar SQL simples.
import org.springframework.stereotype.Repository; // Marca esta classe como componente do Spring.

import java.sql.Date; // Tipo JDBC usado para converter LocalDate.
import java.sql.ResultSet; // Resultado de consultas SQL.
import java.sql.SQLException; // Exceção lançada em erros de JDBC.
import java.time.LocalDate; // Tipo de data moderna do Java.
import java.util.List; // Estrutura usada para retornar listas de registros.

@Repository // Permite que o Spring injete esta classe automaticamente.
public class AgendamentoJdbcRepository { // Repositório que concentra as chamadas JDBC de agendamentos.

    // View completa de agendamentos filtrada por cliente para evitar sobrecarga.
    private static final String AGENDAMENTO_VIEW_SQL = """
        SELECT
            `ID do agendamento`            AS age_id,
            `ID do cliente`                AS cli_id,
            `ID do serviço`                AS ser_id,
            `ID do prestador`              AS pre_id,
            `Data do agendamento`          AS age_data,
            `Período do agendamento`       AS age_periodo,
            `ID do endereço do agendamento` AS end_id,
            `Status do agendamento`        AS age_status,
            `Valor do agendamento`         AS age_valor,
            `Pagamento confirmado`         AS age_pago,
            `Data de cancelamento`         AS age_cancel,
            `Motivo do cancelamento`       AS age_motivo,
            `Nome do cliente`              AS cli_nome,
            `E-mail do cliente`            AS cli_email,
            `Telefone do cliente`          AS cli_tel,
            `Nome do prestador`            AS pre_nome,
            `E-mail do prestador`          AS pre_email,
            `Telefone do prestador`        AS pre_tel,
            `Nome do serviço`              AS ser_nome,
            `Descrição do serviço`         AS ser_desc,
            `Logradouro do endereço`       AS end_logradouro,
            `Número do endereço`           AS end_numero,
            `Complemento do endereço`      AS end_complemento,
            `Bairro do endereço`           AS end_bairro,
            `Cidade do endereço`           AS end_cidade,
            `UF do endereço`               AS end_uf,
            `CEP do endereço`              AS end_cep
        FROM view_agendamentos_completo
        WHERE `ID do cliente` = ?
        ORDER BY `Data do agendamento` DESC
        """;

    // View dedicada ao histórico de status de um agendamento.
    private static final String HISTORICO_VIEW_SQL = """
        SELECT
            `ID do histórico`     AS his_id,
            `ID do agendamento`   AS age_id,
            `Status anterior`     AS his_ant,
            `Status novo`         AS his_novo,
            `Data da alteração`   AS his_data,
            `ID do cliente`       AS cli_id,
            `Nome do cliente`     AS cli_nome,
            `E-mail do cliente`   AS cli_email,
            `ID do prestador`     AS pre_id,
            `Nome do prestador`   AS pre_nome,
            `E-mail do prestador` AS pre_email,
            `Data do agendamento` AS age_data,
            `Janela de horário`   AS age_janela,
            `Status atual`        AS age_status,
            `Valor do agendamento` AS age_valor,
            `ID do serviço`       AS ser_id,
            `Nome do serviço`     AS ser_nome,
            `Descrição do serviço` AS ser_desc
        FROM view_historico_status_completo
        WHERE `ID do agendamento` = ?
        ORDER BY `Data da alteração` DESC, `ID do histórico` DESC
        """;

    // View com as notificações geradas pelas triggers de agendamento.
    private static final String NOTIFICACOES_VIEW_SQL = """
        SELECT
            `ID da notificação`   AS not_id,
            `Tipo da notificação` AS not_tipo,
            `Mensagem`            AS not_msg,
            `Enviado`             AS not_env,
            `Data da notificação` AS not_data,
            `ID do cliente`       AS cli_id,
            `Nome do cliente`     AS cli_nome,
            `E-mail do cliente`   AS cli_email,
            `Telefone do cliente` AS cli_tel,
            `ID do prestador`     AS pre_id,
            `Nome do prestador`   AS pre_nome,
            `E-mail do prestador` AS pre_email,
            `Telefone do prestador` AS pre_tel,
            `ID do agendamento`   AS age_id,
            `Data do agendamento` AS age_data,
            `Status do agendamento` AS age_status,
            `Valor do agendamento` AS age_valor,
            `Nome do serviço`     AS ser_nome,
            `Descrição do serviço` AS ser_desc
        FROM view_notificacoes_detalhadas
        WHERE `ID do agendamento` = ?
        ORDER BY `Data da notificação` DESC, `ID da notificação` DESC
        """;

    private final JdbcTemplate jdbcTemplate; // Instância reaproveitada para todas as chamadas SQL.

    public AgendamentoJdbcRepository(JdbcTemplate jdbcTemplate) { // Construtor chamado pelo Spring.
        this.jdbcTemplate = jdbcTemplate; // Armazena o JdbcTemplate recebido via injeção.
    } // Fim do construtor.

    public void inserirAgendamento(AgendamentoProcedureRequest request) { // Executa a procedure de criação.
        jdbcTemplate.update( // Dispara a procedure passando todos os parâmetros na ordem oficial.
            "CALL inserir_agendamento_de_servico(?, ?, ?, ?, ?, ?, ?, ?)", // Chamada SQL.
            request.cliId(), // Param 1: ID do cliente.
            request.serId(), // Param 2: ID do serviço.
            request.preId(), // Param 3: ID do prestador.
            Date.valueOf(request.data()), // Param 4: data do agendamento.
            request.periodo(), // Param 5: período.
            request.enderecoId(), // Param 6: endereço.
            request.statusInicial(), // Param 7: status inicial.
            request.valor() // Param 8: valor em moeda.
        ); // Fim da chamada.
    }

    public void cancelarAgendamento(CancelarAgendamentoRequest request) { // Executa a procedure de cancelamento.
        jdbcTemplate.update( // Chama cancelar_agendamento_de_servico com ID e motivo.
            "CALL cancelar_agendamento_de_servico(?, ?)", // Procedimento SQL.
            request.ageId(), // Param 1: ID do agendamento.
            request.motivo() // Param 2: motivo do cancelamento.
        ); // Fim da chamada.
    }

    public List<AgendamentoCompletoView> listarPorCliente(Integer cliId) { // Busca todos os agendamentos do cliente.
        return jdbcTemplate.query( // Executa o SQL da view com o ID como parâmetro.
            AGENDAMENTO_VIEW_SQL,
            (rs, rowNum) -> mapAgendamento(rs),
            cliId
        );
    }

    public List<HistoricoStatusView> historicoPorAgendamento(Integer ageId) { // Busca o histórico de status do agendamento.
        return jdbcTemplate.query(
            HISTORICO_VIEW_SQL,
            (rs, rowNum) -> mapHistorico(rs),
            ageId
        );
    }

    public List<NotificacaoDetalhadaView> notificacoesPorAgendamento(Integer ageId) { // Lista notificações vinculadas.
        return jdbcTemplate.query(
            NOTIFICACOES_VIEW_SQL,
            (rs, rowNum) -> mapNotificacao(rs),
            ageId
        );
    }

    private AgendamentoCompletoView mapAgendamento(ResultSet rs) throws SQLException { // Converte cada linha da view.
        return new AgendamentoCompletoView( // Cria o record com todos os campos.
            rs.getInt("age_id"), // ID do agendamento.
            rs.getInt("cli_id"), // ID do cliente.
            rs.getInt("ser_id"), // ID do serviço.
            rs.getInt("pre_id"), // ID do prestador.
            toLocalDate(rs.getDate("age_data")), // Data do agendamento (null-safe).
            rs.getString("age_periodo"), // Período/Janela.
            rs.getInt("end_id"), // ID do endereço.
            rs.getString("age_status"), // Status atual.
            getDouble(rs, "age_valor"), // Valor (permite NULL).
            getBoolean(rs, "age_pago"), // Flag de pagamento (permite NULL).
            toLocalDate(rs.getDate("age_cancel")), // Data de cancelamento.
            rs.getString("age_motivo"), // Motivo do cancelamento.
            rs.getString("cli_nome"), // Nome do cliente.
            rs.getString("cli_email"), // Email do cliente.
            rs.getString("cli_tel"), // Telefone do cliente.
            rs.getString("pre_nome"), // Nome do prestador.
            rs.getString("pre_email"), // Email do prestador.
            rs.getString("pre_tel"), // Telefone do prestador.
            rs.getString("ser_nome"), // Nome do serviço.
            rs.getString("ser_desc"), // Descrição do serviço.
            rs.getString("end_logradouro"), // Logradouro.
            rs.getString("end_numero"), // Número.
            rs.getString("end_complemento"), // Complemento.
            rs.getString("end_bairro"), // Bairro.
            rs.getString("end_cidade"), // Cidade.
            rs.getString("end_uf"), // Estado.
            rs.getString("end_cep") // CEP.
        ); // Fim da criação do record.
    } // Fim do método mapAgendamento.

    private HistoricoStatusView mapHistorico(ResultSet rs) throws SQLException { // Converte a view de histórico.
        return new HistoricoStatusView(
            rs.getInt("his_id"), // ID do histórico.
            rs.getInt("age_id"), // ID do agendamento.
            rs.getString("his_ant"), // Status anterior.
            rs.getString("his_novo"), // Status novo.
            toLocalDate(rs.getDate("his_data")), // Data da alteração.
            rs.getInt("cli_id"), // ID do cliente.
            rs.getString("cli_nome"), // Nome do cliente.
            rs.getString("cli_email"), // Email do cliente.
            rs.getInt("pre_id"), // ID do prestador.
            rs.getString("pre_nome"), // Nome do prestador.
            rs.getString("pre_email"), // Email do prestador.
            toLocalDate(rs.getDate("age_data")), // Data do agendamento.
            rs.getString("age_janela"), // Janela/Período.
            rs.getString("age_status"), // Status atual.
            getDouble(rs, "age_valor"), // Valor do agendamento.
            rs.getInt("ser_id"), // ID do serviço.
            rs.getString("ser_nome"), // Nome do serviço.
            rs.getString("ser_desc") // Descrição do serviço.
        );
    }

    private NotificacaoDetalhadaView mapNotificacao(ResultSet rs) throws SQLException { // Converte a view de notificações.
        return new NotificacaoDetalhadaView(
            rs.getInt("not_id"), // ID da notificação.
            rs.getString("not_tipo"), // Tipo da notificação.
            rs.getString("not_msg"), // Mensagem.
            getBoolean(rs, "not_env"), // Flag indicando envio.
            toLocalDate(rs.getDate("not_data")), // Data da notificação.
            rs.getInt("cli_id"), // ID do cliente.
            rs.getString("cli_nome"), // Nome do cliente.
            rs.getString("cli_email"), // Email do cliente.
            rs.getString("cli_tel"), // Telefone do cliente.
            rs.getInt("pre_id"), // ID do prestador.
            rs.getString("pre_nome"), // Nome do prestador.
            rs.getString("pre_email"), // Email do prestador.
            rs.getString("pre_tel"), // Telefone do prestador.
            rs.getInt("age_id"), // ID do agendamento.
            toLocalDate(rs.getDate("age_data")), // Data do agendamento.
            rs.getString("age_status"), // Status do agendamento.
            getDouble(rs, "age_valor"), // Valor do agendamento.
            rs.getString("ser_nome"), // Nome do serviço.
            rs.getString("ser_desc") // Descrição do serviço.
        );
    }

    private LocalDate toLocalDate(Date date) { // Converte java.sql.Date para LocalDate lidando com NULL.
        return date == null ? null : date.toLocalDate();
    }

    private Boolean getBoolean(ResultSet rs, String column) throws SQLException { // Lê boolean permitindo NULL.
        boolean value = rs.getBoolean(column); // Obtém o valor bruto.
        return rs.wasNull() ? null : value; // Retorna null se a coluna também era null.
    }

    private Double getDouble(ResultSet rs, String column) throws SQLException { // Lê double permitindo NULL.
        double value = rs.getDouble(column); // Obtém o valor numérico.
        return rs.wasNull() ? null : value; // Retorna null quando a coluna era null.
    }
}

