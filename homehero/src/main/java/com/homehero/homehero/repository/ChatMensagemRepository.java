package com.homehero.homehero.repository;

import com.homehero.homehero.model.ChatMensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMensagemRepository extends JpaRepository<ChatMensagem, Integer> {
    
    List<ChatMensagem> findByAgendamentoId(Integer agendamentoId);
}

