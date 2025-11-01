package com.homehero.homehero.repository;

import com.homehero.homehero.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    
    Optional<Empresa> findByCnpj(String cnpj);
    
    boolean existsByCnpj(String cnpj);
}

