package com.homehero.homehero.repository;

import com.homehero.homehero.model.HistoricoStatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoStatusAgendamentoRepository extends JpaRepository<HistoricoStatusAgendamento, Integer> {
    
    List<HistoricoStatusAgendamento> findByAgendamentoId(Integer agendamentoId);
}

