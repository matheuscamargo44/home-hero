-- =========================================================
-- 1) RESET DO BANCO
-- =========================================================
DROP DATABASE IF EXISTS homehero;
CREATE DATABASE homehero CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE homehero;

-- =========================================================
-- 2) TABELAS
-- =========================================================

-- 2.1) regiao
CREATE TABLE regiao (
  reg_id INT PRIMARY KEY AUTO_INCREMENT,
  reg_nome VARCHAR(80) NOT NULL,
  reg_cidade VARCHAR(60),
  reg_uf VARCHAR(2)
);

-- 2.2) endereco
CREATE TABLE endereco (
  end_id INT PRIMARY KEY AUTO_INCREMENT,
  end_logradouro VARCHAR(100),
  end_numero VARCHAR(15),
  end_complemento VARCHAR(60),
  end_bairro VARCHAR(60),
  end_cidade VARCHAR(60),
  end_uf VARCHAR(2),
  end_cep VARCHAR(10)
);

-- 2.3) cliente
CREATE TABLE cliente (
  cli_id INT PRIMARY KEY AUTO_INCREMENT,
  cli_nome VARCHAR(80) NOT NULL,
  cli_cpf VARCHAR(14) NOT NULL UNIQUE,
  cli_nascimento DATE NOT NULL,
  cli_senha VARCHAR(60) NOT NULL,
  end_id INT NOT NULL,
  cli_email VARCHAR(120) NOT NULL UNIQUE,
  cli_telefone VARCHAR(20) NOT NULL UNIQUE,
  FOREIGN KEY (end_id) REFERENCES endereco(end_id)
);

-- 2.4) prestador
CREATE TABLE prestador (
  pre_id INT PRIMARY KEY AUTO_INCREMENT,
  pre_nome VARCHAR(80) NOT NULL,
  pre_cpf VARCHAR(14) NOT NULL UNIQUE,
  pre_nascimento DATE NOT NULL,
  pre_areas VARCHAR(120),
  pre_experiencia VARCHAR(255),
  pre_certificados VARCHAR(255),
  pre_senha VARCHAR(60) NOT NULL,
  end_id INT NOT NULL,
  pre_email VARCHAR(120) NOT NULL UNIQUE,
  pre_telefone VARCHAR(20) NOT NULL UNIQUE,
  FOREIGN KEY (end_id) REFERENCES endereco(end_id)
);

-- 2.5) categoria_servico
CREATE TABLE categoria_servico (
  cat_id INT PRIMARY KEY AUTO_INCREMENT,
  cat_nome VARCHAR(60) NOT NULL,
  cat_descricao VARCHAR(255)
);

-- 2.6) servico
CREATE TABLE servico (
  ser_id INT PRIMARY KEY AUTO_INCREMENT,
  ser_nome VARCHAR(80) NOT NULL,
  ser_descricao VARCHAR(255),
  ser_preco_base FLOAT,
  ser_ativo BOOLEAN,
  cat_id INT,
  FOREIGN KEY (cat_id) REFERENCES categoria_servico(cat_id)
);

-- 2.7) registro_regiao
CREATE TABLE registro_regiao (
  rre_id INT PRIMARY KEY AUTO_INCREMENT,
  reg_id INT NOT NULL,
  cli_id INT,
  pre_id INT,
  rre_data DATE,
  FOREIGN KEY (reg_id) REFERENCES regiao(reg_id),
  FOREIGN KEY (cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (pre_id) REFERENCES prestador(pre_id)
);

-- 2.8) prestador_servico
CREATE TABLE prestador_servico (
  prs_id INT PRIMARY KEY AUTO_INCREMENT,
  pre_id INT NOT NULL,
  ser_id INT NOT NULL,
  prs_preco FLOAT,
  prs_ativo BOOLEAN,
  prs_data DATE,
  FOREIGN KEY (pre_id) REFERENCES prestador(pre_id),
  FOREIGN KEY (ser_id) REFERENCES servico(ser_id)
);

-- 2.9) disponibilidade_prestador
CREATE TABLE disponibilidade_prestador (
  dis_id INT PRIMARY KEY AUTO_INCREMENT,
  pre_id INT NOT NULL,
  dis_dia VARCHAR(10),
  dis_janela VARCHAR(20),
  dis_ativo BOOLEAN,
  FOREIGN KEY (pre_id) REFERENCES prestador(pre_id)
);

-- 2.10) certificacao_prestador
CREATE TABLE certificacao_prestador (
  cer_id INT PRIMARY KEY AUTO_INCREMENT,
  pre_id INT NOT NULL,
  cer_titulo VARCHAR(120),
  cer_instituicao VARCHAR(120),
  cer_data DATE,
  cer_url VARCHAR(255),
  FOREIGN KEY (pre_id) REFERENCES prestador(pre_id)
);

