package com.homehero.homehero.repository;

import com.homehero.homehero.model.CategoriaServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaServicoRepository extends JpaRepository<CategoriaServico, Integer> {
    
    Optional<CategoriaServico> findByNome(String nome);
    
    boolean existsByNome(String nome);
}

