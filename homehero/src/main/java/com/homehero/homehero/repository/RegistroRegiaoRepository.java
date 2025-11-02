package com.homehero.homehero.repository;

import com.homehero.homehero.model.RegistroRegiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroRegiaoRepository extends JpaRepository<RegistroRegiao, Integer> {
    
    List<RegistroRegiao> findByClienteId(Integer clienteId);
    
    List<RegistroRegiao> findByPrestadorId(Integer prestadorId);
    
    List<RegistroRegiao> findByEmpresaId(Integer empresaId);
    
    List<RegistroRegiao> findByRegiaoId(Integer regiaoId);
}

