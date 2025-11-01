package com.homehero.homehero.repository;

import com.homehero.homehero.model.CertificacaoPrestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificacaoPrestadorRepository extends JpaRepository<CertificacaoPrestador, Integer> {
    
    List<CertificacaoPrestador> findByPrestadorId(Integer prestadorId);
}

