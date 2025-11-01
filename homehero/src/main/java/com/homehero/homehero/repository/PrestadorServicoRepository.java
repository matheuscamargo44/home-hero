package com.homehero.homehero.repository;

import com.homehero.homehero.model.PrestadorServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrestadorServicoRepository extends JpaRepository<PrestadorServico, Integer> {
    
    List<PrestadorServico> findByPrestadorId(Integer prestadorId);
    
    List<PrestadorServico> findByServicoId(Integer servicoId);
    
    List<PrestadorServico> findByPrestadorIdAndAtivoTrue(Integer prestadorId);
    
    Optional<PrestadorServico> findByPrestadorIdAndServicoId(Integer prestadorId, Integer servicoId);
}

