package com.homehero.homehero.repository;

import com.homehero.homehero.model.ChatbotLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatbotLogRepository extends JpaRepository<ChatbotLog, Integer> {
    
    List<ChatbotLog> findByClienteId(Integer clienteId);
    
    List<ChatbotLog> findByPrestadorId(Integer prestadorId);
    
    List<ChatbotLog> findByAgendamentoId(Integer agendamentoId);
}

