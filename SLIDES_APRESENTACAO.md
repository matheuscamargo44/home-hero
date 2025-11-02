# 📊 SLIDES RESUMIDOS PARA APRESENTAÇÃO
## HomeHero - Mapeamento de Banco de Dados com Spring Boot

---

## 🎯 SLIDE 1: INTRODUÇÃO

### O QUE É SPRING BOOT?
- Framework Java que **simplifica** desenvolvimento
- **Conecta automaticamente** ao banco de dados
- **Cria SQL automaticamente** baseado em métodos Java
- **Facilita** desenvolvimento de APIs

### O QUE VAMOS VER HOJE?
✅ Como conectar ao MySQL  
✅ Como mapear tabelas SQL para classes Java  
✅ Como buscar dados sem escrever SQL  
✅ Como usar Stored Procedures do banco  
✅ Como implementar Triggers em Java  

---

## 🎯 SLIDE 2: ARQUITETURA DO PROJETO

```
homehero/
├── pom.xml                    ← Bibliotecas (dependências)
├── application.properties     ← Configuração do banco
└── src/main/java/
    ├── HomeheroApplication.java  ← Inicia tudo
    ├── model/                    ← 23 entidades (tabelas)
    ├── repository/               ← 23 repositories (buscar dados)
    └── listener/                 ← Triggers (eventos automáticos)
```

### NÚMEROS DO PROJETO
- 📦 **23 tabelas** mapeadas para classes Java
- 🔍 **23 repositories** para buscar dados
- 🔧 **5 stored procedures** implementadas
- ⚡ **3 triggers** implementados

---

## 🎯 SLIDE 3: CONEXÃO COM BANCO

### 📄 application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/HomeHero
spring.datasource.username=root
spring.datasource.password=
```

**Traduzindo:**
- `jdbc:mysql://` = Quero me conectar ao MySQL
- `localhost:3306` = No meu computador, porta 3306
- `HomeHero` = Nome do banco de dados

**É como um endereço!** 📍

### 📦 Dependências (pom.xml)

```xml
<dependency>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
```

**O que são?**
- **JPA** = Interface para mapear Java → SQL
- **MySQL Connector** = Driver para conectar ao MySQL

---

## 🎯 SLIDE 4: MAPEAMENTO TABELA → CLASSE JAVA

### 💡 CONCEITO FUNDAMENTAL

**No banco SQL:**
```sql
CREATE TABLE cliente (
  cli_id INT PRIMARY KEY,
  cli_nome_completo VARCHAR(80),
  cli_cpf VARCHAR(14)
);
```

**No Java:**
```java
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "cli_id")
    private Integer id;
    
    @Column(name = "cli_nome_completo")
    private String nomeCompleto;
    
    @Column(name = "cli_cpf")
    private String cpf;
}
```

### 🔑 ANOTAÇÕES PRINCIPAIS

| Anotação | O que faz |
|----------|-----------|
| `@Entity` | Diz que é uma tabela |
| `@Table(name = "...")` | Nome da tabela no banco |
| `@Id` | Chave primária |
| `@Column(name = "...")` | Nome da coluna |

---

## 🎯 SLIDE 5: RELACIONAMENTOS ENTRE TABELAS

### 📊 Tipos de Relacionamentos

#### 1️⃣ **Relacionamento 1:N** (Um para Muitos)

**Exemplo: 1 Cliente tem Muitos Emails**

```java
// No Cliente.java
@OneToMany(mappedBy = "cliente")
private List<Email> emails;

// No Email.java
@ManyToOne
@JoinColumn(name = "ema_cli_id")
private Cliente cliente;
```

**Visual:**
```
Cliente "João"
  ├── Email: joao@gmail.com
  ├── Email: joao@hotmail.com
  └── Email: joao@yahoo.com
```

#### 2️⃣ **Relacionamento N:1** (Muitos para Um)

**Exemplo: Muitos Clientes têm o mesmo Endereço**

```java
@ManyToOne
@JoinColumn(name = "cli_endereco_id")
private Endereco endereco;
```

---

## 🎯 SLIDE 6: REPOSITORIES - BUSCA AUTOMÁTICA

### 🎯 O QUE SÃO REPOSITORIES?

