package com.homehero.service;

import com.homehero.dao.AdminDAO;
import com.homehero.dao.ClienteDAO;
import com.homehero.dao.PrestadorDAO;
import com.homehero.model.Admin;
import com.homehero.model.Cliente;
import com.homehero.model.Prestador;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private AdminDAO adminDAO = new AdminDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private PrestadorDAO prestadorDAO = new PrestadorDAO();

    public Map<String, Object> login(String identifier, String senha) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (identifier == null || senha == null) {
                response.put("success", false);
                response.put("message", "Email/CPF e senha são obrigatórios");
                return response;
            }

            Admin admin = adminDAO.buscarPorEmail(identifier);
            if (admin != null && admin.getSenha().equals(senha)) {
                response.put("success", true);
                response.put("userType", "admin");
                response.put("admin", admin);
                response.put("token", "admin-token-" + admin.getId());
                return response;
            }

            String cpfLimpo = identifier.replaceAll("[^0-9]", "");
            if (cpfLimpo.length() == 11) {
                Cliente cliente = clienteDAO.buscarPorCpf(cpfLimpo);
                if (cliente != null && cliente.getSenha().equals(senha)) {
                    response.put("success", true);
                    response.put("userType", "cliente");
                    response.put("cliente", cliente);
                    response.put("token", "cliente-token-" + cliente.getId());
                    return response;
                }

                Prestador prestador = prestadorDAO.buscarPorCpf(cpfLimpo);
                if (prestador != null && prestador.getSenha().equals(senha)) {
                    response.put("success", true);
                    response.put("userType", "prestador");
                    response.put("prestador", prestador);
                    response.put("token", "prestador-token-" + prestador.getId());
                    return response;
                }
            }

            response.put("success", false);
            response.put("message", "Email/CPF ou senha incorretos");
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao processar login: " + e.getMessage());
        }
        
        return response;
    }
}

