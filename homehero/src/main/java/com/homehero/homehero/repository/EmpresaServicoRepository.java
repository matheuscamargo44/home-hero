package com.homehero.homehero.repository;

import com.homehero.homehero.model.EmpresaServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaServicoRepository extends JpaRepository<EmpresaServico, Integer> {
    
    List<EmpresaServico> findByEmpresaId(Integer empresaId);
    
    List<EmpresaServico> findByServicoId(Integer servicoId);
    
    List<EmpresaServico> findByEmpresaIdAndAtivoTrue(Integer empresaId);
    
    Optional<EmpresaServico> findByEmpresaIdAndServicoId(Integer empresaId, Integer servicoId);
}

