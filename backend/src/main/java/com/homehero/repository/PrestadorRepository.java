package com.homehero.repository; // Pacote das interfaces de acesso a dados.

import com.homehero.model.Prestador; // Entidade manipulada por este repositório.
import org.springframework.data.jpa.repository.JpaRepository; // Interface base com operações CRUD.
import org.springframework.stereotype.Repository; // Indica que é um componente Spring.

import java.util.Optional; // Facilita o tratamento de resultados ausentes.

@Repository // Permite a detecção automática pelo Spring.
public interface PrestadorRepository extends JpaRepository<Prestador, Integer> { // Repositório com operações padrão.
    Optional<Prestador> findByCpf(String cpf); // Busca um prestador pelo CPF.
} // Fim da interface.


