package com.homehero.homehero.repository;

import com.homehero.homehero.model.DisputaReembolso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisputaReembolsoRepository extends JpaRepository<DisputaReembolso, Integer> {
    
    List<DisputaReembolso> findByAgendamentoId(Integer agendamentoId);
    
    List<DisputaReembolso> findByClienteId(Integer clienteId);
    
    List<DisputaReembolso> findByPrestadorId(Integer prestadorId);
    
    List<DisputaReembolso> findByStatus(String status);
}

