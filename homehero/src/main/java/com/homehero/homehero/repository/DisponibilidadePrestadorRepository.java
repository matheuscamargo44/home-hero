package com.homehero.homehero.repository;

import com.homehero.homehero.model.DisponibilidadePrestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisponibilidadePrestadorRepository extends JpaRepository<DisponibilidadePrestador, Integer> {
    
    List<DisponibilidadePrestador> findByPrestadorId(Integer prestadorId);
    
    List<DisponibilidadePrestador> findByPrestadorIdAndAtivoTrue(Integer prestadorId);
    
    List<DisponibilidadePrestador> findByPrestadorIdAndDiaSemana(Integer prestadorId, String diaSemana);
}

