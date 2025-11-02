package com.homehero.homehero.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class AgendamentoServicoRepositoryCustomImpl implements AgendamentoServicoRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Object[]> listarAgendamentosPorCliente(Integer clienteId) {
        Query query = entityManager.createNativeQuery(
            "CALL listar_agendamentos_por_id_de_cliente(?)"
        );
        query.setParameter(1, clienteId);
        return query.getResultList();
    }
    
    @Override
    public Integer inserirAgendamentoSimples(
        Integer clienteId,
        Integer servicoId,
        Integer prestadorId,
        Integer empresaId,
        LocalDate data,
        String janela,
        Integer enderecoId,
        String status,
        Float valor
    ) {
        Query query = entityManager.createNativeQuery(
            "CALL inserir_agendamento_de_servico_simples(?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        query.setParameter(1, clienteId);
        query.setParameter(2, servicoId);
        query.setParameter(3, prestadorId);
        query.setParameter(4, empresaId);
        query.setParameter(5, data);
        query.setParameter(6, janela);
        query.setParameter(7, enderecoId);
        query.setParameter(8, status);
        query.setParameter(9, valor);
        
        query.executeUpdate();
        
        Query lastIdQuery = entityManager.createNativeQuery(
            "SELECT LAST_INSERT_ID()"
        );
        return ((Number) lastIdQuery.getSingleResult()).intValue();
    }
    
    @Override
    public void cancelarAgendamento(Integer agendamentoId, String motivo) {
        Query query = entityManager.createNativeQuery(
            "CALL cancelar_agendamento_de_servico(?, ?)"
        );
        query.setParameter(1, agendamentoId);
        query.setParameter(2, motivo);
        query.executeUpdate();
    }
    
    @Override
    public void registrarAvaliacao(
        Integer agendamentoId,
        Integer clienteId,
        Integer prestadorId,
        Integer nota,
        String comentario
    ) {
        Query query = entityManager.createNativeQuery(
            "CALL registrar_avaliacao_de_prestador(?, ?, ?, ?, ?)"
        );
        query.setParameter(1, agendamentoId);
        query.setParameter(2, clienteId);
        query.setParameter(3, prestadorId);
        query.setParameter(4, nota);
        query.setParameter(5, comentario);
        query.executeUpdate();
    }
}

