package com.homehero.homehero.repository;

import com.homehero.homehero.model.Cliente;
import com.homehero.homehero.repository.custom.ClienteRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>, ClienteRepositoryCustom {
    
    Optional<Cliente> findByCpf(String cpf);
    
    boolean existsByCpf(String cpf);
}