-- 2.11) agendamento_servico
CREATE TABLE agendamento_servico (
  age_id INT PRIMARY KEY AUTO_INCREMENT,
  cli_id INT NOT NULL,
  ser_id INT NOT NULL,
  pre_id INT,
  age_data DATE,
  age_janela VARCHAR(20),
  end_id INT,
  age_status VARCHAR(20),
  age_valor FLOAT,
  age_pago BOOLEAN,
  age_data_cancel DATE,
  age_motivo VARCHAR(120),
  FOREIGN KEY (cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (ser_id) REFERENCES servico(ser_id),
  FOREIGN KEY (pre_id) REFERENCES prestador(pre_id),
  FOREIGN KEY (end_id) REFERENCES endereco(end_id)
);

-- 2.12) pagamento
CREATE TABLE pagamento (
  pag_id INT PRIMARY KEY AUTO_INCREMENT,
  age_id INT NOT NULL,
  pag_forma VARCHAR(20),
  pag_valor FLOAT,
  pag_status VARCHAR(20),
  pag_ref VARCHAR(60),
  pag_data DATE,
  FOREIGN KEY (age_id) REFERENCES agendamento_servico(age_id)
);

-- 2.13) disputa_reembolso
CREATE TABLE disputa_reembolso (
  dsp_id INT PRIMARY KEY AUTO_INCREMENT,
  age_id INT NOT NULL,
  cli_id INT,
  pre_id INT,
  dsp_motivo VARCHAR(255),
  dsp_status VARCHAR(20),
  dsp_valor FLOAT,
  dsp_abertura DATE,
  dsp_fechamento DATE,
  FOREIGN KEY (age_id) REFERENCES agendamento_servico(age_id),
  FOREIGN KEY (cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (pre_id) REFERENCES prestador(pre_id)
);

-- 2.14) avaliacao
CREATE TABLE avaliacao (
  ava_id INT PRIMARY KEY AUTO_INCREMENT,
  age_id INT NOT NULL,
  cli_id INT NOT NULL,
  pre_id INT,
  ava_nota INT,
  ava_coment VARCHAR(400),
  ava_data DATE,
  FOREIGN KEY (age_id) REFERENCES agendamento_servico(age_id),
  FOREIGN KEY (cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (pre_id) REFERENCES prestador(pre_id)
);

-- 2.15) notificacao
CREATE TABLE notificacao (
  not_id INT PRIMARY KEY AUTO_INCREMENT,
  cli_id INT,
  pre_id INT,
  age_id INT,
  not_tipo VARCHAR(30),
  not_msg VARCHAR(255),
  not_enviado BOOLEAN,
  not_data DATE,
  FOREIGN KEY (cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (pre_id) REFERENCES prestador(pre_id),
  FOREIGN KEY (age_id) REFERENCES agendamento_servico(age_id)
);

-- 2.16) chat_mensagem (chat humano)
CREATE TABLE chat_mensagem (
  cha_id INT PRIMARY KEY AUTO_INCREMENT,
  age_id INT,
  cha_remetente_tipo VARCHAR(20),
  cha_remetente_id INT,
  cha_destinatario_tipo VARCHAR(20),
  cha_destinatario_id INT,
  cha_msg VARCHAR(500),
  cha_data DATE,
  FOREIGN KEY (age_id) REFERENCES agendamento_servico(age_id)
);

-- 2.17) historico_status_agendamento
CREATE TABLE historico_status_agendamento (
  his_id INT PRIMARY KEY AUTO_INCREMENT,
  age_id INT NOT NULL,
  his_status_ant VARCHAR(20),
  his_status_novo VARCHAR(20),
  his_data DATE,
  FOREIGN KEY (age_id) REFERENCES agendamento_servico(age_id)
);

-- 2.18) admin
CREATE TABLE admin (
  adm_id INT PRIMARY KEY AUTO_INCREMENT,
  adm_nome VARCHAR(80) NOT NULL,
  adm_email VARCHAR(120) NOT NULL UNIQUE,
  adm_senha VARCHAR(60) NOT NULL
);

USE homehero;

-- =========================================================
-- 3) VIEWS (mais simples / estáticas)
-- =========================================================

-- 3.1) Categorias de serviço
CREATE OR REPLACE VIEW view_categorias_servico AS
SELECT
  cat_id       AS 'ID da categoria',
  cat_nome     AS 'Nome da categoria',
  cat_descricao AS 'Descrição da categoria'
