package com.homehero.repository;

import com.homehero.model.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Integer> {
    Optional<Prestador> findByCpf(String cpf);
}


