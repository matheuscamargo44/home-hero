# HomeHero - Plataforma de Serviços Domiciliares

Plataforma completa desenvolvida em Spring Boot que conecta clientes com prestadores de serviços e empresas, facilitando o agendamento, pagamento e gestão de serviços domiciliares.

## Tecnologias Utilizadas

- **Spring Boot 3.5.7**: Framework Java para desenvolvimento de aplicações
- **Spring Data JPA**: Camada de persistência para acesso a dados
- **MySQL**: Banco de dados relacional
- **Lombok**: Biblioteca para reduzir boilerplate code
- **Maven**: Gerenciador de dependências e build

## Estrutura do Projeto

```
homehero/
├── src/
│   ├── main/
│   │   ├── java/com/homehero/homehero/
│   │   │   ├── model/          # 24 entidades JPA
│   │   │   ├── repository/     # 24 repositories + custom repositories
│   │   │   └── listener/       # Listeners para eventos JPA
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## Instalação

1. **Clonar o repositório:**

```bash
git clone https://github.com/seu-usuario/home-hero.git
cd home-hero
```

2. **Configurar o banco de dados:**

Edite o arquivo `homehero/src/main/resources/application.properties` e configure as credenciais do MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/HomeHero?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=sua_senha
```

3. **Executar a aplicação:**

```bash
cd homehero
mvn spring-boot:run
```

Ou execute através da IDE de sua preferência.

## Modelos de Dados

O sistema possui 24 entidades principais:

- **Cliente**: Clientes da plataforma
- **Prestador**: Prestadores de serviços individuais
- **Empresa**: Empresas que oferecem serviços
- **Endereco**: Endereços dos usuários e agendamentos
- **Regiao**: Regiões de atuação
- **RegistroRegiao**: Registro de clientes/prestadores/empresas por região
- **Email**: Emails de clientes/prestadores/empresas (multivalorado)
- **Telefone**: Telefones de clientes/prestadores/empresas (multivalorado)
- **CategoriaServico**: Categorias de serviços
- **Servico**: Serviços oferecidos na plataforma
- **PrestadorServico**: Ofertas de serviços por prestadores
- **EmpresaServico**: Ofertas de serviços por empresas
- **AgendamentoServico**: Agendamentos de serviços
- **AtribuicaoServico**: Atribuição de agendamentos a funcionários
- **HistoricoStatusAgendamento**: Histórico de mudanças de status
- **FuncionarioEmpresa**: Funcionários de empresas
- **DisponibilidadePrestador**: Disponibilidade de prestadores
- **CertificacaoPrestador**: Certificações de prestadores
- **Pagamento**: Pagamentos realizados
- **DisputaReembolso**: Disputas e solicitações de reembolso
- **Avaliacao**: Avaliações de serviços
- **Notificacao**: Notificações do sistema
- **ChatMensagem**: Mensagens de chat
- **ChatbotLog**: Logs de interações com chatbot

## Event Listeners

Os seguintes listeners foram implementados:

1. **AgendamentoServicoListener**: 
   - Cria histórico de status ao inserir novo agendamento
   - Registra mudanças de status do agendamento

2. **AvaliacaoListener**:
   - Cria notificação quando uma avaliação é registrada

## Banco de Dados

O sistema utiliza MySQL como banco de dados. Certifique-se de que:

1. O MySQL está instalado e rodando
2. O banco de dados `HomeHero` existe (ou será criado automaticamente se configurado)
3. As credenciais de acesso estão corretas no arquivo `application.properties`

O banco de dados será criado automaticamente na primeira execução (se não existir) e as tabelas serão atualizadas conforme o modelo JPA usando `spring.jpa.hibernate.ddl-auto=update`.

## Documentação Adicional

Para entender melhor os relacionamentos entre as entidades, consulte o arquivo [RELACIONAMENTOS.md](RELACIONAMENTOS.md).

## Desenvolvimento

O projeto utiliza Spring Data JPA para acesso a dados. Cada entidade possui seu respectivo Repository que estende `JpaRepository`, fornecendo métodos CRUD básicos e permitindo consultas customizadas.

Os relacionamentos entre entidades são configurados usando anotações JPA (`@OneToMany`, `@ManyToOne`, `@JoinColumn`) com `FetchType.LAZY` para melhor performance.

## Funcionalidades Principais

- Gerenciamento completo de clientes, prestadores e empresas
- Sistema de agendamento flexível (com prestador ou empresa)
- Controle de disponibilidade e certificações
- Sistema de pagamentos e disputas
- Avaliações e notificações
- Chat entre usuários
- Histórico de status de agendamentos
- Logs de chatbot

## Observações

- Os listeners JPA foram implementados para eventos automáticos
- As relações JPA são configuradas com cascade e lazy loading
- O comportamento de cascade permite operações automáticas em entidades relacionadas

