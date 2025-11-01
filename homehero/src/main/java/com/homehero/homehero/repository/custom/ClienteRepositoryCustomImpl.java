package com.homehero.homehero.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClienteRepositoryCustomImpl implements ClienteRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Object[]> pesquisarClientesPorNomeExato(String nomeCompleto) {
        Query query = entityManager.createNativeQuery(
            "CALL pesquisar_clientes_por_nome_exato(?)"
        );
        query.setParameter(1, nomeCompleto);
        return query.getResultList();
    }
}

