package com.homehero;

import com.homehero.server.SimpleHttpServer;

public class Main {
    public static void main(String[] args) {
        try {
            SimpleHttpServer server = new SimpleHttpServer();
            server.iniciar();
        } catch (Exception e) {
            System.err.println("Erro ao iniciar servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

