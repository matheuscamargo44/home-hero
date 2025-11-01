package com.homehero.homehero.repository.custom;

import java.util.List;

public interface ClienteRepositoryCustom {
    
    /**
     * Procedure: pesquisar_clientes_por_nome_exato
     * Busca clientes pelo nome exato
     */
    List<Object[]> pesquisarClientesPorNomeExato(String nomeCompleto);
}

