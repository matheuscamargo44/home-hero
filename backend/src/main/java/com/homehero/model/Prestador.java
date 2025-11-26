package com.homehero.model; // Pacote das entidades.

import jakarta.persistence.*; // Importa as anotações JPA necessárias.
import lombok.AllArgsConstructor; // Lombok cria o construtor com todos os campos.
import lombok.Data; // Lombok gera métodos de acesso automaticamente.
import lombok.NoArgsConstructor; // Lombok gera o construtor vazio requerido pelo JPA.

import java.time.LocalDate; // Tipo Java para datas sem hora.

@Entity // Marca a classe como entidade JPA.
@Table(name = "prestador") // Tabela física correspondente.
@Data // Lombok gera getters/setters.
@NoArgsConstructor // Construtor vazio padrão.
@AllArgsConstructor // Construtor completo.
public class Prestador { // Início da classe Prestador.
    @Id // Indica a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa auto incremento.
    @Column(name = "pre_id") // Nome da coluna no banco.
    private Integer id; // Identificador do prestador.

    @Column(name = "pre_nome", nullable = false, length = 80) // Coluna obrigatória para nome.
    private String nome; // Nome do prestador.

    @Column(name = "pre_cpf", nullable = false, unique = true, length = 14) // CPF com máscara e unicidade.
    private String cpf; // Documento nacional.

    @Column(name = "pre_nascimento", nullable = false) // Coluna obrigatória para data de nascimento.
    private LocalDate nascimento; // Data de nascimento.

    @Column(name = "pre_areas", length = 120) // Campo opcional para áreas de atuação.
    private String areas; // Descrição resumida das áreas de atuação.

    @Column(name = "pre_experiencia", length = 255) // Campo opcional para experiência.
    private String experiencia; // Texto livre sobre experiência.

    @Column(name = "pre_certificados", length = 255) // Campo opcional para certificados.
    private String certificados; // Lista ou descrição de certificados.

    @Column(name = "pre_senha", nullable = false, length = 60) // Coluna obrigatória com senha.
    private String senha; // Senha em texto simples (apenas para fins demo).

    @Column(name = "end_id", nullable = false) // Chave estrangeira para a tabela endereco.
    private Integer enderecoId; // ID do endereço associado.

    @Column(name = "pre_email", nullable = false, unique = true, length = 120) // Email único.
    private String email; // E-mail de contato/login.

    @Column(name = "pre_telefone", nullable = false, unique = true, length = 20) // Telefone único.
    private String telefone; // Telefone principal.
} // Fim da classe Prestador.


