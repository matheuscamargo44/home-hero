# Guia de Deploy

## Frontend (Angular) - Vercel ✅

O frontend está configurado para deploy na Vercel através do arquivo `vercel.json`.

**Status:** Configurado e pronto para deploy

## Backend (Spring Boot) - Opções de Deploy

A Vercel **não suporta** aplicações Java/Spring Boot. Use uma das opções abaixo:

### Opção 1: Railway (Recomendado - Mais fácil)

1. Acesse [railway.app](https://railway.app)
2. Conecte seu repositório GitHub
3. Selecione o diretório `backend`
4. Railway detectará automaticamente o Java e Maven
5. Configure as variáveis de ambiente:
   - `DATABASE_URL` - URL completa do MySQL (ex: `jdbc:mysql://host:port/database`)
   - `DATABASE_USERNAME` - Usuário do banco
   - `DATABASE_PASSWORD` - Senha do banco
   - `PORT` - Porta (Railway define automaticamente)

**Arquivo:** `backend/railway.json` já está configurado

### Opção 2: Render

1. Acesse [render.com](https://render.com)
2. Crie um novo Web Service
3. Conecte o repositório GitHub
4. Configure:
   - **Root Directory:** `backend`
   - **Build Command:** `mvn clean package -DskipTests`
   - **Start Command:** `java -jar target/homehero-backend-1.0.0.jar`
5. Configure as variáveis de ambiente

**Arquivo:** `backend/render.yaml` já está configurado

### Opção 3: Docker (Qualquer plataforma que suporte Docker)

1. Use o `Dockerfile` fornecido em `backend/Dockerfile`
2. Build: `docker build -t homehero-backend ./backend`
3. Run: `docker run -p 8080:8080 -e DATABASE_URL=... homehero-backend`

### Opção 4: Heroku

1. Instale o Heroku CLI
2. `cd backend`
3. `heroku create`
4. Configure variáveis de ambiente
5. `git push heroku main`

**Arquivo:** `backend/Procfile` já está configurado

## Configuração do Banco de Dados

Para produção, você precisará de um banco MySQL hospedado:

- **Railway:** Oferece MySQL como addon
- **Render:** Oferece MySQL como serviço
- **PlanetScale:** MySQL serverless gratuito
- **AWS RDS:** MySQL gerenciado
- **Clever Cloud:** MySQL gratuito

## Variáveis de Ambiente Necessárias

```env
DATABASE_URL=jdbc:mysql://host:port/database?useSSL=true&serverTimezone=UTC
DATABASE_USERNAME=seu_usuario
DATABASE_PASSWORD=sua_senha
PORT=8080
SHOW_SQL=false
```

## Atualizar Frontend para Produção

Após fazer deploy do backend, atualize a URL da API no arquivo `frontend/src/environments/environment.prod.ts`:

```typescript
export const environment = {
  production: true,
  apiUrl: 'https://seu-backend.railway.app/api' // Substitua pela URL do seu backend
};
```

**Importante:** 
- Os serviços já estão configurados para usar `environment.apiUrl`
- O build de produção usa automaticamente `environment.prod.ts`
- Faça commit e push após atualizar a URL

