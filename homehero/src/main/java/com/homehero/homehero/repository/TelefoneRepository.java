package com.homehero.homehero.repository;

import com.homehero.homehero.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Integer> {
    
    List<Telefone> findByClienteId(Integer clienteId);
    
    List<Telefone> findByPrestadorId(Integer prestadorId);
    
    List<Telefone> findByEmpresaId(Integer empresaId);
}

