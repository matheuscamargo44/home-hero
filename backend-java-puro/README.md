# Backend Java Puro - HomeHero

Versão simplificada do backend em Java puro (sem frameworks como Spring Boot).

## Estrutura

```
backend-java-puro/
├── src/main/java/com/homehero/
│   ├── Main.java                    # Classe principal
│   ├── database/
│   │   └── DatabaseConnection.java  # Conexão com banco de dados
│   ├── model/                       # Modelos (POJOs)
│   │   ├── Admin.java
│   │   ├── Cliente.java
│   │   ├── Prestador.java
│   │   └── Servico.java
│   ├── dao/                         # Data Access Objects
│   │   ├── AdminDAO.java
│   │   ├── ClienteDAO.java
│   │   ├── PrestadorDAO.java
│   │   ├── ServicoDAO.java
│   │   └── EnderecoDAO.java
│   ├── service/                     # Lógica de negócio
│   │   ├── AuthService.java
│   │   └── DatabaseTestService.java
│   ├── controller/                  # Controllers REST
│   │   ├── AuthController.java
│   │   ├── AdminController.java
│   │   ├── ClienteController.java
│   │   ├── PrestadorController.java
│   │   ├── ServicoController.java
│   │   └── DatabaseTestController.java
│   └── server/
│       └── SimpleHttpServer.java    # Servidor HTTP simples
```

## Dependências

Para compilar e executar, você precisa:

1. **JDK 17+**
2. **MySQL Connector/J** - Adicione ao classpath:
   - Baixe: https://dev.mysql.com/downloads/connector/j/
   - Ou use Maven/Gradle para gerenciar dependências

3. **Gson** (para JSON):
   - Baixe: https://github.com/google/gson
   - Ou adicione via Maven:
     ```xml
     <dependency>
         <groupId>com.google.code.gson</groupId>
         <artifactId>gson</artifactId>
         <version>2.10.1</version>
     </dependency>
     ```

## Configuração

Edite `DatabaseConnection.java` para configurar:
- URL do banco de dados
- Usuário
- Senha

## Como Executar

### Compilação Manual

```bash
# Compilar todos os arquivos
javac -cp ".:mysql-connector-java-8.0.33.jar:gson-2.10.1.jar" \
  src/main/java/com/homehero/**/*.java

# Executar
java -cp ".:mysql-connector-java-8.0.33.jar:gson-2.10.1.jar:src/main/java" \
  com.homehero.Main
```

### Com Maven

Crie um `pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.homehero</groupId>
    <artifactId>homehero-java-puro</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.0.33</version>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>
    </dependencies>
</project>
```

Depois:
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.homehero.Main"
```

## Endpoints

### Autenticação
- `POST /api/auth/login` - Login unificado

### Clientes
- `GET /api/clientes/cpf/{cpf}` - Buscar cliente por CPF
- `POST /api/clientes/cadastro` - Cadastrar cliente

### Prestadores
- `GET /api/prestadores/cpf/{cpf}` - Buscar prestador por CPF
- `POST /api/prestadores/cadastro` - Cadastrar prestador

### Serviços
- `GET /api/servicos` - Listar serviços ativos

### Admin
- `POST /api/admin/create-default` - Criar admin padrão
- `POST /api/admin/verify` - Verificar token admin

### Database Test (Procedures, Views e Triggers)
- `GET /api/database-test/views` - Listar views disponíveis
- `POST /api/database-test/view/{viewName}` - Executar uma view
- `GET /api/database-test/procedures` - Listar procedures disponíveis
- `POST /api/database-test/procedure/{procedureName}` - Executar uma procedure
- `GET /api/database-test/triggers` - Listar triggers disponíveis
- `GET /api/database-test/trigger/{triggerName}` - Obter informações de um trigger
- `POST /api/database-test/trigger/{triggerName}?operation=SELECT` - Testar trigger
- `POST /api/database-test/trigger/{triggerName}?execute` - Executar ação de trigger

## Diferenças do Spring Boot

Esta versão em Java puro:
- Não usa Spring Boot ou qualquer framework
- Usa `HttpServer` do JDK para servidor HTTP
- Usa JDBC direto (sem JPA/Hibernate)
- Usa `CallableStatement` para executar procedures
- Usa `PreparedStatement` para executar views
- Usa Gson para JSON (em vez de Jackson)
- Código mais simples e direto
- Ideal para entender os conceitos básicos

## Funcionalidades Implementadas

✅ **CRUD básico** - Clientes, Prestadores, Serviços, Admin  
✅ **Autenticação** - Login unificado  
✅ **Procedures** - Execução de stored procedures do MySQL  
✅ **Views** - Execução de views do banco de dados  
✅ **Triggers** - Teste e execução de triggers

