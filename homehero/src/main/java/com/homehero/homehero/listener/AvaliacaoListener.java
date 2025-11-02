package com.homehero.homehero.listener;

import com.homehero.homehero.model.Avaliacao;
import com.homehero.homehero.model.Notificacao;
import jakarta.persistence.PostPersist;

import java.time.LocalDate;

public class AvaliacaoListener {
    
    @PostPersist
    public void posInserir(Avaliacao avaliacao) {
        Notificacao notificacao = new Notificacao();
        notificacao.setCliente(avaliacao.getCliente());
        notificacao.setPrestador(avaliacao.getPrestador());
        notificacao.setAgendamento(avaliacao.getAgendamento());
        notificacao.setTipo("Avaliacao");
        notificacao.setMensagem("Nova avaliação registrada.");
        notificacao.setEnviado(false);
        notificacao.setData(LocalDate.now());
        
        if (avaliacao.getPrestador().getNotificacoes() == null) {
            avaliacao.getPrestador().setNotificacoes(new java.util.ArrayList<>());
        }
        avaliacao.getPrestador().getNotificacoes().add(notificacao);
    }
}

