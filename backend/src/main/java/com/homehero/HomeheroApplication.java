package com.homehero; // Pacote raiz da aplicação Spring Boot.

import org.springframework.boot.SpringApplication; // Classe utilitária para iniciar o app.
import org.springframework.boot.autoconfigure.SpringBootApplication; // Habilita a configuração automática do Spring.

@SpringBootApplication // Combina @Configuration, @EnableAutoConfiguration e @ComponentScan.
public class HomeheroApplication { // Classe principal gerada pelo Spring Initializr.
    public static void main(String[] args) { // Método inicial da JVM.
        SpringApplication.run(HomeheroApplication.class, args); // Sobe o contexto Spring e o servidor embutido.
    } // Fim do método main.
} // Fim da classe HomeheroApplication.






