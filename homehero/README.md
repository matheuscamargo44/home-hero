# HomeHero - Plataforma de Serviços para o Lar

## 📋 Visão Geral

HomeHero é uma plataforma completa para conectar clientes com prestadores de serviços domésticos, empresas parceiras e gerenciar todo o ciclo de vida dos serviços, desde o agendamento até o pagamento e avaliação.

## 🏗️ Arquitetura

### Backend (Java Spring Boot)
- **Porta**: 8080
- **Tecnologias**: Spring Boot 3.5.7, Spring Data JPA, Spring Security, MySQL
- **Padrão**: MVC (Model-View-Controller)
- **Responsabilidades**:
  - API REST para comunicação com o frontend
  - Conexão e gerenciamento do banco de dados MySQL
  - Regras de negócio e validações
  - Autenticação e segurança (BCrypt)
  - Criptografia de senhas

### Frontend (TypeScript)
- **Opções**: React, Angular ou Vue.js com TypeScript
- **Porta sugerida**: 3000 (React) ou 4200 (Angular)
- **Responsabilidades**:
  - Interface do usuário (UI/UX)
  - Consumo da API REST do backend
  - Validações de formulário no cliente
  - Gerenciamento de estado

## 📁 Estrutura do Projeto

```
homehero/
├── src/
│   ├── main/
│   │   ├── java/com/homehero/homehero/
│   │   │   ├── config/          # Configurações (CORS, Security)
│   │   │   ├── model/            # Entidades JPA (Cliente, Prestador, etc.)
│   │   │   ├── repository/       # Interfaces JPA Repository
│   │   │   ├── service/          # Lógica de negócio (a criar)
│   │   │   ├── controller/       # Controllers REST (a criar)
│   │   │   └── HomeheroApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## 🚀 Como Começar

### Pré-requisitos
- Java 25 (ou versão compatível)
- Maven 3.6+
- MySQL 8.0+
- Node.js 18+ (para o frontend)
- Git

### 1. Configurar o Banco de Dados

Execute o script SQL fornecido para criar o banco `HomeHero` e todas as tabelas:

```sql
CREATE DATABASE HomeHero;
USE HomeHero;
-- Execute todo o script SQL fornecido
```

### 2. Configurar o Backend

1. **Ajustar credenciais do MySQL** no arquivo `application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=sua_senha_aqui
```

2. **Instalar dependências**:
```bash
mvn clean install
```

3. **Executar o backend**:
```bash
mvn spring-boot:run
```

4. **Testar a API**:
```bash
curl http://localhost:8080/api/public/health
```

Resposta esperada:
```json
{
  "status": "OK",
  "message": "API HomeHero está funcionando!"
}
```

### 3. Configurar o Frontend

Escolha uma das opções abaixo:

#### Opção A: React + TypeScript + Vite
```bash
npm create vite@latest frontend -- --template react-ts
cd frontend
npm install
npm install axios  # Para fazer requisições HTTP
npm run dev
```

#### Opção B: Angular
```bash
ng new frontend --routing --style=css
cd frontend
npm install
ng serve
```

#### Opção C: Vue.js + TypeScript
```bash
npm create vite@latest frontend -- --template vue-ts
cd frontend
npm install
npm install axios
npm run dev
```

### 4. Criar Serviço HTTP no Frontend

Exemplo com React + Axios:

```typescript
// src/services/api.ts
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;
```

## 📝 Próximos Passos

### Backend (Java Spring Boot)

1. **Completar entidades JPA**:
   - [ ] Email, Telefone
   - [ ] Servico, CategoriaServico
   - [ ] AgendamentoServico
   - [ ] Pagamento, Disputa, Avaliacao
   - [ ] Demais entidades do banco

2. **Criar Services**:
   - [ ] ClienteService (cadastro, validação CPF)
   - [ ] PrestadorService
   - [ ] EmpresaService
   - [ ] AuthService (login, autenticação)
   - [ ] AgendamentoService
   - [ ] PagamentoService

3. **Criar Controllers REST**:
   - [ ] ClienteController (`/api/clientes`)
   - [ ] PrestadorController (`/api/prestadores`)
   - [ ] AuthController (`/api/auth/login`, `/api/auth/register`)
   - [ ] AgendamentoController (`/api/agendamentos`)
   - [ ] ServicoController (`/api/servicos`)

4. **Implementar Autenticação JWT**:
   - [ ] Adicionar dependência `spring-boot-starter-jwt`
   - [ ] Criar JwtUtil para gerar/validar tokens
   - [ ] Criar filtro de autenticação

5. **Validações e Regras de Negócio**:
   - [ ] Validação de CPF/CNPJ únicos (RN001)
   - [ ] Validação de cancelamento com 24h antecedência (RN003)
   - [ ] Validação de avaliação (RN006)
   - [ ] Implementar BCrypt para senhas (RNF004)

### Frontend (TypeScript)

1. **Estrutura de Pastas**:
   ```
   frontend/
   ├── src/
   │   ├── components/     # Componentes React/Vue/Angular
   │   ├── pages/          # Páginas/Rotas
   │   ├── services/       # Serviços de API
   │   ├── models/         # Interfaces/Models TypeScript
   │   ├── utils/          # Funções auxiliares
   │   └── App.tsx         # Componente principal
   ```

2. **Criar Models TypeScript**:
   ```typescript
   // models/Cliente.ts
   export interface Cliente {
     id?: number;
     nomeCompleto: string;
     cpf: string;
     dataNascimento: string;
     formaPagamentoPreferida: string;
   }
   ```

3. **Implementar Telas**:
   - [ ] Tela de Login
   - [ ] Tela de Cadastro (Cliente, Prestador, Empresa)
   - [ ] Dashboard
   - [ ] Listagem de Prestadores
   - [ ] Agendamento de Serviços
   - [ ] Histórico de Serviços

4. **Integração com API**:
   - [ ] Criar serviços para cada endpoint
   - [ ] Implementar tratamento de erros
   - [ ] Gerenciar autenticação (JWT tokens)
   - [ ] Implementar interceptors para adicionar tokens

## 🔐 Segurança

- **Senhas**: Criptografadas com BCrypt (RNF004)
- **Autenticação**: JWT Tokens (a implementar)
- **CORS**: Configurado para permitir comunicação com frontend
- **Validações**: Dados obrigatórios validados no backend

## 📊 Banco de Dados

- **SGBD**: MySQL 8.0+
- **Nome do Banco**: HomeHero
- **Tabelas**: 23 tabelas principais
- **Views**: 4 views para consultas complexas
- **Procedures**: 5 stored procedures
- **Triggers**: 3 triggers para auditoria

## 🧪 Testes

### Testar Backend
```bash
mvn test
```

### Testar API Manualmente
Use Postman, Insomnia ou curl:
```bash
# Health check
curl http://localhost:8080/api/public/health