FROM categoria_servico
ORDER BY cat_nome;

-- 3.2) Serviços com suas categorias
CREATE OR REPLACE VIEW view_servicos_com_categorias AS
SELECT
  servico.ser_id        AS 'ID do serviço',
  servico.ser_nome      AS 'Nome do serviço',
  servico.ser_descricao AS 'Descrição do serviço',
  servico.ser_preco_base AS 'Preço base',
  servico.ser_ativo     AS 'Serviço ativo',
  categoria_servico.cat_id   AS 'ID da categoria',
  categoria_servico.cat_nome AS 'Nome da categoria'
FROM servico
JOIN categoria_servico USING (cat_id)
ORDER BY categoria_servico.cat_nome, servico.ser_nome;

-- 3.3) Prestadores (visão básica)
CREATE OR REPLACE VIEW view_prestadores_basico AS
SELECT
  prestador.pre_id      AS 'ID do prestador',
  prestador.pre_nome    AS 'Nome do prestador',
  prestador.pre_cpf     AS 'CPF do prestador',
  prestador.pre_email   AS 'E-mail do prestador',
  prestador.pre_telefone AS 'Telefone do prestador',
  endereco.end_cidade   AS 'Cidade',
  endereco.end_uf       AS 'UF'
FROM prestador
JOIN endereco USING (end_id)
ORDER BY prestador.pre_nome;

-- 3.4) Agendamentos (resumo)
CREATE OR REPLACE VIEW view_agendamentos_resumido AS
SELECT
  agendamento_servico.age_id   AS 'ID do agendamento',
  agendamento_servico.age_data AS 'Data',
  agendamento_servico.age_janela AS 'Período',
  agendamento_servico.age_status AS 'Status',
  agendamento_servico.age_valor  AS 'Valor',
  cliente.cli_nome            AS 'Cliente',
  prestador.pre_nome          AS 'Prestador',
  servico.ser_nome            AS 'Serviço'
FROM agendamento_servico
JOIN cliente USING (cli_id)
JOIN servico USING (ser_id)
JOIN prestador USING (pre_id)
ORDER BY agendamento_servico.age_data;

-- 3.5) Média de avaliação por prestador
CREATE OR REPLACE VIEW view_media_de_avaliacao_por_prestador AS
SELECT
  prestador.pre_id   AS 'ID do prestador',
  prestador.pre_nome AS 'Nome do prestador',
  COUNT(avaliacao.ava_id) AS 'Quantidade de avaliações',
  AVG(avaliacao.ava_nota) AS 'Média das notas'
FROM prestador
JOIN avaliacao USING (pre_id)
GROUP BY prestador.pre_id, prestador.pre_nome
ORDER BY AVG(avaliacao.ava_nota) DESC;

-- 3.6) Agendamentos por status
CREATE OR REPLACE VIEW view_agendamentos_por_status AS
SELECT
  age_status     AS 'Status do agendamento',
  COUNT(*)       AS 'Total de agendamentos',
  SUM(age_valor) AS 'Soma dos valores',
  AVG(age_valor) AS 'Valor médio'
FROM agendamento_servico
GROUP BY age_status
ORDER BY COUNT(*) DESC;

-- 3.7) Pagamentos por status
CREATE OR REPLACE VIEW view_pagamentos_por_status AS
SELECT
  pag_status    AS 'Status do pagamento',
  COUNT(*)      AS 'Quantidade de pagamentos',
  SUM(pag_valor) AS 'Soma dos valores pagos'
FROM pagamento
GROUP BY pag_status
ORDER BY COUNT(*) DESC;

-- 3.8) Disputas abertas
CREATE OR REPLACE VIEW view_disputas_abertas AS
SELECT
  disputa_reembolso.dsp_id      AS 'ID da disputa',
  disputa_reembolso.age_id      AS 'ID do agendamento',
  disputa_reembolso.dsp_status  AS 'Status da disputa',
  disputa_reembolso.dsp_motivo  AS 'Motivo da disputa',
  disputa_reembolso.dsp_valor   AS 'Valor em disputa',
  disputa_reembolso.dsp_abertura AS 'Data de abertura',
  cliente.cli_nome              AS 'Cliente',
  prestador.pre_nome            AS 'Prestador',
  servico.ser_nome              AS 'Serviço'
FROM disputa_reembolso
JOIN agendamento_servico USING (age_id)
JOIN servico USING (ser_id)
LEFT JOIN cliente ON cliente.cli_id = disputa_reembolso.cli_id
LEFT JOIN prestador ON prestador.pre_id = disputa_reembolso.pre_id
WHERE disputa_reembolso.dsp_status = 'Aberta'
ORDER BY disputa_reembolso.dsp_abertura DESC;