**Repository = Interface que cria SQL automaticamente!**

```java
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCpf(String cpf);
}
```

### ✨ MÁGICA DO SPRING

**Você escreve:**
```java
clienteRepository.findByCpf("123.456.789-00");
```

**O Spring cria automaticamente:**
```sql
SELECT * FROM cliente WHERE cli_cpf = '123.456.789-00'
```

**Sem escrever SQL!** 🎉

### 📋 Métodos Automáticos Disponíveis

| Método Java | SQL Gerado |
|-------------|------------|
| `save(cliente)` | `INSERT INTO cliente ...` |
| `findById(id)` | `SELECT * FROM cliente WHERE id = ?` |
| `findAll()` | `SELECT * FROM cliente` |
| `delete(cliente)` | `DELETE FROM cliente WHERE id = ?` |
| `findByCpf(cpf)` | `SELECT * FROM cliente WHERE cli_cpf = ?` |
| `existsByCpf(cpf)` | `SELECT COUNT(*) > 0 FROM cliente WHERE cli_cpf = ?` |

---

## 🎯 SLIDE 7: STORED PROCEDURES

### 🎯 O QUE SÃO?

**Stored Procedure = Função SQL armazenada no banco**

**No banco:**
```sql
CREATE PROCEDURE pesquisar_clientes_por_nome_exato(IN nome VARCHAR(80))
BEGIN
  SELECT * FROM cliente WHERE cli_nome_completo = nome;
END
```

**No Java:**
```java
public List<Object[]> pesquisarClientesPorNomeExato(String nome) {
    Query query = entityManager.createNativeQuery(
        "CALL pesquisar_clientes_por_nome_exato(?)"
    );
    query.setParameter(1, nome);
    return query.getResultList();
}
```

### 💡 QUANDO USAR?

✅ Lógica complexa com múltiplas queries  
✅ Performance crítica  
✅ Reutilizar código SQL  

---

## 🎯 SLIDE 8: TRIGGERS - EVENTOS AUTOMÁTICOS

### 🎯 O QUE SÃO?

**Trigger = Código que executa automaticamente quando algo acontece**

**No banco:**
```sql
CREATE TRIGGER trigger_pos_inserir_agendamento_registrar_status_inicial
AFTER INSERT ON agendamento_servico
BEGIN
  INSERT INTO historico_status_agendamento ...
END
```

**No Java:**
```java
@EntityListeners(AgendamentoServicoListener.class)
public class AgendamentoServico {
    // ...
}

public class AgendamentoServicoListener {
    @PostPersist  // Executa DEPOIS de inserir
    public void posInserir(AgendamentoServico agendamento) {
        // Cria histórico automaticamente
    }
}
```

### 📅 Anotações de Evento

| Anotação | Quando Executa |
|----------|----------------|
| `@PostPersist` | DEPOIS de inserir |
| `@PostUpdate` | DEPOIS de atualizar |
| `@PreUpdate` | ANTES de atualizar |

---

## 🎯 SLIDE 9: DEMONSTRAÇÃO PRÁTICA

### 💻 Exemplo 1: Criar um Cliente

```java
// 1. Criar objeto Java
Cliente cliente = new Cliente();
cliente.setNomeCompleto("João Silva");
cliente.setCpf("123.456.789-00");

// 2. Salvar no banco
clienteRepository.save(cliente);

// 3. Spring executa automaticamente:
// SQL: INSERT INTO cliente (cli_nome_completo, cli_cpf) 
//      VALUES ('João Silva', '123.456.789-00')
```

### 💻 Exemplo 2: Buscar Cliente

```java
// Buscar por CPF
Optional<Cliente> cliente = clienteRepository.findByCpf("123.456.789-00");

// SQL gerado automaticamente:
// SELECT * FROM cliente WHERE cli_cpf = '123.456.789-00'
```

---

## 🎯 SLIDE 10: FLUXO COMPLETO

```
1. VOCÊ ESCREVE JAVA:
   Cliente cliente = new Cliente();
   cliente.setNomeCompleto("João");
   repository.save(cliente);

2. SPRING TRADUZ PARA SQL:
   INSERT INTO cliente (cli_nome_completo) VALUES ('João');

3. MYSQL EXECUTA O SQL:
   ✓ Dados salvos no banco

4. RESULTADO VOLTA PARA JAVA:
   cliente.getId() → retorna o ID gerado
```

