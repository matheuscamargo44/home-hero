package com.homehero.controller;

import com.homehero.dao.ClienteDAO;
import com.homehero.dao.EnderecoDAO;
import com.homehero.model.Cliente;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ClienteController {
    private ClienteDAO clienteDAO = new ClienteDAO();
    private EnderecoDAO enderecoDAO = new EnderecoDAO();
    private Gson gson = new Gson();

    public String buscarPorCpf(String cpf) {
        Map<String, Object> response = new HashMap<>();
        try {
            String cpfLimpo = cpf.replaceAll("[^0-9]", "");
            Cliente cliente = clienteDAO.buscarPorCpf(cpfLimpo);
            
            if (cliente != null) {
                response.put("success", true);
                response.put("cliente", cliente);
            } else {
                response.put("success", false);
                response.put("message", "CPF não encontrado");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao buscar cliente: " + e.getMessage());
        }
        return gson.toJson(response);
    }

    public String cadastrar(String body) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> dados = gson.fromJson(body, Map.class);
            
            if (!validarCampos(dados)) {
                response.put("success", false);
                response.put("message", "Campos obrigatórios: nomeCompleto, cpf, email, telefone, senha");
                return gson.toJson(response);
            }

            String cpf = dados.get("cpf").toString().replaceAll("[^0-9]", "");
            if (clienteDAO.buscarPorCpf(cpf) != null) {
                response.put("success", false);
                response.put("message", "CPF já cadastrado");
                return gson.toJson(response);
            }

            Integer enderecoId = enderecoDAO.criar(
                dados.getOrDefault("logradouro", "").toString(),
                dados.getOrDefault("numero", "").toString(),
                dados.getOrDefault("complemento", "").toString(),
                dados.getOrDefault("bairro", "").toString(),
                dados.getOrDefault("cidade", "").toString(),
                dados.getOrDefault("uf", "").toString(),
                dados.getOrDefault("cep", "").toString().replaceAll("[^0-9-]", "")
            );

            Cliente cliente = new Cliente();
            cliente.setNomeCompleto(dados.get("nomeCompleto").toString());
            cliente.setCpf(cpf);
            cliente.setDataNascimento(parseDataNascimento(dados.get("dataNascimento")));
            cliente.setSenha(dados.get("senha").toString());
            cliente.setEnderecoId(enderecoId);
            cliente.setEmail(dados.get("email").toString());
            cliente.setTelefone(dados.get("telefone").toString().replaceAll("[^0-9() -]", ""));

            clienteDAO.criar(cliente);

            response.put("success", true);
            response.put("message", "Cliente cadastrado com sucesso");
            response.put("cliente", cliente);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao cadastrar cliente: " + e.getMessage());
        }
        return gson.toJson(response);
    }

    private boolean validarCampos(Map<String, Object> dados) {
        return dados.get("nomeCompleto") != null && dados.get("cpf") != null &&
               dados.get("email") != null && dados.get("telefone") != null && 
               dados.get("senha") != null;
    }

    private LocalDate parseDataNascimento(Object data) {
        if (data != null && !data.toString().isEmpty()) {
            try {
                return LocalDate.parse(data.toString());
            } catch (Exception e) {
                return LocalDate.of(1990, 1, 1);
            }
        }
        return LocalDate.of(1990, 1, 1);
    }
}