-- 3.9) Disponibilidade dos prestadores
CREATE OR REPLACE VIEW view_disponibilidade_prestadores AS
SELECT
  prestador.pre_id              AS 'ID do prestador',
  prestador.pre_nome            AS 'Nome do prestador',
  disponibilidade_prestador.dis_id    AS 'ID da disponibilidade',
  disponibilidade_prestador.dis_dia   AS 'Dia da semana',
  disponibilidade_prestador.dis_janela AS 'Janela de atendimento',
  disponibilidade_prestador.dis_ativo AS 'Disponibilidade ativa'
FROM prestador
JOIN disponibilidade_prestador USING (pre_id)
ORDER BY prestador.pre_nome, disponibilidade_prestador.dis_dia;

-- =========================================================
-- 4) PROCEDURES
-- =========================================================

DROP PROCEDURE IF EXISTS pesquisar_cliente_dados_pessoais;
DROP PROCEDURE IF EXISTS pesquisar_cliente_dados_completos;
DROP PROCEDURE IF EXISTS pesquisar_cliente_agendamentos_disputas;
DROP PROCEDURE IF EXISTS pesquisar_prestador_dados_pessoais;
DROP PROCEDURE IF EXISTS pesquisar_prestador_dados_completos;
DROP PROCEDURE IF EXISTS pesquisar_prestador_agendamentos_disputas;
DROP PROCEDURE IF EXISTS listar_agendamentos_por_id_de_cliente;
DROP PROCEDURE IF EXISTS inserir_agendamento_de_servico;
DROP PROCEDURE IF EXISTS cancelar_agendamento_de_servico;
DROP PROCEDURE IF EXISTS registrar_avaliacao_de_prestador;
DROP PROCEDURE IF EXISTS inserir_cliente;
DROP PROCEDURE IF EXISTS inserir_prestador;
DROP PROCEDURE IF EXISTS listar_agendamentos_por_periodo_e_status;
DROP PROCEDURE IF EXISTS abrir_disputa;
DROP PROCEDURE IF EXISTS fechar_disputa;
DROP PROCEDURE IF EXISTS inserir_pagamento;

DELIMITER $$

-- 4.1) Cliente - dados pessoais (cliente + endereço)
CREATE PROCEDURE pesquisar_cliente_dados_pessoais(
  IN p_nome VARCHAR(80)
)
BEGIN
  SELECT
    c.cli_id,
    c.cli_nome,
    c.cli_cpf,
    c.cli_nascimento,
    c.cli_email,
    c.cli_telefone,
    e.end_id,
    e.end_logradouro,
    e.end_numero,
    e.end_complemento,
    e.end_bairro,
    e.end_cidade,
    e.end_uf,
    e.end_cep
  FROM cliente c
  JOIN endereco e
    ON e.end_id = c.end_id
  WHERE c.cli_nome = p_nome
  ORDER BY c.cli_nome;
END $$

-- 4.2) Cliente - visão completa (pessoais + agendamentos, serviços, pagamentos, disputas, avaliações)
CREATE PROCEDURE pesquisar_cliente_dados_completos(
  IN p_nome VARCHAR(80)
)
BEGIN
  SELECT
    c.cli_id,
    c.cli_nome,
    c.cli_cpf,
    c.cli_nascimento,
    c.cli_email,
    c.cli_telefone,
    e.end_logradouro,
    e.end_numero,
    e.end_complemento,
    e.end_bairro,
    e.end_cidade,
    e.end_uf,
    e.end_cep,
    a.age_id,
    a.age_data,
    a.age_janela,
    a.age_status,
    a.age_valor,
    a.age_pago,
    s.ser_id,
    s.ser_nome,
    s.ser_descricao,
    s.ser_preco_base,
    p.pre_id,
    p.pre_nome,
    p.pre_email,
    p.pre_telefone,
    pag.pag_id,
    pag.pag_forma,
    pag.pag_valor,
    pag.pag_status,
    pag.pag_ref,
    pag.pag_data,
    d.dsp_id,
    d.dsp_status,
    d.dsp_motivo,
    d.dsp_valor,
    d.dsp_abertura,
    d.dsp_fechamento,
    av.ava_id,
    av.ava_nota,
    av.ava_coment,
    av.ava_data
  FROM cliente c
  JOIN endereco e
    ON e.end_id = c.end_id
  LEFT JOIN agendamento_servico a
    ON a.cli_id = c.cli_id
  LEFT JOIN servico s
    ON s.ser_id = a.ser_id
  LEFT JOIN prestador p
    ON p.pre_id = a.pre_id
  LEFT JOIN pagamento pag
    ON pag.age_id = a.age_id
  LEFT JOIN disputa_reembolso d
    ON d.age_id = a.age_id
   AND d.cli_id = c.cli_id
  LEFT JOIN avaliacao av
    ON av.age_id = a.age_id
   AND av.cli_id = c.cli_id
  WHERE c.cli_nome = p_nome
  ORDER BY a.age_data;
