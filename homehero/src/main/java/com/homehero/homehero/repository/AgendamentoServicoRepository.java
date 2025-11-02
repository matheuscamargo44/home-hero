package com.homehero.homehero.repository;

import com.homehero.homehero.model.AgendamentoServico;
import com.homehero.homehero.repository.custom.AgendamentoServicoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface AgendamentoServicoRepository extends JpaRepository<AgendamentoServico, Integer>, AgendamentoServicoRepositoryCustom {
    
    List<AgendamentoServico> findByClienteId(Integer clienteId);
    
    List<AgendamentoServico> findByPrestadorId(Integer prestadorId);
    
    List<AgendamentoServico> findByEmpresaId(Integer empresaId);
    
    List<AgendamentoServico> findByData(LocalDate data);
    
    List<AgendamentoServico> findByStatus(String status);
    
    @Query("SELECT a FROM AgendamentoServico a WHERE a.cliente.id = :clienteId AND a.status = :status")
    List<AgendamentoServico> findByClienteIdAndStatus(@Param("clienteId") Integer clienteId, @Param("status") String status);
    
    @Query("SELECT a FROM AgendamentoServico a WHERE a.prestador.id = :prestadorId AND a.data = :data AND a.status != 'Cancelado'")
    List<AgendamentoServico> findByPrestadorIdAndData(@Param("prestadorId") Integer prestadorId, @Param("data") LocalDate data);
}

