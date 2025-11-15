package com.homehero.config;

import com.homehero.model.Admin;
import com.homehero.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração que inicializa o administrador padrão do sistema
 * Executa automaticamente quando a aplicação inicia
 */
@Configuration
public class AdminInitializer {

    /**
     * Cria o administrador padrão se ele ainda não existir
     * Credenciais padrão: admin@homehero.com / admin123
     * 
     * @param adminRepository Repositório de administradores
     * @return CommandLineRunner que executa a inicialização
     */
    @Bean
    public CommandLineRunner initAdmin(AdminRepository adminRepository) {
        return args -> {
            if (!adminRepository.existsByEmail("admin@homehero.com")) {
                Admin admin = new Admin();
                admin.setNome("Administrador");
                admin.setEmail("admin@homehero.com");
                admin.setSenha("admin123");
                adminRepository.save(admin);
                System.out.println("Admin padrão criado: admin@homehero.com / admin123");
            } else {
                System.out.println("Admin padrão já existe.");
            }
        };
    }
}






