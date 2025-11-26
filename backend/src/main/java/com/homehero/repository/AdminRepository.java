package com.homehero.repository; // Pacote que agrupa os repositórios Spring Data.

import com.homehero.model.Admin; // Entidade que este repositório manipula.
import org.springframework.data.jpa.repository.JpaRepository; // Interface base do Spring Data.
import org.springframework.stereotype.Repository; // Marca a interface como componente Spring.

import java.util.Optional; // Tipo que encapsula valores que podem estar ausentes.

@Repository // Habilita detecção automática pelo Spring.
public interface AdminRepository extends JpaRepository<Admin, Integer> { // Repositório com operações CRUD básicas.
    Optional<Admin> findByEmail(String email); // Consulta um admin pelo e-mail.
    boolean existsByEmail(String email); // Verifica se já existe um admin com o e-mail informado.
} // Fim da interface.






