# Demo guiado – Cadastro de Cliente

Guia rápido para preparar a demonstração do fluxo de cadastro de cliente, evidenciando como o backend persiste os dados e como consultar procedures/views no banco local.

## Pré-requisitos
- MySQL/MariaDB local com acesso via linha de comando (`mysql`).
- Java 17 + Maven 3.9 (para o backend Spring Boot em `backend`).
- Node.js 18+ e Angular CLI 17 (`npm install -g @angular/cli`) para o frontend em `frontend`.
- Git Bash ou PowerShell com acesso ao diretório do projeto `C:\Users\mathe\Documents\home-hero`.

## Preparação do ambiente (T-30 min)
1. **Banco**  
   - Opcional: recrie o schema do zero para começar limpo:
     ```
     cd C:\Users\mathe\Documents\home-hero
     mysql -u root -p < database\dbHomeHero.sql
     ```
   - Carregue o script da demo para deixar queries prontas (detalhes abaixo):
     ```
     mysql -u root -p < database\demo_cadastro_cliente.sql
     ```
     > O script é idempotente: ele reinicia o schema chamando `dbHomeHero.sql`, insere dados auxiliares e disponibiliza blocos de consulta às views.
2. **Backend**  
   ```
   cd C:\Users\mathe\Documents\home-hero\backend
   mvn spring-boot:run
   ```
   - Aguarde o log `Started HomeHeroApplication` (porta padrão 8080).
3. **Frontend**  
   ```
   cd C:\Users\mathe\Documents\home-hero\frontend
   npm install
   npm run start
   ```
   - Acesse `http://localhost:4200` e valide o carregamento da landing page.

## Checklist rápido antes da apresentação
- [ ] Backend e frontend em execução sem erros.
- [ ] Banco `homehero` acessível via `mysql -u root -p homehero`.
- [ ] Script `database/demo_cadastro_cliente.sql` testado (rodar blocos individuais conforme necessário).
- [ ] Navegador com aba limpa aberta na rota de cadastro.
- [ ] Credenciais/telefone fictícios anotados para digitar ao vivo.

## Fluxo demonstrado no app
1. **Acesso inicial** – Mostrar rapidamente a landing page para contextualizar.
2. **Início do cadastro** – Clicar na CTA referente ao cliente (ex.: “Quero contratar” / “Sou cliente”).
3. **Formulário** – Preencher os campos obrigatórios (nome completo, CPF, e-mail, telefone, senha e endereço).  
   - Use um CPF válido apenas em formato numérico (o backend limpa os caracteres).  
   - Guarde o e-mail digitado para validar no banco depois.
4. **Envio** – Submeter o formulário e destacar o feedback positivo exibido pela SPA (loading + toast/mensagem).
5. **Confirmação** – Ressaltar que o backend:
   - Insere endereço e cliente em transação.
   - Valida unicidade via queries nativas (`SELECT COUNT(*) FROM cliente WHERE cli_email = ?`).

## Evidências no banco (procedures/views)
Com o cadastro concluído, alterne para o terminal MySQL para relacionar a tela com os elementos do banco:

1. **Executar a procedure `pesquisar_clientes_por_nome_exato`**  
   ```sql
   USE homehero;
   CALL pesquisar_clientes_por_nome_exato('NOME_DIGITADO');
   ```
   Explique que a procedure retorna o mesmo registro criado via API, comprovando que o banco está consistente.

2. **Consultar a view `view_dados_de_clientes`**  
   ```sql
   SELECT *
   FROM view_dados_de_clientes
   WHERE `E-mail do cliente` = 'EMAIL_DIGITADO';
   ```
   Mostre que a view agrega endereço + dados cadastrais em uma única consulta, ideal para relatórios.

3. **(Opcional) Rodar os blocos do script da demo**  
   - O arquivo `database/demo_cadastro_cliente.sql` contém:
     - Reinicialização completa do schema (`SOURCE database/dbHomeHero.sql;`).
     - Inserts auxiliares (região/endereço) usados nos exemplos.
     - Chamadas às procedures `inserir_cliente` e `pesquisar_clientes_por_nome_exato`.
     - Consultas às views `view_dados_de_clientes` e estatísticas baseadas nos novos dados.
   - Execute blocos individuais no MySQL Workbench ou `mysql` para explicar cada etapa sem digitar ao vivo.

## Pós-demo / contingências
- Se algo falhar no cadastro online, utilize o bloco `CALL inserir_cliente` do script para mostrar o fluxo direto no banco enquanto investiga o frontend.
- Caso precise repetir a demo, rode novamente:
  ```
  mysql -u root -p < database\demo_cadastro_cliente.sql
  ```
  Isso zera o banco e repõe os dados de apoio, garantindo ambiente previsível.

## Roteiro sugerido (linha do tempo)
| Momento | Ação | Observações |
| --- | --- | --- |
| T-15 min | Rodar `database/demo_cadastro_cliente.sql` | Garante schema limpo e dados auxiliares. |
| T-10 min | Subir backend (`mvn spring-boot:run`) | Conferir logs de erro. |
| T-5 min | Subir frontend (`npm run start`) | Deixar aba já apontada para o formulário. |
| Live 00:00 | Contexto rápido da landing page | Apontar dor do usuário e CTA. |
| Live 01:00 | Preencher formulário de cliente | Narrar cada campo obrigatório. |
| Live 02:30 | Mostrar feedback da SPA | Destacar validações + mensagens de sucesso. |
| Live 03:00 | Alternar para terminal MySQL | Executar blocos 2 e 3 do script (procedures/views). |
| Live 04:30 | Conectar resultado com slides | Explicar como a view alimenta relatórios. |
| Live 05:00 | Q&A / plano B | Mostrar blocos “placeholder” se houver dúvidas específicas. |

## Próximos passos sugeridos
- Preparar scripts semelhantes para agendamento/pagamento (para demonstrar triggers).
- Gerar prints ou tabelas com os resultados das views para anexar aos slides oficiais.

