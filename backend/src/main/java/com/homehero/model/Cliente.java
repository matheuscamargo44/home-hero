package com.homehero.model; // Pacote onde ficam as entidades JPA.

import jakarta.persistence.*; // Importa todas as anotações do JPA utilizadas aqui.
import lombok.AllArgsConstructor; // Lombok gera o construtor com todos os campos.
import lombok.Data; // Lombok gera getters/setters automaticamente.
import lombok.NoArgsConstructor; // Lombok gera o construtor vazio exigido pelo JPA.

import java.time.LocalDate; // Tipo Java usado para representar datas sem horário.

@Entity // Indica que esta classe corresponde a uma tabela do banco.
@Table(name = "cliente") // Especifica que a tabela física se chama cliente.
@Data // Gera os métodos utilitários de forma automática.
@NoArgsConstructor // Construtor padrão requerido pelo Hibernate.
@AllArgsConstructor // Construtor completo útil durante testes/conversões.
public class Cliente { // Início da entidade Cliente.
    @Id // Define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa auto incremento.
    @Column(name = "cli_id") // Mapeia a coluna física.
    private Integer id; // Identificador numérico do cliente.

    @Column(name = "cli_nome", nullable = false, length = 80) // Configura a coluna de nome.
    private String nomeCompleto; // Nome completo do cliente.

    @Column(name = "cli_cpf", nullable = false, unique = true, length = 14) // CPF único com máscara.
    private String cpf; // Documento nacional do cliente.

    @Column(name = "cli_nascimento", nullable = false) // Coluna obrigatória para data de nascimento.
    private LocalDate dataNascimento; // Representa a data em formato ISO.

    @Column(name = "cli_senha", nullable = false, length = 60) // Campo obrigatório para senha.
    private String senha; // Senha armazenada em texto (apenas para fins demonstrativos).

    @Column(name = "end_id", nullable = false) // Chave estrangeira para endereço.
    private Integer enderecoId; // ID da tabela endereco.

    @Column(name = "cli_email", nullable = false, unique = true, length = 120) // Email único por cliente.
    private String email; // E-mail utilizado para contato/logins.

    @Column(name = "cli_telefone", nullable = false, unique = true, length = 20) // Telefone único.
    private String telefone; // Telefone principal do cliente.
} // Fim da classe Cliente.






