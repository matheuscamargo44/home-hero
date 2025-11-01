package com.homehero.homehero.repository;

import com.homehero.homehero.model.FuncionarioEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioEmpresaRepository extends JpaRepository<FuncionarioEmpresa, Integer> {
    
    List<FuncionarioEmpresa> findByEmpresaId(Integer empresaId);
    
    List<FuncionarioEmpresa> findByEmpresaIdAndAtivoTrue(Integer empresaId);
}

