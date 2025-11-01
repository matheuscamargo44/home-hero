package com.homehero.homehero.repository;

import com.homehero.homehero.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {
    
    List<Notificacao> findByClienteId(Integer clienteId);
    
    List<Notificacao> findByPrestadorId(Integer prestadorId);
    
    List<Notificacao> findByEnviadoFalse();
    
    List<Notificacao> findByTipo(String tipo);
}

