package com.homehero.homehero.repository;

import com.homehero.homehero.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Integer> {
    
    Optional<Regiao> findByNome(String nome);
    
    List<Regiao> findByCidade(String cidade);
    
    List<Regiao> findByUf(String uf);
}

