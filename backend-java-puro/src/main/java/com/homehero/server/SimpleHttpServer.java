package com.homehero.server;

import com.homehero.controller.*;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class SimpleHttpServer {
    private static final int PORT = 8080;
    private AuthController authController = new AuthController();
    private ClienteController clienteController = new ClienteController();
    private PrestadorController prestadorController = new PrestadorController();
    private ServicoController servicoController = new ServicoController();
    private AdminController adminController = new AdminController();
    private com.homehero.controller.DatabaseTestController databaseTestController = new com.homehero.controller.DatabaseTestController();

    public void iniciar() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        server.createContext("/api/auth/login", new Handler((exchange, body) -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                return authController.login(body);
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/clientes/cpf/", new Handler((exchange, body) -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String path = exchange.getRequestURI().getPath();
                String cpf = path.substring(path.lastIndexOf("/") + 1);
                return clienteController.buscarPorCpf(cpf);
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/clientes/cadastro", new Handler((exchange, body) -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                return clienteController.cadastrar(body);
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/prestadores/cpf/", new Handler((exchange, body) -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String path = exchange.getRequestURI().getPath();
                String cpf = path.substring(path.lastIndexOf("/") + 1);
                return prestadorController.buscarPorCpf(cpf);
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/prestadores/cadastro", new Handler((exchange, body) -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                return prestadorController.cadastrar(body);
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/servicos", new Handler((exchange, body) -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                return servicoController.listar();
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/admin/create-default", new Handler((exchange, body) -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                return adminController.criarAdminPadrao();
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/admin/verify", new Handler((exchange, body) -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                String token = exchange.getRequestHeaders().getFirst("Authorization");
                return adminController.verificarToken(token);
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/database-test/views", new Handler((exchange, body) -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                return databaseTestController.getViews();
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/database-test/procedures", new Handler((exchange, body) -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                return databaseTestController.getProcedures();
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/database-test/triggers", new Handler((exchange, body) -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                return databaseTestController.getTriggers();
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/database-test/view/", new Handler((exchange, body) -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                String path = exchange.getRequestURI().getPath();
                String viewName = path.substring(path.lastIndexOf("/") + 1);
                return databaseTestController.testView(viewName);
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/database-test/procedure/", new Handler((exchange, body) -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                String path = exchange.getRequestURI().getPath();
                String procedureName = path.substring(path.lastIndexOf("/") + 1);
                return databaseTestController.testProcedure(procedureName, body);
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.createContext("/api/database-test/trigger/", new Handler((exchange, body) -> {
            String path = exchange.getRequestURI().getPath();
            String triggerName = path.substring(path.lastIndexOf("/") + 1);
            
            if ("GET".equals(exchange.getRequestMethod())) {
                return databaseTestController.getTriggerInfo(triggerName);
            } else if ("POST".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                if (query != null && query.contains("execute")) {
                    return databaseTestController.executeTriggerAction(triggerName, body);
                } else {
                    String operation = "SELECT";
                    if (query != null && query.contains("operation=")) {
                        operation = query.substring(query.indexOf("operation=") + 10);
                        if (operation.contains("&")) {
                            operation = operation.substring(0, operation.indexOf("&"));
                        }
                    }
                    return databaseTestController.testTrigger(triggerName, operation);
                }
            }
            return "{\"success\":false,\"message\":\"Método não permitido\"}";
        }));
        
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor HTTP iniciado na porta " + PORT);
    }

    @FunctionalInterface
    interface RequestHandler {
        String handle(HttpExchange exchange, String body) throws IOException;
    }

    static class Handler implements HttpHandler {
        private RequestHandler handler;

        public Handler(RequestHandler handler) {
            this.handler = handler;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String body = lerBody(exchange.getRequestBody());
            String response = handler.handle(exchange, body);
            
            exchange.getResponseHeaders().add("Content-Type", "application/json;charset=UTF-8");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(200, 0);
                exchange.close();
                return;
            }
            
            byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        }

        private String lerBody(InputStream is) throws IOException {
            StringBuilder body = new StringBuilder();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                body.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
            }
            return body.toString();
        }
    }
}

