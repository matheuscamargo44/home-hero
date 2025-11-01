package com.homehero.homehero.listener;

import com.homehero.homehero.model.AgendamentoServico;
import com.homehero.homehero.model.HistoricoStatusAgendamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configurable
public class AgendamentoServicoListener {
    
    private static EntityManager entityManager;
    
    private static String statusAnterior;
    
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        entityManager = em;
    }
    
    @PreUpdate
    public void preUpdate(AgendamentoServico agendamento) {
        if (entityManager != null) {
            AgendamentoServico agendamentoAtual = entityManager.find(AgendamentoServico.class, agendamento.getId());
            if (agendamentoAtual != null) {
                statusAnterior = agendamentoAtual.getStatus();
            }
        }
    }
    
    /**
     * Trigger: trigger_pos_inserir_agendamento_registrar_status_inicial
     * Registra o status inicial do agendamento ao ser criado
     */
    @PostPersist
    @Transactional
    public void posInserir(AgendamentoServico agendamento) {
        if (entityManager != null) {
            HistoricoStatusAgendamento historico = new HistoricoStatusAgendamento();
            historico.setAgendamento(agendamento);
            historico.setStatusAnterior("Criado");
            historico.setStatusNovo(agendamento.getStatus() != null ? agendamento.getStatus() : "Pendente");
            historico.setDataAlteracao(LocalDate.now());
            entityManager.persist(historico);
        }
    }
    
    /**
     * Trigger: trigger_pos_atualizar_agendamento_registrar_mudanca_de_status
     * Registra mudanças de status do agendamento
     */
    @PostUpdate
    @Transactional
    public void posAtualizar(AgendamentoServico agendamento) {
        if (entityManager != null && statusAnterior != null && 
            !statusAnterior.equals(agendamento.getStatus())) {
            
            HistoricoStatusAgendamento historico = new HistoricoStatusAgendamento();
            historico.setAgendamento(agendamento);
            historico.setStatusAnterior(statusAnterior);
            historico.setStatusNovo(agendamento.getStatus());
            historico.setDataAlteracao(LocalDate.now());
            entityManager.persist(historico);
        }
        statusAnterior = null;
    }
}

