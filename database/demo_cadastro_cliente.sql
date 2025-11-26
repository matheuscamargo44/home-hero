-- ============================================================
-- Script de apoio: Demo cadastro de cliente
-- ============================================================
-- Execute a partir da raiz do projeto:
--   mysql -u root -p < database/demo_cadastro_cliente.sql
-- O script reinicia o schema, popula dados auxiliares e mostra
-- como usar as procedures/views durante a apresentação.
-- ============================================================

SOURCE database/dbHomeHero.sql;

USE homehero;

-- ============================================================
-- 1) Dados auxiliares para a demo
-- ============================================================
INSERT INTO regiao (reg_nome, reg_cidade, reg_uf)
VALUES
  ('Curitiba Centro', 'Curitiba', 'PR'),
  ('São Paulo Zona Sul', 'São Paulo', 'SP'),
  ('Rio Zona Norte', 'Rio de Janeiro', 'RJ');

INSERT INTO categoria_servico (cat_nome, cat_descricao)
VALUES
  ('Limpeza', 'Serviços de limpeza residencial'),
  ('Reparos', 'Pequenos reparos e manutenção'),
  ('Jardinagem', 'Cuidados com jardins e áreas externas');

-- Endereço base usado pela procedure (simula o endereço digitado no app)
INSERT INTO endereco (
  end_logradouro, end_numero, end_complemento,
  end_bairro, end_cidade, end_uf, end_cep
) VALUES (
  'Rua das Flores', '123', 'Apto 42',
  'Batel', 'Curitiba', 'PR', '80000-000'
);
SET @endereco_demo := LAST_INSERT_ID();

-- ============================================================
-- 2) Procedimento de cadastro (plano B para a demo)
-- ============================================================
SELECT '== PROCEDURE inserir_cliente ==' AS Etapa;
CALL inserir_cliente(
  'Cliente Procedure Demo',
  '12345678901',
  '1990-08-15',
  'SenhaSegura#1',
  @endereco_demo,
  'cliente.procedure@demo.com',
  '(41)98888-7777'
);

SELECT '== PROCEDURE pesquisar_clientes_por_nome_exato ==' AS Etapa;
CALL pesquisar_clientes_por_nome_exato('Cliente Procedure Demo');

-- ============================================================
-- 3) Views para evidenciar o cadastro
-- ============================================================
SELECT '== VIEW view_dados_de_clientes (cliente da procedure) ==' AS Etapa;
SELECT *
FROM view_dados_de_clientes
WHERE `E-mail do cliente` = 'cliente.procedure@demo.com';

SELECT '== VIEW view_dados_de_clientes (agregação por cidade) ==' AS Etapa;
SELECT
  `Cidade`,
  COUNT(*) AS `Total de clientes`
FROM view_dados_de_clientes
GROUP BY `Cidade`
ORDER BY COUNT(*) DESC;

-- ============================================================
-- 4) Blocos para validar o cliente criado via aplicação
--    (substitua pelos dados digitados durante a demo)
-- ============================================================
SELECT '== PLACEHOLDER: Procedure com dados reais ==' AS Etapa;
SELECT 'Edite o bloco abaixo com o endereço e dados digitados no app caso precise repetir manualmente.' AS Instrucoes;
/*
CALL inserir_cliente(
  'NOME_DO_APP',
  'CPF_SOMENTE_NUMEROS',
  '1994-05-10',
  'Senha#Do#App',
  ENDERECO_ID_EXISTENTE,
  'email@exemplo.com',
  '(41)90000-0000'
);
*/

SELECT '== PLACEHOLDER: Consulta à view com e-mail digitado ==' AS Etapa;
/*
SELECT *
FROM view_dados_de_clientes
WHERE `E-mail do cliente` = 'email@exemplo.com';
*/

-- Fim do script





