package com.homehero.repository; // Pacote onde ficam os repositórios.

import com.homehero.model.Cliente; // Entidade manipulada por este repositório.
import org.springframework.data.jpa.repository.JpaRepository; // Interface base do Spring Data.
import org.springframework.stereotype.Repository; // Marca a interface como componente Spring.

import java.util.Optional; // Usado para retornar resultado opcional.

@Repository // Permite que o Spring injete esta interface onde for necessário.
public interface ClienteRepository extends JpaRepository<Cliente, Integer> { // Repositório com operações padrão.
    Optional<Cliente> findByCpf(String cpf); // Busca cliente pelo CPF normalizado.
} // Fim da interface.