---

## 🎯 SLIDE 11: RESUMO VISUAL

### 📊 Mapeamento Completo

```
BANCO SQL          ↔    CLASSE JAVA
────────────────────────────────────
CREATE TABLE       ↔    @Entity
cli_id INT         ↔    @Id Integer id
cli_nome VARCHAR   ↔    @Column String nome
FOREIGN KEY        ↔    @ManyToOne / @OneToMany
STORED PROCEDURE   ↔    EntityManager.createNativeQuery()
TRIGGER            ↔    @PostPersist / @PostUpdate (Listener)
```

---

## 🎯 SLIDE 12: NÚMEROS DO PROJETO HOMEHERO

### ✅ O QUE FOI IMPLEMENTADO

- ✅ **23 Entidades JPA** (mapeando todas as tabelas do banco)
- ✅ **23 Repositories** (interfaces para buscar dados)
- ✅ **5 Stored Procedures** (implementadas como métodos customizados)
- ✅ **3 Triggers** (implementados usando JPA Listeners)
- ✅ **Relacionamentos bidirecionais** configurados
- ✅ **FetchType.LAZY** em todos os relacionamentos (boa prática)

### 📊 Estrutura Final

```
model/          → 23 classes Java = 23 tabelas SQL
repository/      → 23 interfaces = métodos de busca
listener/        → 2 listeners = 3 triggers SQL
custom/          → 4 classes = 5 procedures SQL
```

---

## 🎯 SLIDE 13: CONCLUSÃO

### ✅ O QUE APRENDEMOS HOJE?

1. ✅ Spring Boot **simplifica** conexão com banco
2. ✅ JPA **mapeia** tabelas SQL para classes Java
3. ✅ Repositories **criam** SQL automaticamente
4. ✅ Relacionamentos são feitos com **anotações simples**
5. ✅ Stored Procedures podem ser **chamadas via Java**
6. ✅ Triggers são implementados com **Listeners**

### 🎯 PRÓXIMOS PASSOS

1. Criar Services (lógica de negócio)
2. Criar Controllers (API REST)
3. Implementar validações
4. Criar frontend TypeScript
5. Integrar tudo!

---

## 🎯 SLIDE 14: REFERÊNCIAS RÁPIDAS

### 📚 Anotações Mais Usadas

| Anotação | Uso |
|----------|-----|
| `@Entity` | Marca classe como tabela |
| `@Table(name = "...")` | Define nome da tabela |
| `@Id` | Chave primária |
| `@Column(name = "...")` | Nome da coluna |
| `@ManyToOne` | Relacionamento N:1 |
| `@OneToMany` | Relacionamento 1:N |
| `@JoinColumn` | Chave estrangeira |

### 📚 Métodos Repository

| Método | SQL |
|--------|-----|
| `save(entity)` | INSERT/UPDATE |
| `findById(id)` | SELECT WHERE id |
| `findAll()` | SELECT * |
| `findByNome(String)` | SELECT WHERE nome |
| `existsByCpf(String)` | SELECT COUNT > 0 |

---

## 🎯 SLIDE 15: DICAS FINAIS

### ✅ Para Estudar Mais

1. Pratique criando suas próprias entidades
2. Experimente diferentes tipos de relacionamentos
3. Crie métodos customizados nos repositories
4. Teste stored procedures e triggers
5. Leia a documentação do Spring Data JPA

### 🎓 Recursos Recomendados

- 📖 Spring Boot Documentation: https://spring.io/projects/spring-boot
- 📖 JPA Documentation: https://jakarta.ee/specifications/persistence/
- 📖 Spring Data JPA: https://spring.io/projects/spring-data-jpa

---

## 🎯 SLIDE 16: PERGUNTAS?

### 🤔 Tire suas dúvidas!

- Como funciona o mapeamento?
- Quando usar repositories vs procedures?
- Como otimizar queries?
- Como implementar novos relacionamentos?

---

**Obrigado pela atenção!** 🎓

