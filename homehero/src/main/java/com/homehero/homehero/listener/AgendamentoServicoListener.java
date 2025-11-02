package com.homehero.homehero.listener;

import com.homehero.homehero.model.AgendamentoServico;
import com.homehero.homehero.model.HistoricoStatusAgendamento;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreUpdate;

import java.time.LocalDate;

public class AgendamentoServicoListener {
    
    private static final ThreadLocal<String> statusAnterior = new ThreadLocal<>();
    
    @PreUpdate
    public void preUpdate(AgendamentoServico agendamento) {
        statusAnterior.set(agendamento.getStatus());
    }
    
    @PostPersist
    public void posInserir(AgendamentoServico agendamento) {
        HistoricoStatusAgendamento historico = new HistoricoStatusAgendamento();
        historico.setAgendamento(agendamento);
        historico.setStatusAnterior("Criado");
        historico.setStatusNovo(agendamento.getStatus() != null ? agendamento.getStatus() : "Pendente");
        historico.setDataAlteracao(LocalDate.now());
        
        if (agendamento.getHistoricosStatus() == null) {
            agendamento.setHistoricosStatus(new java.util.ArrayList<>());
        }
        agendamento.getHistoricosStatus().add(historico);
    }
    
    @PostUpdate
    public void posAtualizar(AgendamentoServico agendamento) {
        String anterior = statusAnterior.get();
        if (anterior != null && !anterior.equals(agendamento.getStatus())) {
            HistoricoStatusAgendamento historico = new HistoricoStatusAgendamento();
            historico.setAgendamento(agendamento);
            historico.setStatusAnterior(anterior);
            historico.setStatusNovo(agendamento.getStatus());
            historico.setDataAlteracao(LocalDate.now());
            
            if (agendamento.getHistoricosStatus() == null) {
                agendamento.setHistoricosStatus(new java.util.ArrayList<>());
            }
            agendamento.getHistoricosStatus().add(historico);
        }
        statusAnterior.remove();
    }
}

