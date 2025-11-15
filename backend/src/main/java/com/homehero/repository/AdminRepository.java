package com.homehero.repository;

import com.homehero.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de banco de dados relacionadas a administradores
 * Herda métodos CRUD básicos do JpaRepository
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    /**
     * Busca um administrador pelo email
     * @param email Email do administrador
     * @return Optional contendo o admin se encontrado
     */
    Optional<Admin> findByEmail(String email);
    
    /**
     * Verifica se existe um administrador com o email informado
     * @param email Email a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);
}






