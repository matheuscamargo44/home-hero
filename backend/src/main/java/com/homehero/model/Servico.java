package com.homehero.model; // Pacote das entidades JPA.

import jakarta.persistence.*; // Importa as anotações do JPA.
import lombok.AllArgsConstructor; // Gera construtor com todos os campos.
import lombok.Data; // Gera getters/setters automaticamente.
import lombok.NoArgsConstructor; // Gera construtor vazio obrigatório pelo Hibernate.

@Entity // Indica que a classe é persistida no banco.
@Table(name = "servico") // Define o nome da tabela física.
@Data // Lombok gera métodos utilitários.
@NoArgsConstructor // Construtor padrão.
@AllArgsConstructor // Construtor completo.
public class Servico { // Início da classe Servico.
    @Id // Marca a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa auto incremento.
    @Column(name = "ser_id") // Mapeia a coluna ser_id.
    private Integer id; // Identificador do serviço.

    @Column(name = "ser_nome", nullable = false, length = 80) // Nome obrigatório.
    private String nome; // Nome comercial do serviço.

    @Column(name = "ser_descricao", length = 255) // Descrição opcional.
    private String descricao; // Texto explicando o serviço.

    @Column(name = "ser_preco_base") // Coluna que guarda o preço base.
    private Float precoBase; // Valor sugerido para o serviço.

    @Column(name = "ser_ativo") // Coluna booleana indicativa.
    private Boolean ativo; // Diz se o serviço está disponível.

    @Column(name = "cat_id") // Chave estrangeira para categoria_servico.
    private Integer categoriaId; // ID da categoria associada.
} // Fim da classe Servico.






