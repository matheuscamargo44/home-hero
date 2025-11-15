package com.homehero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação HomeHero Backend
 * Inicializa o servidor Spring Boot na porta 8080
 */
@SpringBootApplication
public class HomeheroApplication {
    /**
     * Método principal que inicia a aplicação Spring Boot
     * @param args Argumentos da linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(HomeheroApplication.class, args);
    }
}