END $$

-- 4.3) Cliente - só agendamentos, disputas, pagamentos, avaliações, notificações
CREATE PROCEDURE pesquisar_cliente_agendamentos_disputas(
  IN p_nome VARCHAR(80)
)
BEGIN
  SELECT
    c.cli_id,
    c.cli_nome,
    a.age_id,
    a.age_data,
    a.age_janela,
    a.age_status,
    a.age_valor,
    a.age_pago,
    a.age_data_cancel,
    a.age_motivo,
    s.ser_id,
    s.ser_nome,
    s.ser_descricao,
    s.ser_preco_base,
    p.pre_id,
    p.pre_nome,
    p.pre_email,
    p.pre_telefone,
    pag.pag_id,
    pag.pag_forma,
    pag.pag_valor,
    pag.pag_status,
    pag.pag_ref,
    pag.pag_data,
    d.dsp_id,
    d.dsp_status,
    d.dsp_motivo,
    d.dsp_valor,
    d.dsp_abertura,
    d.dsp_fechamento,
    av.ava_id,
    av.ava_nota,
    av.ava_coment,
    av.ava_data,
    n.not_id,
    n.not_tipo,
    n.not_msg,
    n.not_enviado,
    n.not_data
  FROM cliente c
  LEFT JOIN agendamento_servico a
    ON a.cli_id = c.cli_id
  LEFT JOIN servico s
    ON s.ser_id = a.ser_id
  LEFT JOIN prestador p
    ON p.pre_id = a.pre_id
  LEFT JOIN pagamento pag
    ON pag.age_id = a.age_id
  LEFT JOIN disputa_reembolso d
    ON d.age_id = a.age_id
   AND d.cli_id = c.cli_id
  LEFT JOIN avaliacao av
    ON av.age_id = a.age_id
   AND av.cli_id = c.cli_id
  LEFT JOIN notificacao n
    ON n.age_id = a.age_id
   AND n.cli_id = c.cli_id
  WHERE c.cli_nome = p_nome
  ORDER BY a.age_data;
END $$

-- 4.4) Prestador - dados pessoais (prestador + endereço)
CREATE PROCEDURE pesquisar_prestador_dados_pessoais(
  IN p_nome VARCHAR(80)
)
BEGIN
  SELECT
    p.pre_id,
    p.pre_nome,
    p.pre_cpf,
    p.pre_nascimento,
    p.pre_areas,
    p.pre_experiencia,
    p.pre_certificados,
    p.pre_email,
    p.pre_telefone,
    e.end_id,
    e.end_logradouro,
    e.end_numero,
    e.end_complemento,
    e.end_bairro,
    e.end_cidade,
    e.end_uf,
    e.end_cep
  FROM prestador p
  JOIN endereco e
    ON e.end_id = p.end_id
  WHERE p.pre_nome = p_nome
  ORDER BY p.pre_nome;
END $$

-- 4.5) Prestador - visão completa (pessoais + agendamentos, serviços, pagamentos, disputas, avaliações)
CREATE PROCEDURE pesquisar_prestador_dados_completos(
  IN p_nome VARCHAR(80)
)
BEGIN
  SELECT
    p.pre_id,
    p.pre_nome,
    p.pre_cpf,
    p.pre_nascimento,
    p.pre_areas,
    p.pre_experiencia,
    p.pre_certificados,
    p.pre_email,
    p.pre_telefone,
    e.end_logradouro,
    e.end_numero,
    e.end_complemento,
    e.end_bairro,
    e.end_cidade,
    e.end_uf,
    e.end_cep,
    a.age_id,
    a.age_data,
    a.age_janela,
    a.age_status,
    a.age_valor,
    a.age_pago,
    s.ser_id,
    s.ser_nome,
    s.ser_descricao,
    s.ser_preco_base,
    c.cli_id,
    c.cli_nome,
    c.cli_email,
    c.cli_telefone,
    pag.pag_id,
    pag.pag_forma,
    pag.pag_valor,
    pag.pag_status,
    pag.pag_ref,
    pag.pag_data,
    d.dsp_id,
    d.dsp_status,
    d.dsp_motivo,
    d.dsp_valor,
    d.dsp_abertura,
    d.dsp_fechamento,
    av.ava_id,
    av.ava_nota,
    av.ava_coment,
    av.ava_data
  FROM prestador p
  JOIN endereco e
    ON e.end_id = p.end_id
  LEFT JOIN agendamento_servico a
    ON a.pre_id = p.pre_id
  LEFT JOIN servico s
    ON s.ser_id = a.ser_id
  LEFT JOIN cliente c
    ON c.cli_id = a.cli_id
  LEFT JOIN pagamento pag
    ON pag.age_id = a.age_id
  LEFT JOIN disputa_reembolso d
    ON d.age_id = a.age_id
   AND d.pre_id = p.pre_id
  LEFT JOIN avaliacao av
    ON av.age_id = a.age_id
   AND av.pre_id = p.pre_id
  WHERE p.pre_nome = p_nome
  ORDER BY a.age_data;
