package com.homehero.repository;

import com.homehero.model.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de banco de dados relacionadas a prestadores
 * Herda métodos CRUD básicos do JpaRepository
 */
@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Integer> {
    /**
     * Busca um prestador pelo CPF
     * @param cpf CPF do prestador (apenas números)
     * @return Optional contendo o prestador se encontrado
     */
    Optional<Prestador> findByCpf(String cpf);
}


