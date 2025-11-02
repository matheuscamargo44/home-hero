# 🎓 APRESENTAÇÃO: MAPEAMENTO DE BANCO DE DADOS COM SPRING BOOT
## Projeto HomeHero - FATEC ADS 4º Semestre

---

## 📋 SUMÁRIO

1. [O que é Spring Boot?](#1-o-que-é-spring-boot)
2. [Arquitetura do Projeto](#2-arquitetura-do-projeto)
3. [Conexão com Banco de Dados](#3-conexão-com-banco-de-dados)
4. [Mapeamento: Tabela → Classe Java](#4-mapeamento-tabela--classe-java)
5. [Anotações JPA Explicadas](#5-anotações-jpa-explicadas)
6. [Relacionamentos entre Tabelas](#6-relacionamentos-entre-tabelas)
7. [Repositories - Busca Automática](#7-repositories---busca-automática)
8. [Stored Procedures no Java](#8-stored-procedures-no-java)
9. [Triggers - Eventos Automáticos](#9-triggers---eventos-automáticos)
10. [Demonstração Prática](#10-demonstração-prática)
11. [Resumo Visual](#11-resumo-visual)

---

## 1. O QUE É SPRING BOOT?

### 🎯 Conceito Simplificado

**Spring Boot** é um framework Java que:
- ✅ **Simplifica** o desenvolvimento
- ✅ **Automatiza** configurações complexas
- ✅ **Facilita** a conexão com bancos de dados
- ✅ **Acelera** o desenvolvimento de APIs

### 📊 Analogia para Iniciantes

**SEM Spring Boot:**
```
Você precisa:
❌ Configurar conexão com banco manualmente
❌ Escrever SQL para cada operação
❌ Gerenciar transações manualmente
❌ Tratar exceções complexas
```

**COM Spring Boot:**
```
Spring Boot faz automaticamente:
✅ Conecta ao banco de dados
✅ Cria SQL automaticamente
✅ Gerencia transações
✅ Trata erros comuns
```

**É como ter um assistente que faz o trabalho pesado para você!**

---

## 2. ARQUITETURA DO PROJETO

### 📁 Estrutura Visual

```
homehero/
│
├── 📄 pom.xml                          ← Configuração e bibliotecas
│
├── 📁 src/main/
│   ├── 📁 resources/
│   │   └── 📄 application.properties   ← Configuração do banco (URL, usuário, senha)
│   │
│   └── 📁 java/com/homehero/homehero/
│       │
│       ├── 🚀 HomeheroApplication.java    ← Classe principal (inicia a aplicação)
│       │
│       ├── 📁 model/                       ← ENTIDADES (23 classes = 23 tabelas do banco)
│       │   ├── Cliente.java                ← Representa tabela "cliente"
│       │   ├── Prestador.java              ← Representa tabela "prestador"
│       │   ├── Empresa.java                ← Representa tabela "empresa"
│       │   ├── Endereco.java               ← Representa tabela "endereco"
│       │   └── ... (19 outras entidades)
│       │
│       ├── 📁 repository/                  ← REPOSITORIES (interfaces para buscar dados)
│       │   ├── ClienteRepository.java      ← Métodos para buscar clientes
│       │   ├── PrestadorRepository.java    ← Métodos para buscar prestadores
│       │   ├── custom/                     ← PROCEDURES (SQL personalizado)
│       │   │   └── ClienteRepositoryCustomImpl.java
│       │   └── ... (21 outros repositories)
│       │
│       └── 📁 listener/                    ← TRIGGERS (eventos automáticos)
│           ├── AgendamentoServicoListener.java
│           └── AvaliacaoListener.java
```

### 🎯 O que cada pasta faz?

| Pasta | Função | Exemplo |
|-------|--------|---------|
| **model/** | Classes que representam tabelas | `Cliente.java` = tabela `cliente` |
| **repository/** | Interfaces para buscar/salvar dados | `ClienteRepository.findByCpf()` |
| **listener/** | Código que executa automaticamente | Ao criar agendamento, cria histórico |
| **resources/** | Configurações (banco de dados, etc) | URL do MySQL, usuário, senha |

---

## 3. CONEXÃO COM BANCO DE DADOS

### 📄 Arquivo: `application.properties`

```properties
# Configuração do Banco de Dados
spring.datasource.url=jdbc:mysql://localhost:3306/HomeHero
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuração JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 🔍 Explicação Detalhada

#### **1. URL de Conexão**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/HomeHero
```

**Traduzindo:**
- `jdbc:mysql://` = "Quero me conectar ao MySQL"
- `localhost:3306` = "No meu computador, na porta 3306"
- `HomeHero` = "Banco de dados chamado HomeHero"

**É como um endereço:** "Av. MySQL, número 3306, banco HomeHero"

#### **2. Credenciais**
```properties
spring.datasource.username=root    ← Usuário do MySQL
spring.datasource.password=        ← Senha do MySQL (vazia no exemplo)
```

#### **3. Driver JDBC**
```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
**O que é?** É a "ponte" entre Java e MySQL. Sem isso, Java não consegue falar com MySQL.

#### **4. Configuração JPA**
```properties
spring.jpa.hibernate.ddl-auto=update
```
**O que faz?** 
- `update` = Se você mudar a classe Java, o banco é atualizado automaticamente
- ⚠️ **Cuidado:** Em produção, use `validate` ou `none`

```properties
spring.jpa.show-sql=true
```
**O que faz?** Mostra no console o SQL que o Hibernate está executando (útil para aprender!)

---

### 📦 Dependências no `pom.xml`

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

**O que são?**
- **`spring-boot-starter-data-jpa`** = Bibliotecas para usar JPA/Hibernate
- **`mysql-connector-j`** = Driver para conectar ao MySQL

**É como instalar plugins:** Sem essas dependências, o projeto não consegue falar com MySQL.

---

## 4. MAPEAMENTO: TABELA → CLASSE JAVA

### 🎯 Conceito Fundamental

**No banco SQL você escreve:**
```sql
CREATE TABLE cliente (
  cli_id INT PRIMARY KEY AUTO_INCREMENT,
  cli_nome_completo VARCHAR(80) NOT NULL,
  cli_cpf VARCHAR(14),
  cli_data_nascimento DATE,
  cli_forma_pagamento_preferida VARCHAR(20),
  cli_senha VARCHAR(60),
  cli_endereco_id INT,
  FOREIGN KEY (cli_endereco_id) REFERENCES endereco(end_id)
);
```

**No Java, isso vira uma classe:**

```java
@Entity                                    // ← "Esta classe é uma tabela do banco"
@Table(name = "cliente")                  // ← "O nome da tabela é 'cliente'"
@Data                                      // ← Lombok: cria getters/setters automaticamente
@NoArgsConstructor                         // ← Lombok: cria construtor vazio
@AllArgsConstructor                        // ← Lombok: cria construtor com todos os campos
public class Cliente {
    
    @Id                                    // ← "Este campo é a chave primária"
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ← "Banco gera ID automaticamente"
    @Column(name = "cli_id")               // ← "Este campo mapeia para coluna 'cli_id'"
    private Integer id;
    
    @Column(name = "cli_nome_completo", nullable = false)  // ← "Não pode ser nulo"
    private String nomeCompleto;
    
    @Column(name = "cli_cpf", length = 14, unique = true)   // ← "Único no banco"
    private String cpf;
    
    @Column(name = "cli_data_nascimento")
    private LocalDate dataNascimento;
    
    // ... mais campos
}
```

### 📊 Comparação Visual

| Banco de Dados (SQL) | Classe Java | Anotação |
|---------------------|-------------|----------|
| `CREATE TABLE cliente` | `@Entity` + `@Table(name = "cliente")` | Define que é uma tabela |
| `cli_id INT PRIMARY KEY AUTO_INCREMENT` | `@Id` + `@GeneratedValue` | Chave primária auto-incremento |
| `cli_nome_completo VARCHAR(80) NOT NULL` | `@Column(name = "...", nullable = false)` | Campo obrigatório |
| `cli_cpf VARCHAR(14) UNIQUE` | `@Column(..., unique = true)` | Campo único |
| `cli_endereco_id INT` + `FOREIGN KEY` | `@ManyToOne` + `@JoinColumn` | Relacionamento com outra tabela |

---

### 🔍 Exemplo Prático: `Endereco.java`

```java
@Entity
@Table(name = "endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id")
    private Integer id;
    
    @Column(name = "end_logradouro")
    private String logradouro;
    
    @Column(name = "end_numero")
    private String numero;
    
    @Column(name = "end_bairro")
    private String bairro;
    
    @Column(name = "end_cidade")
    private String cidade;
    
    @Column(name = "end_uf")
    private String uf;
    
    @Column(name = "end_cep")
    private String cep;
}
```

**O que cada parte faz?**

1. **`@Entity`** = Diz ao Spring: "Esta classe representa uma tabela do banco"
2. **`@Table(name = "endereco")`** = Diz qual tabela do banco usar
3. **`@Id`** = Marca o campo como chave primária
4. **`@GeneratedValue`** = Diz que o banco gera o ID automaticamente
5. **`@Column(name = "end_id")`** = Mapeia para a coluna `end_id` do banco

---

## 5. ANOTAÇÕES JPA EXPLICADAS

### 📌 Anotações Principais

| Anotação | O que faz | Exemplo |
|----------|-----------|---------|
| `@Entity` | Define que a classe é uma entidade (tabela) | `@Entity` acima da classe |
| `@Table(name = "...")` | Define o nome da tabela no banco | `@Table(name = "cliente")` |
| `@Id` | Marca o campo como chave primária | `@Id private Integer id;` |
| `@GeneratedValue` | ID gerado automaticamente pelo banco | `@GeneratedValue(strategy = IDENTITY)` |
| `@Column(name = "...")` | Nome da coluna no banco de dados | `@Column(name = "cli_nome")` |
| `@ManyToOne` | Relacionamento: Muitos → Um | Muitos clientes têm 1 endereço |
| `@OneToMany` | Relacionamento: Um → Muitos | 1 cliente tem muitos emails |
| `@JoinColumn` | Define a coluna de chave estrangeira | `@JoinColumn(name = "cli_endereco_id")` |

### 🔍 Detalhamento das Anotações

#### **1. @Entity**
```java
@Entity
public class Cliente {
    // ...
}
```
**O que faz?** Informa ao Spring: "Esta classe Java representa uma tabela do banco de dados"

**Sem isso:** O Spring não sabe que `Cliente` é uma tabela.

---

#### **2. @Table**
```java
@Table(name = "cliente")
public class Cliente {
    // ...
}
```
**O que faz?** Define qual tabela do banco usar.

**Por que usar?** 
- No Java: `Cliente` (maiúscula)
- No banco: `cliente` (minúscula)
- O Spring precisa saber fazer a tradução!

---

#### **3. @Id e @GeneratedValue**
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "cli_id")
private Integer id;
```

**O que faz?**
- **`@Id`** = "Este campo é a chave primária"
- **`@GeneratedValue`** = "O banco gera o valor automaticamente (AUTO_INCREMENT)"
- **`GenerationType.IDENTITY`** = Usa o AUTO_INCREMENT do MySQL

**Resultado:** Quando você criar um cliente sem ID, o banco automaticamente gera o próximo número.

---

#### **4. @Column**
```java
@Column(name = "cli_nome_completo", nullable = false, length = 80)
private String nomeCompleto;
```

**Parâmetros:**
- **`name`** = Nome da coluna no banco
- **`nullable = false`** = Campo obrigatório (NOT NULL)
- **`length = 80`** = Tamanho máximo (VARCHAR(80))
- **`unique = true`** = Valor único no banco (UNIQUE)

---

#### **5. @ManyToOne (Relacionamento N:1)**
```java
@ManyToOne
@JoinColumn(name = "cli_endereco_id")
private Endereco endereco;
```

**O que significa?**
- **Muitos Clientes** podem ter o **mesmo Endereço**
- A chave estrangeira `cli_endereco_id` fica na tabela `cliente`

**Exemplo:**
```
Cliente 1 → Endereco "Rua A, 100"
Cliente 2 → Endereco "Rua A, 100"  ← Mesmo endereço!
Cliente 3 → Endereco "Rua B, 200"
```

---

#### **6. @OneToMany (Relacionamento 1:N)**
```java
@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<Email> emails;
```

**O que significa?**
- **1 Cliente** pode ter **Muitos Emails**
- **`mappedBy = "cliente"`** = A chave estrangeira está na tabela `email` (campo `ema_cli_id`)
- **`cascade = ALL`** = Se deletar o Cliente, deleta os Emails também
- **`fetch = LAZY`** = Só carrega os emails quando você pedir (economiza memória)

**Exemplo:**
```
Cliente "João Silva"
  ├── Email: joao@gmail.com
  ├── Email: joao@hotmail.com
  └── Email: joao@yahoo.com
```

---

## 6. RELACIONAMENTOS ENTRE TABELAS

### 🔗 Tipos de Relacionamentos

#### **1. Relacionamento 1:1 (Um para Um)**
- Raro no nosso projeto
- Exemplo: 1 Cliente tem 1 CPF único

#### **2. Relacionamento 1:N (Um para Muitos)** ⭐ MAIS COMUM

**Exemplos no HomeHero:**
- 1 Cliente tem Muitos Emails
- 1 Cliente tem Muitos Telefones
- 1 Cliente tem Muitos Agendamentos
- 1 Prestador tem Muitos Certificações

**Implementação:**
```java
// No Cliente.java
@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
private List<Email> emails;

// No Email.java
@ManyToOne
@JoinColumn(name = "ema_cli_id")
private Cliente cliente;
```

**Diagrama:**
```
Cliente (1)
    │
    └── Email (N)
         ├── Email 1 (ema_cli_id = 1)
         ├── Email 2 (ema_cli_id = 1)
         └── Email 3 (ema_cli_id = 1)
```

---

#### **3. Relacionamento N:N (Muitos para Muitos)**

**Exemplo no HomeHero:**
- Muitos Prestadores oferecem Muitos Serviços
- Muitos Clientes se registram em Muitas Regiões

**Como funciona?**
Usa uma tabela intermediária (`prestador_servico`):

```java
// Prestador.java
@OneToMany(mappedBy = "prestador")
private List<PrestadorServico> prestadoresServicos;

// Servico.java
@OneToMany(mappedBy = "servico")
private List<PrestadorServico> prestadoresServicos;

// PrestadorServico.java (tabela intermediária)
@ManyToOne
@JoinColumn(name = "prs_pre_id")
private Prestador prestador;

@ManyToOne
@JoinColumn(name = "prs_ser_id")
private Servico servico;
```

**Diagrama:**
```
Prestador 1 ──┐
              ├── PrestadorServico ──→ Servico 1
Prestador 2 ──┤                      └── Servico 2
              └── PrestadorServico ──→ Servico 3
Prestador 3 ──┘
```

---

### 📊 Relacionamentos no Projeto HomeHero

| Entidade | Relacionamento | Com |
|----------|---------------|-----|
| `Cliente` | 1:N | `Email`, `Telefone`, `Agendamento`, `Avaliacao` |
| `Prestador` | 1:N | `Email`, `Telefone`, `Certificacao`, `Agendamento` |
| `AgendamentoServico` | N:1 | `Cliente`, `Prestador`, `Servico`, `Endereco` |
| `Prestador` | N:N | `Servico` (via `PrestadorServico`) |

---

## 7. REPOSITORIES - BUSCA AUTOMÁTICA

### 🎯 O que é um Repository?

**Repository = Interface que faz consultas no banco automaticamente**

Você escreve métodos em português, e o Spring cria o SQL automaticamente!

### 📝 Exemplo: `ClienteRepository.java`

```java
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    Optional<Cliente> findByCpf(String cpf);
    
    boolean existsByCpf(String cpf);
}
```

### 🔍 Como Funciona?

#### **1. Herança de `JpaRepository`**

```java
extends JpaRepository<Cliente, Integer>
```

**O que você ganha automaticamente:**
- `save(Cliente)` → `INSERT INTO cliente ...`
- `findById(Integer)` → `SELECT * FROM cliente WHERE cli_id = ?`
- `findAll()` → `SELECT * FROM cliente`
- `delete(Cliente)` → `DELETE FROM cliente WHERE cli_id = ?`
- `deleteById(Integer)` → `DELETE FROM cliente WHERE cli_id = ?`
- `count()` → `SELECT COUNT(*) FROM cliente`

**Sem escrever SQL!** 🎉

---

#### **2. Métodos Customizados (Query Methods)**

**Regra do Spring Data JPA:**
```
findBy + NomeDoCampo + (Operadores) + (Parâmetros)
```

**Exemplos:**

| Método Java | SQL Gerado |
|-------------|------------|
| `findByCpf(String cpf)` | `SELECT * FROM cliente WHERE cli_cpf = ?` |
| `findByNomeCompleto(String nome)` | `SELECT * FROM cliente WHERE cli_nome_completo = ?` |
| `existsByCpf(String cpf)` | `SELECT COUNT(*) > 0 FROM cliente WHERE cli_cpf = ?` |
| `findByNomeCompletoAndCpf(String nome, String cpf)` | `SELECT * FROM cliente WHERE cli_nome_completo = ? AND cli_cpf = ?` |

**Mais exemplos:**

```java
// Encontrar por nome (case-insensitive)
Optional<Cliente> findByNomeCompletoIgnoreCase(String nome);

// Encontrar todos que contém um texto
List<Cliente> findByNomeCompletoContaining(String texto);

// Encontrar por data de nascimento maior que
List<Cliente> findByDataNascimentoAfter(LocalDate data);

// Encontrar ordenado
List<Cliente> findByOrderByNomeCompletoAsc();

// Contar quantos existem
long countByCpfNotNull();
```

**Operadores disponíveis:**
- `And` = E
- `Or` = OU
- `Between` = Entre dois valores
- `LessThan` = Menor que
- `GreaterThan` = Maior que
- `IsNull` = É nulo
- `IsNotNull` = Não é nulo
- `Like` = Contém texto
- `Containing` = Contém (case-sensitive)
- `IgnoreCase` = Ignora maiúsculas/minúsculas

---

### 💻 Exemplo Prático de Uso

```java
@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public void exemploUso() {
        // 1. Criar um novo cliente
        Cliente novoCliente = new Cliente();
        novoCliente.setNomeCompleto("João Silva");
        novoCliente.setCpf("123.456.789-00");
        clienteRepository.save(novoCliente);
        // SQL gerado: INSERT INTO cliente (cli_nome_completo, cli_cpf) VALUES (?, ?)
        
        // 2. Buscar cliente por CPF
        Optional<Cliente> cliente = clienteRepository.findByCpf("123.456.789-00");
        // SQL gerado: SELECT * FROM cliente WHERE cli_cpf = ?
        
        // 3. Verificar se CPF existe
        boolean existe = clienteRepository.existsByCpf("123.456.789-00");
        // SQL gerado: SELECT COUNT(*) > 0 FROM cliente WHERE cli_cpf = ?
        
        // 4. Buscar todos os clientes
        List<Cliente> todos = clienteRepository.findAll();
        // SQL gerado: SELECT * FROM cliente
        
        // 5. Deletar um cliente
        clienteRepository.delete(novoCliente);
        // SQL gerado: DELETE FROM cliente WHERE cli_id = ?
    }
}
```

---

## 8. STORED PROCEDURES NO JAVA

### 🎯 O que são Stored Procedures?

**Stored Procedure** = Funções SQL que ficam armazenadas no banco de dados.

**No banco você tem:**
```sql
CREATE PROCEDURE pesquisar_clientes_por_nome_exato(IN nome_completo_exato VARCHAR(80))
BEGIN
  SELECT cliente.cli_id, cliente.cli_nome_completo, cliente.cli_cpf
  FROM cliente
  WHERE cliente.cli_nome_completo = nome_completo_exato;
END
```

### 💻 Como Chamar no Java?

#### **Passo 1: Criar Interface Custom**

```java
public interface ClienteRepositoryCustom {
    List<Object[]> pesquisarClientesPorNomeExato(String nomeCompleto);
}
```

#### **Passo 2: Implementar a Interface**

```java
@Repository
public class ClienteRepositoryCustomImpl implements ClienteRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Object[]> pesquisarClientesPorNomeExato(String nomeCompleto) {
        Query query = entityManager.createNativeQuery(
            "CALL pesquisar_clientes_por_nome_exato(?)"
        );
        query.setParameter(1, nomeCompleto);
        return query.getResultList();
    }
}
```

**Explicação:**
- **`EntityManager`** = Objeto que gerencia conexões com o banco
- **`createNativeQuery`** = Executa SQL direto (sem tradução do JPA)
- **`CALL`** = Comando para executar stored procedures
- **`?`** = Parâmetro (será substituído pelo valor)
- **`setParameter(1, nomeCompleto)`** = Define o 1º parâmetro como `nomeCompleto`

#### **Passo 3: Usar no Repository Principal**

```java
public interface ClienteRepository extends JpaRepository<Cliente, Integer>, 
                                          ClienteRepositoryCustom {
    // Métodos automáticos já disponíveis
}
```

---

### 📊 Outros Exemplos de Procedures no Projeto

#### **Procedure: Inserir Agendamento**
```sql
CREATE PROCEDURE inserir_agendamento_de_servico_simples(...)
```

**No Java:**
```java
Integer id = repository.inserirAgendamentoSimples(
    clienteId, servicoId, prestadorId, empresaId,
    data, janela, enderecoId, status, valor
);
```

#### **Procedure: Cancelar Agendamento**
```sql
CREATE PROCEDURE cancelar_agendamento_de_servico(...)
```

**No Java:**
```java
repository.cancelarAgendamento(agendamentoId, motivo);
```

---

## 9. TRIGGERS - EVENTOS AUTOMÁTICOS

### 🎯 O que são Triggers?

**Trigger** = Código que executa automaticamente quando algo acontece no banco.

**No banco você tem:**
```sql
CREATE TRIGGER trigger_pos_inserir_agendamento_registrar_status_inicial
AFTER INSERT ON agendamento_servico
FOR EACH ROW
BEGIN
  INSERT INTO historico_status_agendamento
    (his_age_id, his_status_anterior, his_status_novo, his_data_alteracao)
  VALUES
    (NEW.age_id, 'Criado', NEW.age_status, CURDATE());
END
```

### 💻 Como Implementar no Java?

#### **Usando JPA Entity Listeners**

```java
// 1. Criar o Listener (Trigger)
public class AgendamentoServicoListener {
    
    @PostPersist  // ← Executa DEPOIS de inserir
    public void posInserir(AgendamentoServico agendamento) {
        HistoricoStatusAgendamento historico = new HistoricoStatusAgendamento();
        historico.setAgendamento(agendamento);
        historico.setStatusAnterior("Criado");
        historico.setStatusNovo(agendamento.getStatus());
        historico.setDataAlteracao(LocalDate.now());
        
        // Adiciona ao histórico do agendamento
        if (agendamento.getHistoricosStatus() == null) {
            agendamento.setHistoricosStatus(new ArrayList<>());
        }
        agendamento.getHistoricosStatus().add(historico);
    }
    
    @PostUpdate  // ← Executa DEPOIS de atualizar
    public void posAtualizar(AgendamentoServico agendamento) {
        // Registra mudança de status
        if (statusAnterior != null && !statusAnterior.equals(agendamento.getStatus())) {
            // Cria novo registro no histórico
        }
    }
}
```

#### **2. Associar o Listener à Entidade**

```java
@Entity
@Table(name = "agendamento_servico")
@EntityListeners(AgendamentoServicoListener.class)  // ← Liga o listener
public class AgendamentoServico {
    // ...
}
```

### 📅 Anotações de Evento (Quando Executar)

| Anotação | Quando Executa | Exemplo |
|----------|----------------|---------|
| `@PrePersist` | ANTES de inserir | Validar dados antes de salvar |
| `@PostPersist` | DEPOIS de inserir | Criar histórico ao criar agendamento |
| `@PreUpdate` | ANTES de atualizar | Guardar valor antigo |
| `@PostUpdate` | DEPOIS de atualizar | Registrar mudança de status |
| `@PreRemove` | ANTES de deletar | Verificar se pode deletar |
| `@PostRemove` | DEPOIS de deletar | Limpar dados relacionados |

---

### 🎯 Exemplo Prático: Trigger de Avaliação

**No banco:**
```sql
CREATE TRIGGER trigger_pos_inserir_avaliacao_criar_notificacao
AFTER INSERT ON avaliacao
FOR EACH ROW
BEGIN
  INSERT INTO notificacao
    (not_cli_id, not_pre_id, not_age_id, not_tipo, not_mensagem, not_enviado, not_data)
  VALUES
    (NEW.ava_cli_id, NEW.ava_pre_id, NEW.ava_age_id, 'Avaliacao', 'Nova avaliacao registrada.', 0, CURDATE());
END
```

**No Java:**
```java
public class AvaliacaoListener {
    
    @PostPersist
    public void posInserir(Avaliacao avaliacao) {
        Notificacao notificacao = new Notificacao();
        notificacao.setCliente(avaliacao.getCliente());
        notificacao.setPrestador(avaliacao.getPrestador());
        notificacao.setAgendamento(avaliacao.getAgendamento());
        notificacao.setTipo("Avaliacao");
        notificacao.setMensagem("Nova avaliação registrada.");
        notificacao.setEnviado(false);
        notificacao.setData(LocalDate.now());
        
        // Adiciona à lista de notificações do prestador
        avaliacao.getPrestador().getNotificacoes().add(notificacao);
    }
}
```

**O que acontece?**
1. Alguém cria uma avaliação
2. Automaticamente é criada uma notificação
3. O prestador recebe a notificação

**Tudo automático!** ✅

---

## 10. DEMONSTRAÇÃO PRÁTICA

### 🎬 Script para Apresentação ao Vivo

#### **Demonstração 1: Criar um Cliente**

```java
// 1. Criar objeto Java
Cliente cliente = new Cliente();
cliente.setNomeCompleto("João Silva");
cliente.setCpf("123.456.789-00");
cliente.setDataNascimento(LocalDate.of(1990, 5, 15));

// 2. Salvar no banco
clienteRepository.save(cliente);

// 3. O Spring executa automaticamente:
// SQL: INSERT INTO cliente (cli_nome_completo, cli_cpf, cli_data_nascimento) 
//      VALUES ('João Silva', '123.456.789-00', '1990-05-15')
```

**Com `show-sql=true`, o console mostrará:**
```
Hibernate: INSERT INTO cliente (cli_nome_completo, cli_cpf, cli_data_nascimento, cli_senha, cli_forma_pagamento_preferida, cli_endereco_id) 
           VALUES (?, ?, ?, ?, ?, ?)
Hibernate: binding parameter [1] as [VARCHAR] - [João Silva]
Hibernate: binding parameter [2] as [VARCHAR] - [123.456.789-00]
...
```

---

#### **Demonstração 2: Buscar Cliente por CPF**

```java
// Buscar cliente por CPF
Optional<Cliente> cliente = clienteRepository.findByCpf("123.456.789-00");

if (cliente.isPresent()) {
    System.out.println("Cliente encontrado: " + cliente.get().getNomeCompleto());
} else {
    System.out.println("Cliente não encontrado");
}

// SQL gerado automaticamente:
// SELECT * FROM cliente WHERE cli_cpf = '123.456.789-00'
```

---

#### **Demonstração 3: Criar Relacionamento (Cliente + Email)**

```java
// 1. Buscar cliente
Cliente cliente = clienteRepository.findByCpf("123.456.789-00").orElse(null);

// 2. Criar email
Email email = new Email();
email.setEnderecoEmail("joao@gmail.com");
email.setCliente(cliente);  // ← Define o relacionamento

// 3. Salvar email
emailRepository.save(email);

// SQL gerado:
// INSERT INTO email (ema_endereco_email, ema_cli_id) 
// VALUES ('joao@gmail.com', 1)
```

---

#### **Demonstração 4: Usar Procedure**

```java
// Chamar stored procedure
List<Object[]> resultados = clienteRepository.pesquisarClientesPorNomeExato("João Silva");

for (Object[] row : resultados) {
    Integer id = (Integer) row[0];
    String nome = (String) row[1];
    String cpf = (String) row[2];
    System.out.println("ID: " + id + ", Nome: " + nome + ", CPF: " + cpf);
}

// SQL executado:
// CALL pesquisar_clientes_por_nome_exato('João Silva')
```

---

#### **Demonstração 5: Ver Trigger em Ação**

```java
// 1. Criar agendamento
AgendamentoServico agendamento = new AgendamentoServico();
agendamento.setCliente(cliente);
agendamento.setServico(servico);
agendamento.setStatus("Pendente");
agendamentoRepository.save(agendamento);

// 2. Automaticamente o Trigger executa!
// O Listener @PostPersist cria um registro no histórico

// 3. Verificar histórico
List<HistoricoStatusAgendamento> historicos = 
    historicoRepository.findByAgendamentoId(agendamento.getId());

System.out.println("Históricos criados: " + historicos.size());
// Resultado: 1 (criado automaticamente pelo trigger!)
```

---

## 11. RESUMO VISUAL

### 📊 Mapeamento Completo

```
┌─────────────────────────────────────────────────────────┐
│                  BANCO DE DADOS (MySQL)                 │
│                                                          │
│  CREATE TABLE cliente (                                 │
│    cli_id INT PRIMARY KEY,                              │
│    cli_nome_completo VARCHAR(80),                       │
│    cli_cpf VARCHAR(14)                                   │
│  );                                                     │
└─────────────────────────────────────────────────────────┘
                        ↕ (JPA/Hibernate traduz)
┌─────────────────────────────────────────────────────────┐
│                    CLASSE JAVA                          │
│                                                          │
│  @Entity                                                │
│  @Table(name = "cliente")                              │
│  public class Cliente {                                  │
│    @Id                                                  │
│    @Column(name = "cli_id")                            │
│    private Integer id;                                   │
│                                                          │
│    @Column(name = "cli_nome_completo")                 │
│    private String nomeCompleto;                         │
│  }                                                       │
└─────────────────────────────────────────────────────────┘
                        ↕ (Repository abstrai)
┌─────────────────────────────────────────────────────────┐
│                   REPOSITORY                            │
│                                                          │
│  interface ClienteRepository {                          │
│    Cliente findByCpf(String cpf);                       │
│    // SQL: SELECT * FROM cliente WHERE cli_cpf = ?      │
│  }                                                       │
└─────────────────────────────────────────────────────────┘
```

---

### 🔄 Fluxo Completo: Criar um Cliente

```
1. VOCÊ ESCREVE JAVA:
   Cliente cliente = new Cliente();
   cliente.setNomeCompleto("João");
   repository.save(cliente);

2. SPRING BOOT/HIBERNATE TRADUZ PARA SQL:
   INSERT INTO cliente (cli_nome_completo) VALUES ('João');

3. MYSQL EXECUTA O SQL:
   ✓ Dados salvos no banco

4. RESULTADO VOLTA PARA JAVA:
   cliente.getId() → retorna o ID gerado pelo banco
```

---

### 📚 Glossário de Termos

| Termo | Significado |
|-------|-------------|
| **JPA** | Java Persistence API - Especificação para mapear objetos Java para tabelas |
| **Hibernate** | Implementação do JPA que o Spring Boot usa |
| **Entity** | Classe Java que representa uma tabela do banco |
| **Repository** | Interface que faz consultas no banco automaticamente |
| **EntityManager** | Objeto que gerencia conexões e executa SQL nativo |
| **Annotation** | Anotação - Instrução para o Spring (começa com @) |
| **Cascade** | Quando deleta o pai, deleta os filhos automaticamente |
| **Lazy Loading** | Carrega dados apenas quando necessário (economiza memória) |
| **Stored Procedure** | Função SQL armazenada no banco |
| **Trigger** | Código que executa automaticamente quando algo acontece |

---

## 12. PERGUNTAS FREQUENTES PARA OS ALUNOS

### ❓ **P: Por que usar Spring Boot e não JDBC puro?**

**R:** Com JDBC puro você precisa:
- Escrever SQL manualmente para cada operação
- Tratar conexões e transações manualmente
- Mapear resultados SQL para objetos Java manualmente

Com Spring Boot:
- Escreve métodos simples (`findByCpf()`)
- Spring cria SQL automaticamente
- Transações gerenciadas automaticamente
- Menos código = menos erros

---

### ❓ **P: O que acontece se eu mudar a classe Java?**

**R:** Com `ddl-auto=update`:
- Spring atualiza o banco automaticamente
- Adiciona novas colunas se você adicionar campos
- ⚠️ **Cuidado:** Não remove colunas ou renomeia (usa `validate` em produção)

---

### ❓ **P: Como eu sei qual SQL está sendo executado?**

**R:** Ative `show-sql=true` no `application.properties` e veja no console!

---

### ❓ **P: Qual a diferença entre @ManyToOne e @OneToMany?**

**R:**
- **@ManyToOne** = Muitos objetos têm 1 relacionamento (a chave estrangeira fica nesta entidade)
- **@OneToMany** = 1 objeto tem muitos relacionamentos (a chave estrangeira fica na outra entidade)

**Exemplo:**
- `Cliente` tem `@OneToMany List<Email>` = 1 cliente tem muitos emails
- `Email` tem `@ManyToOne Cliente` = muitos emails têm 1 cliente

---

### ❓ **P: Quando usar Stored Procedures?**

**R:** Use quando:
- Lógica complexa que precisa de múltiplas queries
- Performance crítica (procedures são otimizadas no banco)
- Reutilizar código SQL em múltiplas aplicações

---

### ❓ **P: FetchType.LAZY vs EAGER?**

**R:**
- **LAZY** = Carrega apenas quando você pedir (economiza memória) ⭐ RECOMENDADO
- **EAGER** = Carrega tudo imediatamente (pode ser lento com muitos dados)

**Exemplo LAZY:**
```java
Cliente cliente = repository.findById(1);
// Ainda não carregou os emails

List<Email> emails = cliente.getEmails();
// Agora carrega os emails do banco
```

---

## 13. EXERCÍCIOS PRÁTICOS PARA OS ALUNOS

### 🎯 Exercício 1: Criar uma Nova Entidade

**Desafio:** Mapear a tabela `regiao` que já existe no banco.

**Solução esperada:**
```java
@Entity
@Table(name = "regiao")
@Data
public class Regiao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_id")
    private Integer id;
    
    @Column(name = "reg_nome", nullable = false)
    private String nome;
    
    @Column(name = "reg_cidade")
    private String cidade;
    
    @Column(name = "reg_uf", length = 2)
    private String uf;
}
```

---

### 🎯 Exercício 2: Criar Repository com Métodos Customizados

**Desafio:** Criar métodos no `RegiaoRepository`:
- Buscar por cidade
- Buscar por UF
- Verificar se nome existe

**Solução esperada:**
```java
public interface RegiaoRepository extends JpaRepository<Regiao, Integer> {
    List<Regiao> findByCidade(String cidade);
    List<Regiao> findByUf(String uf);
    boolean existsByNome(String nome);
}
```

---

### 🎯 Exercício 3: Criar Relacionamento

**Desafio:** Criar relacionamento entre `Cliente` e `Regiao` (tabela intermediária `registro_regiao`).

**Dica:** Use `@OneToMany` no Cliente e `@ManyToOne` na tabela intermediária.

---

## 14. DICAS PARA A APRESENTAÇÃO

### ✅ **Checklist Antes de Apresentar**

1. ✅ **Tenha o MySQL rodando**
   ```bash
   mysql -u root -p
   USE HomeHero;
   ```

2. ✅ **Configure o `application.properties` corretamente**
   - URL do banco
   - Usuário e senha

3. ✅ **Ative `show-sql=true`**
   - Para mostrar SQL no console durante a apresentação

4. ✅ **Teste os exemplos antes**
   - Crie um cliente
   - Busque por CPF
   - Veja o SQL gerado

---

### 🎤 **Roteiro de Apresentação (30 minutos)**

**1. Introdução (5 min)**
- O que é Spring Boot
- Por que usar
- Arquitetura geral

**2. Conexão com Banco (5 min)**
- Mostrar `application.properties`
- Explicar URL, usuário, senha
- Mostrar dependências no `pom.xml`

**3. Mapeamento de Entidades (10 min)**
- Mostrar classe `Endereco.java` (simples)
- Depois `Cliente.java` (com relacionamentos)
- Explicar cada anotação

**4. Repositories (5 min)**
- Mostrar `ClienteRepository`
- Demonstrar `findByCpf()` funcionando
- Mostrar SQL gerado no console

**5. Procedures e Triggers (3 min)**
- Explicar o conceito
- Mostrar código Java

**6. Demonstração ao Vivo (2 min)**
- Criar um cliente
- Buscar por CPF
- Ver resultado

---

## 15. RECURSOS VISUAIS PARA PROJEÇÃO

### 📊 Diagrama: Arquitetura Completa

```
┌──────────────────────────────────────────────────────┐
│              FRONTEND (TypeScript)                   │
│  - React/Angular/Vue                                  │
│  - Consome API REST                                   │
└──────────────────┬───────────────────────────────────┘
                   │ HTTP (JSON)
                   ▼
┌──────────────────────────────────────────────────────┐
│            BACKEND (Java Spring Boot)                │
│                                                       │
│  ┌──────────────┐  ┌──────────────┐                  │
│  │ Controllers  │→ │   Services   │                  │
│  │  (API REST)  │  │ (Lógica)     │                  │
│  └──────┬───────┘  └──────┬───────┘                  │
│         │                  │                          │
│         └─────────┬────────┘                          │
│                   ▼                                    │
│         ┌──────────────────┐                           │
│         │   Repositories  │                           │
│         │  (JPA Queries)   │                           │
│         └────────┬─────────┘                           │
└──────────────────┼────────────────────────────────────┘
                   │ JDBC
                   ▼
┌──────────────────────────────────────────────────────┐
│           BANCO DE DADOS (MySQL)                       │
│  - Tabelas                                            │
│  - Stored Procedures                                  │
│  - Triggers                                           │
└──────────────────────────────────────────────────────┘
```

---

### 📊 Diagrama: Fluxo de Dados

```
┌─────────────┐
│   Usuário   │
└──────┬──────┘
       │ Clica em "Salvar Cliente"
       ▼
┌─────────────────────┐
│ Frontend TypeScript │
│ form.submit()       │
└──────┬──────────────┘
       │ POST /api/clientes
       ▼
┌─────────────────────┐
│ ClienteController   │  ← Recebe requisição HTTP
│ @PostMapping        │
└──────┬──────────────┘
       │ clienteService.save()
       ▼
┌─────────────────────┐
│ ClienteService      │  ← Regras de negócio
│ - Validar CPF       │
│ - Criptografar senha│
└──────┬──────────────┘
       │ clienteRepository.save()
       ▼
┌─────────────────────┐
│ ClienteRepository   │  ← Interface JPA
│ save(Cliente)       │
└──────┬──────────────┘
       │ Hibernate traduz
       ▼
┌─────────────────────┐
│    Hibernate        │  ← Cria SQL automaticamente
│ INSERT INTO ...     │
└──────┬──────────────┘
       │ Executa SQL
       ▼
┌─────────────────────┐
│   MySQL Database    │  ← Dados salvos
│   cliente table     │
└─────────────────────┘
```

---

## 16. CÓDIGO DE EXEMPLO COMPLETO PARA DEMONSTRAÇÃO

### 💻 Exemplo Completo: Service e Controller

```java
@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private EmailRepository emailRepository;
    
    @Autowired
    private EnderecoRepository enderecoRepository;
    
    // Criar cliente completo
    public Cliente criarCliente(String nome, String cpf, String email, Endereco endereco) {
        // 1. Criar endereço
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        
        // 2. Criar cliente
        Cliente cliente = new Cliente();
        cliente.setNomeCompleto(nome);
        cliente.setCpf(cpf);
        cliente.setEndereco(enderecoSalvo);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        
        // 3. Criar email
        Email emailObj = new Email();
        emailObj.setEnderecoEmail(email);
        emailObj.setCliente(clienteSalvo);
        emailRepository.save(emailObj);
        
        return clienteSalvo;
    }
    
    // Buscar cliente com emails
    public Cliente buscarClienteComEmails(Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
            // Carrega emails (lazy loading)
            List<Email> emails = cliente.getEmails();
            return cliente;
        }
        return null;
    }
}
```

---

## 17. CONCLUSÃO PARA OS ALUNOS

### ✅ **O que aprenderam?**

1. ✅ **Spring Boot simplifica** conexão com banco de dados
2. ✅ **JPA mapeia** tabelas SQL para classes Java automaticamente
3. ✅ **Repositories criam** SQL automaticamente baseado no nome do método
4. ✅ **Relacionamentos** entre tabelas são feitos com anotações simples
5. ✅ **Stored Procedures** podem ser chamadas via Java
6. ✅ **Triggers** são implementados com Listeners

### 🎯 **Próximos Passos**

1. Praticar criando novas entidades
2. Criar métodos customizados nos repositories
3. Testar relacionamentos
4. Implementar Services e Controllers
5. Criar API REST completa

---

## 18. REFERÊNCIAS RÁPIDAS

### 📚 Anotações Mais Usadas

| Anotação | Onde Usar | Exemplo |
|----------|-----------|---------|
| `@Entity` | Classe | `@Entity public class Cliente` |
| `@Table` | Classe | `@Table(name = "cliente")` |
| `@Id` | Campo | `@Id private Integer id;` |
| `@Column` | Campo | `@Column(name = "cli_nome")` |
| `@ManyToOne` | Campo | `@ManyToOne private Endereco endereco;` |
| `@OneToMany` | Campo | `@OneToMany private List<Email> emails;` |
| `@JoinColumn` | Com @ManyToOne | `@JoinColumn(name = "cli_end_id")` |

### 📚 Métodos Repository Mais Usados

| Método | SQL Gerado |
|--------|------------|
| `save(entity)` | `INSERT INTO ...` ou `UPDATE ...` |
| `findById(id)` | `SELECT * FROM ... WHERE id = ?` |
| `findAll()` | `SELECT * FROM ...` |
| `delete(entity)` | `DELETE FROM ... WHERE id = ?` |
| `count()` | `SELECT COUNT(*) FROM ...` |
| `findByNome(String)` | `SELECT * FROM ... WHERE nome = ?` |
| `existsByCpf(String)` | `SELECT COUNT(*) > 0 FROM ... WHERE cpf = ?` |

---

**Boa apresentação! 🎓**

**Lembre-se:** Comece simples, mostre exemplos práticos e sempre demonstre ao vivo o código funcionando!

