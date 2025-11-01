package com.homehero.homehero.repository;

import com.homehero.homehero.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    
    List<Pagamento> findByAgendamentoId(Integer agendamentoId);
    
    Optional<Pagamento> findByReferenciaGateway(String referenciaGateway);
    
    List<Pagamento> findByStatus(String status);
}