END $$

-- 4.6) Prestador - só agendamentos, disputas, pagamentos, avaliações, notificações
CREATE PROCEDURE pesquisar_prestador_agendamentos_disputas(
  IN p_nome VARCHAR(80)
)
BEGIN
  SELECT
    p.pre_id,
    p.pre_nome,
    a.age_id,
    a.age_data,
    a.age_janela,
    a.age_status,
    a.age_valor,
    a.age_pago,
    a.age_data_cancel,
    a.age_motivo,
    s.ser_id,
    s.ser_nome,
    s.ser_descricao,
    s.ser_preco_base,
    c.cli_id,
    c.cli_nome,
    c.cli_email,
    c.cli_telefone,
    pag.pag_id,
    pag.pag_forma,
    pag.pag_valor,
    pag.pag_status,
    pag.pag_ref,
    pag.pag_data,
    d.dsp_id,
    d.dsp_status,
    d.dsp_motivo,
    d.dsp_valor,
    d.dsp_abertura,
    d.dsp_fechamento,
    av.ava_id,
    av.ava_nota,
    av.ava_coment,
    av.ava_data,
    n.not_id,
    n.not_tipo,
    n.not_msg,
    n.not_enviado,
    n.not_data
  FROM prestador p
  LEFT JOIN agendamento_servico a
    ON a.pre_id = p.pre_id
  LEFT JOIN servico s
    ON s.ser_id = a.ser_id
  LEFT JOIN cliente c
    ON c.cli_id = a.cli_id
  LEFT JOIN pagamento pag
    ON pag.age_id = a.age_id
  LEFT JOIN disputa_reembolso d
    ON d.age_id = a.age_id
   AND d.pre_id = p.pre_id
  LEFT JOIN avaliacao av
    ON av.age_id = a.age_id
   AND av.pre_id = p.pre_id
  LEFT JOIN notificacao n
    ON n.age_id = a.age_id
   AND n.pre_id = p.pre_id
  WHERE p.pre_nome = p_nome
  ORDER BY a.age_data;
END $$

-- 4.7) listar_agendamentos_por_id_de_cliente
CREATE PROCEDURE listar_agendamentos_por_id_de_cliente(
  IN p_cli_id INT
)
BEGIN
  SELECT
    agendamento_servico.age_id,
    agendamento_servico.age_data,
    agendamento_servico.age_status,
    servico.ser_nome,
    agendamento_servico.age_valor
  FROM agendamento_servico
  JOIN servico USING (ser_id)
  WHERE agendamento_servico.cli_id = p_cli_id
  ORDER BY agendamento_servico.age_data;
END $$

-- 4.8) inserir_agendamento_de_servico
CREATE PROCEDURE inserir_agendamento_de_servico(
  IN p_cli_id INT,
  IN p_ser_id INT,
  IN p_pre_id INT,
  IN p_data DATE,
  IN p_periodo VARCHAR(20),
  IN p_end_id INT,
  IN p_status_inicial VARCHAR(20),
  IN p_valor FLOAT
)
BEGIN
  INSERT INTO agendamento_servico
    (cli_id, ser_id, pre_id,
     age_data, age_janela, end_id, age_status, age_valor, age_pago)
  VALUES
    (p_cli_id, p_ser_id, p_pre_id,
     p_data, p_periodo, p_end_id, p_status_inicial, p_valor, 0);
END $$

-- 4.9) cancelar_agendamento_de_servico
CREATE PROCEDURE cancelar_agendamento_de_servico(
  IN p_age_id INT,
  IN p_motivo VARCHAR(120)
)
BEGIN
  UPDATE agendamento_servico
     SET age_status = 'Cancelado',
         age_data_cancel = CURDATE(),
         age_motivo = p_motivo
   WHERE age_id = p_age_id
     AND age_status != 'Cancelado';
