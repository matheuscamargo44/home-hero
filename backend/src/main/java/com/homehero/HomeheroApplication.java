package com.homehero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot HomeHero.
 * 
 * O Spring Boot facilita o desenvolvimento Java através de:
 * - Autoconfiguração: detecta automaticamente dependências e configura
 * - Servidor embutido: roda como um simples arquivo JAR
 * - Starter Packs: pacotes prontos para integração com bancos de dados
 */
@SpringBootApplication
public class HomeheroApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeheroApplication.class, args);
    }
}






