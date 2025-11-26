package com.homehero.repository; // Pacote com os repositórios Spring Data.

import com.homehero.model.Servico; // Entidade Servico manipulada por este repositório.
import org.springframework.data.jpa.repository.JpaRepository; // Interface base do Spring Data.
import org.springframework.stereotype.Repository; // Marca a interface como componente Spring.

import java.util.List; // Usado para retornar listas de serviços.

@Repository // Habilita a detecção automática pelo Spring.
public interface ServicoRepository extends JpaRepository<Servico, Integer> { // Repositório com operações de CRUD padrão.
    List<Servico> findByAtivoTrue(); // Consulta todos os serviços que estão marcados como ativos.
} // Fim da interface.






