package com.homehero.repository;

import com.homehero.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de banco de dados relacionadas a serviços
 * Herda métodos CRUD básicos do JpaRepository
 */
@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {
    /**
     * Busca todos os serviços que estão ativos
     * @return Lista de serviços ativos
     */
    List<Servico> findByAtivoTrue();
}






