package com.homehero.homehero.repository.custom;

import java.time.LocalDate;
import java.util.List;

public interface AgendamentoServicoRepositoryCustom {
    
    /**
     * Procedure: pesquisar_clientes_por_nome_exato
     * Busca clientes pelo nome exato
     */
    // Será implementado via query nativa no repository
    
    /**
     * Procedure: listar_agendamentos_por_id_de_cliente
     * Lista agendamentos de um cliente específico
     */
    List<Object[]> listarAgendamentosPorCliente(Integer clienteId);
    
    /**
     * Procedure: inserir_agendamento_de_servico_simples
     * Insere um agendamento de serviço
     */
    Integer inserirAgendamentoSimples(
        Integer clienteId,
        Integer servicoId,
        Integer prestadorId,
        Integer empresaId,
        LocalDate data,
        String janela,
        Integer enderecoId,
        String status,
        Float valor
    );
    
    /**
     * Procedure: cancelar_agendamento_de_servico
     * Cancela um agendamento
     */
    void cancelarAgendamento(Integer agendamentoId, String motivo);
    
    /**
     * Procedure: registrar_avaliacao_de_prestador
     * Registra uma avaliação de prestador
     */
    void registrarAvaliacao(
        Integer agendamentoId,
        Integer clienteId,
        Integer prestadorId,
        Integer nota,
        String comentario
    );
}

