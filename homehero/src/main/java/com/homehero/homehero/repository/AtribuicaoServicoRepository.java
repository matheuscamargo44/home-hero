package com.homehero.homehero.repository;

import com.homehero.homehero.model.AtribuicaoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtribuicaoServicoRepository extends JpaRepository<AtribuicaoServico, Integer> {
    
    List<AtribuicaoServico> findByAgendamentoId(Integer agendamentoId);
    
    List<AtribuicaoServico> findByFuncionarioId(Integer funcionarioId);
}

