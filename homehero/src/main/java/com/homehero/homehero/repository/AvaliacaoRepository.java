package com.homehero.homehero.repository;

import com.homehero.homehero.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    
    List<Avaliacao> findByPrestadorId(Integer prestadorId);
    
    List<Avaliacao> findByClienteId(Integer clienteId);
    
    List<Avaliacao> findByAgendamentoId(Integer agendamentoId);
    
    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.prestador.id = :prestadorId")
    Double calcularMediaAvaliacaoPorPrestador(@Param("prestadorId") Integer prestadorId);
    
    @Query("SELECT COUNT(a) FROM Avaliacao a WHERE a.prestador.id = :prestadorId")
    Long contarAvaliacoesPorPrestador(@Param("prestadorId") Integer prestadorId);
}

