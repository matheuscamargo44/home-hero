package com.homehero.controller;

import com.homehero.dao.PrestadorDAO;
import com.homehero.dao.EnderecoDAO;
import com.homehero.model.Prestador;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PrestadorController {
    private PrestadorDAO prestadorDAO = new PrestadorDAO();
    private EnderecoDAO enderecoDAO = new EnderecoDAO();
    private Gson gson = new Gson();

    public String buscarPorCpf(String cpf) {
        Map<String, Object> response = new HashMap<>();
        try {
            String cpfLimpo = cpf.replaceAll("[^0-9]", "");
            Prestador prestador = prestadorDAO.buscarPorCpf(cpfLimpo);
            
            if (prestador != null) {
                response.put("success", true);
                response.put("prestador", prestador);
            } else {
                response.put("success", false);
                response.put("message", "CPF não encontrado");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao buscar prestador: " + e.getMessage());
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
            if (prestadorDAO.buscarPorCpf(cpf) != null) {
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

            Prestador prestador = new Prestador();
            prestador.setNome(dados.get("nomeCompleto").toString());
            prestador.setCpf(cpf);
            prestador.setNascimento(parseDataNascimento(dados.get("dataNascimento")));
            prestador.setAreas(dados.getOrDefault("areasAtuacao", dados.getOrDefault("areas", "")).toString());
            prestador.setExperiencia(dados.getOrDefault("experiencia", "").toString());
            prestador.setCertificados(dados.getOrDefault("certificados", "").toString());
            prestador.setSenha(dados.get("senha").toString());
            prestador.setEnderecoId(enderecoId);
            prestador.setEmail(dados.get("email").toString());
            prestador.setTelefone(dados.get("telefone").toString().replaceAll("[^0-9() -]", ""));

            prestadorDAO.criar(prestador);

            response.put("success", true);
            response.put("message", "Prestador cadastrado com sucesso");
            response.put("prestador", prestador);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao cadastrar prestador: " + e.getMessage());
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

