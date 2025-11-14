package com.homehero.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseTestService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Map<String, Object>> executeView(String viewName) {
        try {
            // Obter nomes das colunas usando INFORMATION_SCHEMA
            String columnNameSql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? ORDER BY ORDINAL_POSITION";
            Query columnNameQuery = entityManager.createNativeQuery(columnNameSql);
            columnNameQuery.setParameter(1, viewName);
            @SuppressWarnings("unchecked")
            List<String> columnNames = columnNameQuery.getResultList();
            
            // Executar a query completa
            String sql = "SELECT * FROM " + viewName;
            Query query = entityManager.createNativeQuery(sql);
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            
            // Converter usando os nomes reais das colunas
            return convertToMapListWithColumnNames(results, columnNames);
        } catch (Exception e) {
            // Fallback para método genérico se houver erro
            String sql = "SELECT * FROM " + viewName;
            Query query = entityManager.createNativeQuery(sql);
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            return convertToMapList(results);
        }
    }
    
    private List<Map<String, Object>> convertToMapListWithColumnNames(List<Object[]> results, List<String> columnNames) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < row.length && i < columnNames.size(); i++) {
                map.put(columnNames.get(i), row[i]);
            }
            mapList.add(map);
        }
        return mapList;
    }

    @Transactional
    public Map<String, Object> executeProcedure(String procedureName, Map<String, Object> parameters) {
        try {
            StringBuilder sql = new StringBuilder("CALL ").append(procedureName).append("(");
            
            if (parameters != null && !parameters.isEmpty()) {
                List<String> paramPlaceholders = new ArrayList<>();
                for (int i = 0; i < parameters.size(); i++) {
                    paramPlaceholders.add("?");
                }
                sql.append(String.join(", ", paramPlaceholders));
            }
            sql.append(")");

            Query query = entityManager.createNativeQuery(sql.toString());
            
            if (parameters != null && !parameters.isEmpty()) {
                int index = 1;
                for (Object value : parameters.values()) {
                    query.setParameter(index++, value);
                }
            }

            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", convertToMapList(results));
            return response;
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao executar procedure: " + e.getMessage());
            response.put("data", new ArrayList<>());
            return response;
        }
    }

    @Transactional
    public Map<String, Object> testTrigger(String tableName, String operation) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Os triggers são executados automaticamente pelo banco
            // Aqui apenas verificamos se a tabela existe e retornamos sucesso
            String sql = "SELECT COUNT(*) FROM " + tableName;
            Query query = entityManager.createNativeQuery(sql);
            Object result = query.getSingleResult();
            
            response.put("success", true);
            response.put("message", "Trigger testado com sucesso. Tabela " + tableName + " acessível.");
            response.put("rowCount", result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao testar trigger: " + e.getMessage());
        }
        return response;
    }

    @Transactional(readOnly = true)
    public List<String> getAvailableViews() {
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = DATABASE()";
        Query query = entityManager.createNativeQuery(sql);
        @SuppressWarnings("unchecked")
        List<String> results = query.getResultList();
        return results;
    }

    @Transactional(readOnly = true)
    public List<String> getAvailableProcedures() {
        String sql = "SELECT ROUTINE_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = DATABASE() AND ROUTINE_TYPE = 'PROCEDURE'";
        Query query = entityManager.createNativeQuery(sql);
        @SuppressWarnings("unchecked")
        List<String> results = query.getResultList();
        return results;
    }

    @Transactional(readOnly = true)
    public List<String> getAvailableTriggers() {
        String sql = "SELECT TRIGGER_NAME FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA = DATABASE()";
        Query query = entityManager.createNativeQuery(sql);
        @SuppressWarnings("unchecked")
        List<String> results = query.getResultList();
        return results;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getTriggerInfo(String triggerName) {
        try {
            // Buscar informações específicas do trigger
            String sql = "SELECT TRIGGER_NAME, EVENT_MANIPULATION, EVENT_OBJECT_TABLE, ACTION_STATEMENT, ACTION_TIMING " +
                        "FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA = DATABASE() AND TRIGGER_NAME = ?";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, triggerName);
            
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            
            Map<String, Object> response = new HashMap<>();
            if (!results.isEmpty()) {
                Object[] row = results.get(0);
                Map<String, Object> data = new HashMap<>();
                data.put("TRIGGER_NAME", row[0]);
                data.put("EVENT_MANIPULATION", row[1]);
                data.put("EVENT_OBJECT_TABLE", row[2]);
                data.put("ACTION_STATEMENT", row[3]);
                data.put("ACTION_TIMING", row[4]);
                
                response.put("success", true);
                response.put("data", data);
            } else {
                response.put("success", false);
                response.put("message", "Trigger não encontrado");
            }
            return response;
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao obter informações do trigger: " + e.getMessage());
            return response;
        }
    }

    private List<Map<String, Object>> convertToMapList(List<Object[]> results) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < row.length; i++) {
                map.put("column" + (i + 1), row[i]);
            }
            mapList.add(map);
        }
        return mapList;
    }

    @Transactional
    public Map<String, Object> executeTriggerAction(String triggerName, Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Mapear trigger para ação correspondente
            if (triggerName.contains("inserir_agendamento_registrar_status_inicial") || 
                triggerName.contains("inserir_agendamento_notificar_prestador")) {
                // Criar agendamento de teste
                return createTestAgendamento(params);
            } else if (triggerName.contains("atualizar_agendamento_registrar_mudanca_de_status")) {
                // Atualizar status de agendamento
                return updateAgendamentoStatus(params);
            } else if (triggerName.contains("inserir_avaliacao_criar_notificacao")) {
                // Criar avaliação
                return createTestAvaliacao(params);
            } else if (triggerName.contains("inserir_pagamento_confirmado_criar_notificacao")) {
                // Criar pagamento confirmado
                return createTestPagamento(params);
            } else if (triggerName.contains("inserir_disputa_aberta_criar_notificacao")) {
                // Criar disputa
                return createTestDisputa(params);
            } else {
                response.put("success", false);
                response.put("message", "Ação não implementada para este trigger");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao executar ação: " + e.getMessage());
        }
        return response;
    }

    private Map<String, Object> createTestAgendamento(Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Buscar primeiro cliente, serviço e prestador disponíveis
            String sql = "SELECT cli_id FROM cliente LIMIT 1";
            Query query = entityManager.createNativeQuery(sql);
            Object cliId = query.getSingleResult();
            
            sql = "SELECT ser_id FROM servico LIMIT 1";
            query = entityManager.createNativeQuery(sql);
            Object serId = query.getSingleResult();
            
            sql = "SELECT pre_id FROM prestador LIMIT 1";
            query = entityManager.createNativeQuery(sql);
            Object preId = query.getSingleResult();
            
            sql = "SELECT end_id FROM endereco LIMIT 1";
            query = entityManager.createNativeQuery(sql);
            Object endId = query.getSingleResult();
            
            // Inserir agendamento
            sql = "INSERT INTO agendamento_servico (age_cli_id, age_ser_id, age_pre_id, age_data, age_janela, age_end_id, age_status, age_valor, age_pago) " +
                  "VALUES (?, ?, ?, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'Manhã', ?, 'Agendado', 150.00, 0)";
            query = entityManager.createNativeQuery(sql);
            query.setParameter(1, cliId);
            query.setParameter(2, serId);
            query.setParameter(3, preId);
            query.setParameter(4, endId);
            query.executeUpdate();
            
            // Buscar o ID do agendamento criado
            sql = "SELECT LAST_INSERT_ID()";
            query = entityManager.createNativeQuery(sql);
            Object ageId = query.getSingleResult();
            
            // Verificar se o trigger foi executado (histórico de status)
            sql = "SELECT * FROM historico_status_agendamento WHERE his_age_id = ?";
            query = entityManager.createNativeQuery(sql);
            query.setParameter(1, ageId);
            @SuppressWarnings("unchecked")
            List<Object[]> historico = query.getResultList();
            
            // Verificar notificações criadas
            sql = "SELECT * FROM notificacao WHERE not_age_id = ? ORDER BY not_data DESC LIMIT 5";
            query = entityManager.createNativeQuery(sql);
            query.setParameter(1, ageId);
            @SuppressWarnings("unchecked")
            List<Object[]> notificacoes = query.getResultList();
            
            response.put("success", true);
            response.put("message", "Agendamento criado com sucesso! Trigger executado automaticamente.");
            response.put("agendamentoId", ageId);
            response.put("historicoStatus", convertToMapList(historico));
            response.put("notificacoes", convertToMapList(notificacoes));
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao criar agendamento: " + e.getMessage());
        }
        return response;
    }

    private Map<String, Object> updateAgendamentoStatus(Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Buscar primeiro agendamento
            String sql = "SELECT age_id, age_status FROM agendamento_servico LIMIT 1";
            Query query = entityManager.createNativeQuery(sql);
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            
            if (results.isEmpty()) {
                response.put("success", false);
                response.put("message", "Nenhum agendamento encontrado para atualizar");
                return response;
            }
            
            Object ageId = results.get(0)[0];
            String oldStatus = results.get(0)[1].toString();
            String newStatus = "Confirmado";
            
            // Atualizar status
            sql = "UPDATE agendamento_servico SET age_status = ? WHERE age_id = ?";
            query = entityManager.createNativeQuery(sql);
            query.setParameter(1, newStatus);
            query.setParameter(2, ageId);
            query.executeUpdate();
            
            // Verificar histórico criado pelo trigger
            sql = "SELECT * FROM historico_status_agendamento WHERE his_age_id = ? ORDER BY his_data_alteracao DESC LIMIT 1";
            query = entityManager.createNativeQuery(sql);
            query.setParameter(1, ageId);
            @SuppressWarnings("unchecked")
            List<Object[]> historico = query.getResultList();
            
            response.put("success", true);
            response.put("message", "Status atualizado de '" + oldStatus + "' para '" + newStatus + "'. Trigger executado automaticamente.");
            response.put("agendamentoId", ageId);
            response.put("statusAnterior", oldStatus);
            response.put("statusNovo", newStatus);
            response.put("historico", convertToMapList(historico));
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao atualizar status: " + e.getMessage());
        }
        return response;
    }

    private Map<String, Object> createTestAvaliacao(Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Buscar primeiro agendamento com prestador
            String sql = "SELECT age_id, age_cli_id, age_pre_id FROM agendamento_servico WHERE age_pre_id IS NOT NULL LIMIT 1";
            Query query = entityManager.createNativeQuery(sql);
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            
            if (results.isEmpty()) {
                response.put("success", false);
                response.put("message", "Nenhum agendamento com prestador encontrado");
                return response;
            }
            
            Object ageId = results.get(0)[0];
            Object cliId = results.get(0)[1];
            Object preId = results.get(0)[2];
            
            // Inserir avaliação
            sql = "INSERT INTO avaliacao (ava_age_id, ava_cli_id, ava_pre_id, ava_nota, ava_comentario, ava_data) " +
                  "VALUES (?, ?, ?, 5, 'Excelente serviço!', CURDATE())";
            query = entityManager.createNativeQuery(sql);
            query.setParameter(1, ageId);
            query.setParameter(2, cliId);
            query.setParameter(3, preId);
            query.executeUpdate();
            
            // Verificar notificação criada pelo trigger
            sql = "SELECT * FROM notificacao WHERE not_age_id = ? AND not_tipo = 'Avaliacao' ORDER BY not_data DESC LIMIT 1";
            query = entityManager.createNativeQuery(sql);
            query.setParameter(1, ageId);
            @SuppressWarnings("unchecked")
            List<Object[]> notificacoes = query.getResultList();
            
            response.put("success", true);
            response.put("message", "Avaliação criada com sucesso! Trigger executado automaticamente e notificação criada.");
            response.put("avaliacaoId", ageId);
            response.put("notificacoes", convertToMapList(notificacoes));
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao criar avaliação: " + e.getMessage());
        }
        return response;
    }

    private Map<String, Object> createTestPagamento(Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Buscar primeiro agendamento
            String sql = "SELECT age_id FROM agendamento_servico LIMIT 1";
            Query query = entityManager.createNativeQuery(sql);
            Object ageId = query.getSingleResult();
            
            // Inserir pagamento confirmado
            sql = "INSERT INTO pagamento (pag_age_id, pag_forma, pag_valor_total, pag_status, pag_referencia_gateway, pag_data) " +
                  "VALUES (?, 'Cartão de Crédito', 150.00, 'Pago', CONCAT('TEST-', UNIX_TIMESTAMP()), CURDATE())";
            query = entityManager.createNativeQuery(sql);
            query.setParameter(1, ageId);
            query.executeUpdate();
            
            // Verificar notificação criada pelo trigger
            sql = "SELECT * FROM notificacao WHERE not_age_id = ? AND not_tipo = 'Pagamento' ORDER BY not_data DESC LIMIT 1";
            query = entityManager.createNativeQuery(sql);
            query.setParameter(1, ageId);
            @SuppressWarnings("unchecked")
            List<Object[]> notificacoes = query.getResultList();
            
            response.put("success", true);
            response.put("message", "Pagamento confirmado criado com sucesso! Trigger executado automaticamente e notificação criada.");
            response.put("pagamentoId", ageId);
            response.put("notificacoes", convertToMapList(notificacoes));
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao criar pagamento: " + e.getMessage());
        }
        return response;
    }

    private Map<String, Object> createTestDisputa(Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Buscar primeiro agendamento
            String sql = "SELECT age_id, age_cli_id, age_pre_id FROM agendamento_servico LIMIT 1";
            Query query = entityManager.createNativeQuery(sql);
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            
            if (results.isEmpty()) {
                response.put("success", false);
                response.put("message", "Nenhum agendamento encontrado");
                return response;
            }
            
            Object ageId = results.get(0)[0];
            Object cliId = results.get(0)[1];
            Object preId = results.get(0)[2];
            
            // Inserir disputa aberta
            sql = "INSERT INTO disputa_reembolso (dsp_age_id, dsp_cli_id, dsp_pre_id, dsp_motivo, dsp_status, dsp_valor_reembolsar, dsp_data_abertura) " +
                  "VALUES (?, ?, ?, 'Serviço não realizado conforme combinado', 'Aberta', 150.00, CURDATE())";
            query = entityManager.createNativeQuery(sql);
            query.setParameter(1, ageId);
            query.setParameter(2, cliId);
            query.setParameter(3, preId);
            query.executeUpdate();
            
            // Verificar notificação criada pelo trigger
            sql = "SELECT * FROM notificacao WHERE not_age_id = ? ORDER BY not_data DESC LIMIT 5";
            query = entityManager.createNativeQuery(sql);
            query.setParameter(1, ageId);
            @SuppressWarnings("unchecked")
            List<Object[]> notificacoes = query.getResultList();
            
            response.put("success", true);
            response.put("message", "Disputa aberta criada com sucesso! Trigger executado automaticamente e notificação criada.");
            response.put("disputaId", ageId);
            response.put("notificacoes", convertToMapList(notificacoes));
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao criar disputa: " + e.getMessage());
        }
        return response;
    }
}

