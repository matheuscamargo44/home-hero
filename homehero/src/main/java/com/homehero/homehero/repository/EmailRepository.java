package com.homehero.homehero.repository;

import com.homehero.homehero.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
    
    List<Email> findByClienteId(Integer clienteId);
    
    List<Email> findByPrestadorId(Integer prestadorId);
    
    List<Email> findByEmpresaId(Integer empresaId);
}

