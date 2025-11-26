package com.homehero.model; // Pacote que agrupa todas as entidades do domínio.

import jakarta.persistence.*; // Importa as anotações JPA necessárias.
import lombok.AllArgsConstructor; // Gera o construtor com todos os argumentos.
import lombok.Data; // Gera getters, setters, equals, hashCode e toString.
import lombok.NoArgsConstructor; // Gera o construtor vazio obrigatório pelo JPA.

@Entity // Informa ao JPA que esta classe está mapeada para uma tabela.
@Table(name = "admin") // Define que a tabela correspondente se chama admin.
@Data // Lombok gera automaticamente os métodos de acesso.
@NoArgsConstructor // Construtor vazio exigido pelo Hibernate.
@AllArgsConstructor // Construtor completo útil em testes/conversões.
public class Admin { // Início da entidade Admin.
    @Id // Identifica o campo chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa auto incremento do banco.
    @Column(name = "adm_id") // Mapeia a coluna física.
    private Integer id; // Armazena o identificador do admin.

    @Column(name = "adm_nome", nullable = false, length = 80) // Define coluna obrigatória para o nome.
    private String nome; // Nome completo do admin.

    @Column(name = "adm_email", nullable = false, unique = true, length = 120) // Coluna única para o e-mail.
    private String email; // E-mail usado para login.

    @Column(name = "adm_senha", nullable = false, length = 60) // Coluna obrigatória para a senha.
    private String senha; // Senha em texto simples (apenas para demonstração).
} // Fim da classe Admin.