# Exemplo de POST (após implementar controller)
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{"nomeCompleto":"João Silva","cpf":"123.456.789-00",...}'
```

## 📚 Documentação de Referência

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [React Documentation](https://react.dev/)
- [Angular Documentation](https://angular.io/)
- [Vue.js Documentation](https://vuejs.org/)
- [TypeScript Documentation](https://www.typescriptlang.org/)

## 🤝 Contribuindo

1. Crie uma branch para sua feature: `git checkout -b feature/nova-funcionalidade`
2. Commit suas mudanças: `git commit -m 'Adiciona nova funcionalidade'`
3. Push para a branch: `git push origin feature/nova-funcionalidade`
4. Abra um Pull Request

## 📄 Licença

Este é um projeto acadêmico.

## ⚠️ Notas Importantes

1. **Credenciais do Banco**: Ajuste `application.properties` com suas credenciais MySQL
2. **Porta do Backend**: Padrão 8080 (altere se necessário)
3. **CORS**: Configurado para localhost:3000 e localhost:4200
4. **Java Version**: Certifique-se de usar Java 25 ou compatível
5. **Spring Security**: Configurado para permitir acesso público a `/api/public/**` e `/api/auth/**`

## 🐛 Solução de Problemas

### Backend não conecta ao MySQL
- Verifique se o MySQL está rodando
- Confirme credenciais em `application.properties`
- Verifique se o banco `HomeHero` existe

### Erro de CORS no frontend
- Verifique se `CorsConfig.java` está configurado
- Confirme que a URL do backend está correta

### Porta já em uso
- Altere `server.port` em `application.properties`
- Ou pare o processo que está usando a porta 8080

---

**Última atualização**: Dezembro 2024