END $$

-- 4.10) registrar_avaliacao_de_prestador
CREATE PROCEDURE registrar_avaliacao_de_prestador(
  IN p_age_id INT,
  IN p_cli_id INT,
  IN p_pre_id INT,
  IN p_nota INT,
  IN p_coment VARCHAR(400)
)
BEGIN
  INSERT INTO avaliacao
    (age_id, cli_id, pre_id, ava_nota, ava_coment, ava_data)
  VALUES
    (p_age_id, p_cli_id, p_pre_id, p_nota, p_coment, CURDATE());
END $$

-- 4.11) inserir_cliente
CREATE PROCEDURE inserir_cliente(
  IN p_nome VARCHAR(80),
  IN p_cpf VARCHAR(14),
  IN p_nasc DATE,
  IN p_senha VARCHAR(60),
  IN p_end_id INT,
  IN p_email VARCHAR(120),
  IN p_telefone VARCHAR(20)
)
BEGIN
  INSERT INTO cliente
    (cli_nome, cli_cpf, cli_nascimento,
     cli_senha, end_id, cli_email, cli_telefone)
  VALUES
    (p_nome, p_cpf, p_nasc, p_senha, p_end_id, p_email, p_telefone);
END $$

-- 4.12) inserir_prestador
CREATE PROCEDURE inserir_prestador(
  IN p_nome VARCHAR(80),
  IN p_cpf VARCHAR(14),
  IN p_nasc DATE,
  IN p_areas VARCHAR(120),
  IN p_exp VARCHAR(255),
  IN p_certs VARCHAR(255),
  IN p_senha VARCHAR(60),
  IN p_end_id INT,
  IN p_email VARCHAR(120),
  IN p_telefone VARCHAR(20)
)
BEGIN
  INSERT INTO prestador
    (pre_nome, pre_cpf, pre_nascimento,
     pre_areas, pre_experiencia, pre_certificados,
     pre_senha, end_id, pre_email, pre_telefone)
  VALUES
    (p_nome, p_cpf, p_nasc,
     p_areas, p_exp, p_certs,
     p_senha, p_end_id, p_email, p_telefone);
END $$

-- 4.13) listar_agendamentos_por_periodo_e_status
CREATE PROCEDURE listar_agendamentos_por_periodo_e_status(
  IN p_ini DATE,
  IN p_fim DATE,
  IN p_status VARCHAR(20)
)
BEGIN
  SELECT
    agendamento_servico.age_id,
    agendamento_servico.age_data,
    agendamento_servico.age_status,
    servico.ser_nome,
    cliente.cli_nome
  FROM agendamento_servico
  JOIN servico USING (ser_id)
  JOIN cliente USING (cli_id)
  WHERE agendamento_servico.age_data BETWEEN p_ini AND p_fim
    AND agendamento_servico.age_status = p_status
  ORDER BY agendamento_servico.age_data;
END $$

-- 4.14) abrir_disputa
CREATE PROCEDURE abrir_disputa(
  IN p_age_id INT,
  IN p_cli_id INT,
  IN p_pre_id INT,
  IN p_motivo VARCHAR(255),
  IN p_valor FLOAT
)
BEGIN
  INSERT INTO disputa_reembolso
    (age_id, cli_id, pre_id, dsp_motivo, dsp_status,
     dsp_valor, dsp_abertura)
  VALUES
    (p_age_id, p_cli_id, p_pre_id, p_motivo, 'Aberta',
     p_valor, CURDATE());
END $$

-- 4.15) fechar_disputa
CREATE PROCEDURE fechar_disputa(
  IN p_dsp_id INT
)
BEGIN
  UPDATE disputa_reembolso
     SET dsp_status = 'Resolvida',
         dsp_fechamento = CURDATE()
   WHERE dsp_id = p_dsp_id
     AND dsp_status != 'Resolvida';
END $$

-- 4.16) inserir_pagamento
CREATE PROCEDURE inserir_pagamento(
  IN p_age_id INT,
  IN p_forma VARCHAR(20),
  IN p_valor FLOAT,
  IN p_status VARCHAR(20),
  IN p_ref VARCHAR(60),
  IN p_data DATE
)
BEGIN
  INSERT INTO pagamento
    (age_id, pag_forma, pag_valor, pag_status, pag_ref, pag_data)
  VALUES
    (p_age_id, p_forma, p_valor, p_status, p_ref, p_data);
END $$

DELIMITER ;

-- =========================================================
-- 5) TRIGGERS
-- =========================================================

