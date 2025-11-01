package com.homehero.homehero.listener;

import com.homehero.homehero.model.Avaliacao;
import com.homehero.homehero.model.Notificacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PostPersist;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configurable
public class AvaliacaoListener {
    
    private static EntityManager entityManager;
    
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        entityManager = em;
    }
    
    /**
     * Trigger: trigger_pos_inserir_avaliacao_criar_notificacao
     * Cria uma notificação quando uma avaliação é registrada
     */
    @PostPersist
    @Transactional
    public void posInserir(Avaliacao avaliacao) {
        if (entityManager != null) {
            Notificacao notificacao = new Notificacao();
            notificacao.setCliente(avaliacao.getCliente());
            notificacao.setPrestador(avaliacao.getPrestador());
            notificacao.setAgendamento(avaliacao.getAgendamento());
            notificacao.setTipo("Avaliacao");
            notificacao.setMensagem("Nova avaliação registrada.");
            notificacao.setEnviado(false);
            notificacao.setData(LocalDate.now());
            entityManager.persist(notificacao);
        }
    }
}

