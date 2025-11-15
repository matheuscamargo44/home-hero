package com.homehero.repository;

import com.homehero.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de banco de dados relacionadas a clientes
 * Herda métodos CRUD básicos do JpaRepository
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    /**
     * Busca um cliente pelo CPF
     * @param cpf CPF do cliente (apenas números)
     * @return Optional contendo o cliente se encontrado
     */
    Optional<Cliente> findByCpf(String cpf);
}