DROP TRIGGER IF EXISTS trigger_pos_inserir_agendamento_registrar_status_inicial;
DROP TRIGGER IF EXISTS trigger_pos_atualizar_agendamento_registrar_mudanca_de_status;
DROP TRIGGER IF EXISTS trigger_pos_inserir_avaliacao_criar_notificacao;
DROP TRIGGER IF EXISTS trigger_pos_inserir_pagamento_confirmado_criar_notificacao;
DROP TRIGGER IF EXISTS trigger_pos_inserir_agendamento_notificar_prestador;
DROP TRIGGER IF EXISTS trigger_pos_inserir_disputa_aberta_criar_notificacao;

DELIMITER $$

-- 5.1) Histórico inicial ao criar agendamento
CREATE TRIGGER trigger_pos_inserir_agendamento_registrar_status_inicial
AFTER INSERT ON agendamento_servico
FOR EACH ROW
BEGIN
  INSERT INTO historico_status_agendamento
    (age_id, his_status_ant, his_status_novo, his_data)
  VALUES
    (NEW.age_id, 'Criado', NEW.age_status, CURDATE());
END $$

-- 5.2) Histórico ao mudar status do agendamento
CREATE TRIGGER trigger_pos_atualizar_agendamento_registrar_mudanca_de_status
AFTER UPDATE ON agendamento_servico
FOR EACH ROW
BEGIN
  IF OLD.age_status != NEW.age_status THEN
    INSERT INTO historico_status_agendamento
      (age_id, his_status_ant, his_status_novo, his_data)
    VALUES
      (NEW.age_id, OLD.age_status, NEW.age_status, CURDATE());
  END IF;
END $$

-- 5.3) Notificação após nova avaliação
CREATE TRIGGER trigger_pos_inserir_avaliacao_criar_notificacao
AFTER INSERT ON avaliacao
FOR EACH ROW
BEGIN
  IF NEW.pre_id IS NOT NULL THEN
    INSERT INTO notificacao
      (cli_id, pre_id, age_id, not_tipo, not_msg, not_enviado, not_data)
    VALUES
      (NEW.cli_id, NEW.pre_id, NEW.age_id,
       'Avaliacao', 'Nova avaliacao registrada para prestador.', 0, CURDATE());
  END IF;
END $$

-- 5.4) Notificação de pagamento confirmado
CREATE TRIGGER trigger_pos_inserir_pagamento_confirmado_criar_notificacao
AFTER INSERT ON pagamento
FOR EACH ROW
BEGIN
  IF NEW.pag_status = 'Pago' THEN
    INSERT INTO notificacao
      (cli_id, pre_id, age_id, not_tipo, not_msg, not_enviado, not_data)
    VALUES
      (
        (SELECT cli_id FROM agendamento_servico WHERE age_id = NEW.age_id),
        (SELECT pre_id FROM agendamento_servico WHERE age_id = NEW.age_id),
        NEW.age_id,
        'Pagamento',
        'Pagamento confirmado.',
        0,
        CURDATE()
      );
  END IF;
END $$

-- 5.5) Notificação de novo agendamento para o prestador
CREATE TRIGGER trigger_pos_inserir_agendamento_notificar_prestador
AFTER INSERT ON agendamento_servico
FOR EACH ROW
BEGIN
  IF NEW.pre_id IS NOT NULL THEN
    INSERT INTO notificacao
      (pre_id, age_id, not_tipo, not_msg, not_enviado, not_data)
    VALUES
      (NEW.pre_id, NEW.age_id,
       'Agendamento', 'Novo agendamento atribuído ao prestador.', 0, CURDATE());
  END IF;
END $$

-- 5.6) Notificações ao abrir disputa
CREATE TRIGGER trigger_pos_inserir_disputa_aberta_criar_notificacao
AFTER INSERT ON disputa_reembolso
FOR EACH ROW
BEGIN
  IF NEW.dsp_status = 'Aberta' THEN

    IF NEW.cli_id IS NOT NULL THEN
      INSERT INTO notificacao
        (cli_id, age_id, not_tipo, not_msg, not_enviado, not_data)
      VALUES
        (NEW.cli_id, NEW.age_id,
         'Disputa', 'Sua disputa foi aberta.', 0, CURDATE());
    END IF;

    IF NEW.pre_id IS NOT NULL THEN
      INSERT INTO notificacao
        (pre_id, age_id, not_tipo, not_msg, not_enviado, not_data)
      VALUES
        (NEW.pre_id, NEW.age_id,
         'Disputa', 'Uma disputa foi aberta neste agendamento.', 0, CURDATE());
    END IF;

  END IF;
END $$

DELIMITER ;
