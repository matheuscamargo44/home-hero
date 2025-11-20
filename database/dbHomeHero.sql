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
  pre_id INT NOT NULL,
  age_data DATE,
  age_janela VARCHAR(20),
  end_id INT NOT NULL,
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
  pre_id INT NOT NULL,
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

-- 2.16) chat_mensagem
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

-- 2.17) chatbot_log
CREATE TABLE chatbot_log (
  cbt_id INT PRIMARY KEY AUTO_INCREMENT,
  cli_id INT,
  pre_id INT,
  age_id INT,
  cbt_pergunta VARCHAR(300),
  cbt_resposta VARCHAR(500),
  cbt_data DATE,
  FOREIGN KEY (cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (pre_id) REFERENCES prestador(pre_id),
  FOREIGN KEY (age_id) REFERENCES agendamento_servico(age_id)
);

-- 2.18) historico_status_agendamento
CREATE TABLE historico_status_agendamento (
  his_id INT PRIMARY KEY AUTO_INCREMENT,
  age_id INT NOT NULL,
  his_status_ant VARCHAR(20),
  his_status_novo VARCHAR(20),
  his_data DATE,
  FOREIGN KEY (age_id) REFERENCES agendamento_servico(age_id)
);

-- 2.19) admin
CREATE TABLE admin (
  adm_id INT PRIMARY KEY AUTO_INCREMENT,
  adm_nome VARCHAR(80) NOT NULL,
  adm_email VARCHAR(120) NOT NULL UNIQUE,
  adm_senha VARCHAR(60) NOT NULL
);

USE homehero;

-- =========================================================
-- 3) VIEWS
-- =========================================================

-- 3.1) view_dados_de_clientes
CREATE OR REPLACE VIEW view_dados_de_clientes
AS
SELECT
  cli_id          AS 'ID do cliente',
  cli_nome        AS 'Nome do cliente',
  cli_cpf         AS 'CPF do cliente',
  cli_nascimento  AS 'Data de nascimento',
  cli_email       AS 'E-mail do cliente',
  cli_telefone    AS 'Telefone do cliente',
  cli_senha       AS 'Senha',
  cliente.end_id  AS 'ID do endereço',
  end_logradouro  AS 'Logradouro',
  end_numero      AS 'Número',
  end_complemento AS 'Complemento',
  end_bairro      AS 'Bairro',
  end_cidade      AS 'Cidade',
  end_uf          AS 'UF',
  end_cep         AS 'CEP'
FROM cliente
JOIN endereco USING (end_id)
ORDER BY cli_nome;

-- 3.2) view_dados_de_prestadores
CREATE OR REPLACE VIEW view_dados_de_prestadores
AS
SELECT
  pre_id          AS 'ID do prestador',
  pre_nome        AS 'Nome do prestador',
  pre_cpf         AS 'CPF do prestador',
  pre_nascimento  AS 'Data de nascimento',
  pre_areas       AS 'Áreas de atuação',
  pre_experiencia AS 'Experiência',
  pre_certificados AS 'Certificados',
  pre_email       AS 'E-mail do prestador',
  pre_telefone    AS 'Telefone do prestador',
  pre_senha       AS 'Senha',
  prestador.end_id AS 'ID do endereço',
  end_logradouro  AS 'Logradouro',
  end_numero      AS 'Número',
  end_complemento AS 'Complemento',
  end_bairro      AS 'Bairro',
  end_cidade      AS 'Cidade',
  end_uf          AS 'UF',
  end_cep         AS 'CEP'
FROM prestador
JOIN endereco USING (end_id)
ORDER BY pre_nome;

-- 3.3) view_servicos_oferecidos_por_prestadores
CREATE OR REPLACE VIEW view_servicos_oferecidos_por_prestadores
AS
SELECT
  pre_id           AS 'ID do prestador',
  pre_nome         AS 'Nome do prestador',
  pre_email        AS 'E-mail do prestador',
  pre_telefone     AS 'Telefone do prestador',
  ser_id           AS 'ID do serviço',
  ser_nome         AS 'Nome do serviço',
  ser_descricao    AS 'Descrição do serviço',
  ser_preco_base   AS 'Preço base do serviço',
  ser_ativo        AS 'Serviço ativo',
  cat_id           AS 'ID da categoria',
  cat_nome         AS 'Nome da categoria',
  cat_descricao    AS 'Descrição da categoria',
  prs_id           AS 'ID da oferta',
  prs_preco        AS 'Preço ofertado',
  prs_ativo        AS 'Oferta ativa',
  prs_data         AS 'Data de cadastro da oferta'
FROM prestador
JOIN prestador_servico USING (pre_id)
JOIN servico USING (ser_id)
JOIN categoria_servico USING (cat_id)
ORDER BY pre_nome, ser_nome;

-- 3.4) view_detalhes_de_agendamentos
CREATE OR REPLACE VIEW view_detalhes_de_agendamentos
AS
SELECT
  age_id                 AS 'ID do agendamento',
  agendamento_servico.cli_id AS 'ID do cliente',
  agendamento_servico.ser_id AS 'ID do serviço',
  agendamento_servico.pre_id AS 'ID do prestador',
  age_data               AS 'Data do agendamento',
  age_janela             AS 'Período do agendamento',
  agendamento_servico.end_id AS 'ID do endereço do agendamento',
  age_status             AS 'Status do agendamento',
  age_valor              AS 'Valor do agendamento',
  age_pago               AS 'Pagamento confirmado',
  age_data_cancel        AS 'Data de cancelamento',
  age_motivo             AS 'Motivo do cancelamento',
  cli_nome               AS 'Nome do cliente',
  cli_cpf                AS 'CPF do cliente',
  cli_email              AS 'E-mail do cliente',
  cli_telefone           AS 'Telefone do cliente',
  prestador.pre_nome     AS 'Nome do prestador',
  prestador.pre_email    AS 'E-mail do prestador',
  prestador.pre_telefone AS 'Telefone do prestador',
  ser_nome               AS 'Nome do serviço',
  ser_descricao          AS 'Descrição do serviço',
  ser_preco_base         AS 'Preço base do serviço',
  end_logradouro         AS 'Logradouro',
  end_numero             AS 'Número',
  end_complemento        AS 'Complemento',
  end_bairro             AS 'Bairro',
  end_cidade             AS 'Cidade',
  end_uf                 AS 'UF',
  end_cep                AS 'CEP',
  pag_id                 AS 'ID do pagamento',
  pag_forma              AS 'Forma de pagamento',
  pag_status             AS 'Status do pagamento',
  pag_valor              AS 'Valor pago',
  pag_data               AS 'Data do pagamento'
FROM agendamento_servico
JOIN cliente USING (cli_id)
JOIN servico USING (ser_id)
JOIN prestador USING (pre_id)
JOIN endereco ON endereco.end_id = agendamento_servico.end_id
LEFT JOIN pagamento USING (age_id)
ORDER BY age_data;

-- 3.5) view_media_de_avaliacao_por_prestador
CREATE OR REPLACE VIEW view_media_de_avaliacao_por_prestador
AS
SELECT
  prestador.pre_id  AS 'ID do prestador',
  prestador.pre_nome AS 'Nome do prestador',
  prestador.pre_email AS 'E-mail do prestador',
  COUNT(*)          AS 'Quantidade de avaliações',
  AVG(ava_nota)     AS 'Média das notas',
  MIN(ava_nota)     AS 'Nota mínima',
  MAX(ava_nota)     AS 'Nota máxima',
  MIN(ava_data)     AS 'Primeira avaliação',
  MAX(ava_data)     AS 'Última avaliação'
FROM avaliacao
JOIN prestador USING (pre_id)
GROUP BY
  prestador.pre_id,
  prestador.pre_nome,
  prestador.pre_email
ORDER BY AVG(ava_nota) DESC;

-- 3.6) view_agendamentos_por_status
CREATE OR REPLACE VIEW view_agendamentos_por_status
AS
SELECT
  age_status   AS 'Status do agendamento',
  COUNT(*)     AS 'Total de agendamentos',
  SUM(age_valor) AS 'Soma dos valores',
  AVG(age_valor) AS 'Valor médio'
FROM agendamento_servico
GROUP BY age_status
ORDER BY COUNT(*) DESC;

-- 3.7) view_agendamentos_por_periodo
CREATE OR REPLACE VIEW view_agendamentos_por_periodo
AS
SELECT
  age_janela   AS 'Período do agendamento',
  COUNT(*)     AS 'Total de agendamentos',
  SUM(age_valor) AS 'Soma dos valores',
  AVG(age_valor) AS 'Valor médio'
FROM agendamento_servico
GROUP BY age_janela
ORDER BY COUNT(*) DESC;

-- 3.8) view_agendamentos_por_regiao_cliente
CREATE OR REPLACE VIEW view_agendamentos_por_regiao_cliente
AS
SELECT
  regiao.reg_id     AS 'ID da região',
  regiao.reg_nome   AS 'Nome da região',
  regiao.reg_cidade AS 'Cidade',
  regiao.reg_uf     AS 'UF',
  COUNT(age_id)     AS 'Total de agendamentos',
  SUM(age_valor)    AS 'Soma dos valores',
  AVG(age_valor)    AS 'Valor médio'
FROM agendamento_servico
JOIN cliente USING (cli_id)
JOIN registro_regiao USING (cli_id)
JOIN regiao USING (reg_id)
GROUP BY
  regiao.reg_id,
  regiao.reg_nome,
  regiao.reg_cidade,
  regiao.reg_uf
ORDER BY COUNT(age_id) DESC;

-- 3.9) view_pagamentos_por_status
CREATE OR REPLACE VIEW view_pagamentos_por_status
AS
SELECT
  pag_status     AS 'Status do pagamento',
  COUNT(*)       AS 'Quantidade de pagamentos',
  SUM(pag_valor) AS 'Soma dos valores pagos',
  AVG(pag_valor) AS 'Ticket médio',
  MIN(pag_valor) AS 'Valor mínimo',
  MAX(pag_valor) AS 'Valor máximo'
FROM pagamento
GROUP BY pag_status
ORDER BY COUNT(*) DESC;

-- 3.10) view_disputas_abertas
CREATE OR REPLACE VIEW view_disputas_abertas
AS
SELECT
  dsp_id              AS 'ID da disputa',
  disputa_reembolso.age_id AS 'ID do agendamento',
  disputa_reembolso.cli_id AS 'ID do cliente',
  disputa_reembolso.pre_id AS 'ID do prestador',
  dsp_status          AS 'Status da disputa',
  dsp_motivo          AS 'Motivo da disputa',
  dsp_valor           AS 'Valor a reembolsar',
  dsp_abertura        AS 'Data de abertura',
  dsp_fechamento      AS 'Data de fechamento',
  cli_nome            AS 'Nome do cliente',
  cli_email           AS 'E-mail do cliente',
  pre_nome            AS 'Nome do prestador',
  pre_email           AS 'E-mail do prestador',
  age_data            AS 'Data do agendamento',
  age_status          AS 'Status do agendamento',
  age_valor           AS 'Valor do agendamento',
  ser_nome            AS 'Nome do serviço'
FROM disputa_reembolso
LEFT JOIN cliente USING (cli_id)
LEFT JOIN prestador USING (pre_id)
JOIN agendamento_servico USING (age_id)
JOIN servico USING (ser_id)
WHERE dsp_status != 'Resolvida'
ORDER BY dsp_abertura DESC;

-- 3.11) view_disponibilidade_de_prestadores
CREATE OR REPLACE VIEW view_disponibilidade_de_prestadores
AS
SELECT
  pre_id          AS 'ID do prestador',
  pre_nome        AS 'Nome do prestador',
  pre_cpf         AS 'CPF do prestador',
  pre_email       AS 'E-mail do prestador',
  pre_telefone    AS 'Telefone do prestador',
  pre_areas       AS 'Áreas de atuação',
  dis_id          AS 'ID da disponibilidade',
  dis_dia         AS 'Dia da semana',
  dis_janela      AS 'Janela de atendimento',
  dis_ativo       AS 'Disponibilidade ativa',
  end_cidade      AS 'Cidade',
  end_uf          AS 'UF'
FROM prestador
JOIN disponibilidade_prestador USING (pre_id)
JOIN endereco USING (end_id)
ORDER BY pre_nome, dis_dia, dis_janela;

-- 3.12) view_agendamentos_completo
CREATE OR REPLACE VIEW view_agendamentos_completo
AS
SELECT
  age_id                    AS 'ID do agendamento',
  agendamento_servico.cli_id AS 'ID do cliente',
  agendamento_servico.ser_id AS 'ID do serviço',
  agendamento_servico.pre_id AS 'ID do prestador',
  age_data                  AS 'Data do agendamento',
  age_janela                AS 'Período do agendamento',
  agendamento_servico.end_id AS 'ID do endereço do agendamento',
  age_status                AS 'Status do agendamento',
  age_valor                 AS 'Valor do agendamento',
  age_pago                  AS 'Pagamento confirmado',
  age_data_cancel           AS 'Data de cancelamento',
  age_motivo                AS 'Motivo do cancelamento',
  cli_nome                  AS 'Nome do cliente',
  cli_cpf                   AS 'CPF do cliente',
  cli_nascimento            AS 'Data de nascimento do cliente',
  cli_email                 AS 'E-mail do cliente',
  cli_telefone              AS 'Telefone do cliente',
  prestador.pre_nome        AS 'Nome do prestador',
  prestador.pre_cpf         AS 'CPF do prestador',
  prestador.pre_nascimento  AS 'Data de nascimento do prestador',
  prestador.pre_areas       AS 'Áreas de atuação do prestador',
  prestador.pre_experiencia AS 'Experiência do prestador',
  prestador.pre_email       AS 'E-mail do prestador',
  prestador.pre_telefone    AS 'Telefone do prestador',
  ser_nome                  AS 'Nome do serviço',
  ser_descricao             AS 'Descrição do serviço',
  ser_preco_base            AS 'Preço base do serviço',
  ser_ativo                 AS 'Serviço ativo',
  end_logradouro            AS 'Logradouro do endereço',
  end_numero                AS 'Número do endereço',
  end_complemento           AS 'Complemento do endereço',
  end_bairro                AS 'Bairro do endereço',
  end_cidade                AS 'Cidade do endereço',
  end_uf                    AS 'UF do endereço',
  end_cep                   AS 'CEP do endereço'
FROM agendamento_servico
JOIN cliente USING (cli_id)
JOIN servico USING (ser_id)
JOIN prestador USING (pre_id)
JOIN endereco ON endereco.end_id = agendamento_servico.end_id;

-- 3.13) view_prestadores_completo
CREATE OR REPLACE VIEW view_prestadores_completo
AS
SELECT
  pre_id             AS 'ID do prestador',
  pre_nome           AS 'Nome do prestador',
  pre_cpf            AS 'CPF do prestador',
  pre_nascimento     AS 'Data de nascimento',
  pre_areas          AS 'Áreas de atuação',
  pre_experiencia    AS 'Experiência',
  pre_certificados   AS 'Certificados',
  pre_email          AS 'E-mail do prestador',
  pre_telefone       AS 'Telefone do prestador',
  prestador.end_id   AS 'ID do endereço',
  end_logradouro     AS 'Logradouro',
  end_numero         AS 'Número',
  end_complemento    AS 'Complemento',
  end_bairro         AS 'Bairro',
  end_cidade         AS 'Cidade',
  end_uf             AS 'UF',
  end_cep            AS 'CEP',
  dis_id             AS 'ID da disponibilidade',
  dis_dia            AS 'Dia da semana',
  dis_janela         AS 'Janela de atendimento',
  dis_ativo          AS 'Disponibilidade ativa',
  cer_id             AS 'ID da certificação',
  cer_titulo         AS 'Título da certificação',
  cer_instituicao    AS 'Instituição da certificação',
  cer_data           AS 'Data da certificação',
  cer_url            AS 'URL do certificado'
FROM prestador
JOIN endereco USING (end_id)
LEFT JOIN disponibilidade_prestador USING (pre_id)
LEFT JOIN certificacao_prestador   USING (pre_id);

-- 3.14) view_avaliacoes_detalhadas
CREATE OR REPLACE VIEW view_avaliacoes_detalhadas
AS
SELECT
  ava_id              AS 'ID da avaliação',
  avaliacao.age_id    AS 'ID do agendamento',
  avaliacao.cli_id    AS 'ID do cliente',
  avaliacao.pre_id    AS 'ID do prestador',
  ava_nota            AS 'Nota',
  ava_coment          AS 'Comentário',
  ava_data            AS 'Data da avaliação',
  cli_nome            AS 'Nome do cliente',
  cli_cpf             AS 'CPF do cliente',
  cli_email           AS 'E-mail do cliente',
  cli_telefone        AS 'Telefone do cliente',
  pre_nome            AS 'Nome do prestador',
  pre_cpf             AS 'CPF do prestador',
  pre_email           AS 'E-mail do prestador',
  pre_telefone        AS 'Telefone do prestador',
  servico.ser_id      AS 'ID do serviço',
  ser_nome            AS 'Nome do serviço',
  ser_descricao       AS 'Descrição do serviço',
  ser_preco_base      AS 'Preço base do serviço',
  age_data            AS 'Data do agendamento',
  age_status          AS 'Status do agendamento',
  age_valor           AS 'Valor do agendamento',
  age_janela          AS 'Período do agendamento',
  cat_nome            AS 'Nome da categoria'
FROM avaliacao
JOIN cliente USING (cli_id)
JOIN prestador USING (pre_id)
JOIN agendamento_servico USING (age_id)
JOIN servico USING (ser_id)
LEFT JOIN categoria_servico USING (cat_id);

-- 3.15) view_pagamentos_detalhados
CREATE OR REPLACE VIEW view_pagamentos_detalhados
AS
SELECT
  pag_id              AS 'ID do pagamento',
  pagamento.age_id    AS 'ID do agendamento',
  pag_forma           AS 'Forma de pagamento',
  pag_valor           AS 'Valor do pagamento',
  pag_status          AS 'Status do pagamento',
  pag_ref             AS 'Referência do gateway',
  pag_data            AS 'Data do pagamento',
  age_data            AS 'Data do agendamento',
  age_status          AS 'Status do agendamento',
  age_valor           AS 'Valor do agendamento',
  age_pago            AS 'Pago no agendamento',
  age_janela          AS 'Período do agendamento',
  cliente.cli_id      AS 'ID do cliente',
  cli_nome            AS 'Nome do cliente',
  cli_cpf             AS 'CPF do cliente',
  cli_email           AS 'E-mail do cliente',
  cli_telefone        AS 'Telefone do cliente',
  prestador.pre_id    AS 'ID do prestador',
  pre_nome            AS 'Nome do prestador',
  pre_cpf             AS 'CPF do prestador',
  pre_email           AS 'E-mail do prestador',
  pre_telefone        AS 'Telefone do prestador',
  servico.ser_id      AS 'ID do serviço',
  ser_nome            AS 'Nome do serviço',
  ser_descricao       AS 'Descrição do serviço',
  ser_preco_base      AS 'Preço base do serviço',
  cat_nome            AS 'Nome da categoria',
  end_cidade          AS 'Cidade',
  end_uf              AS 'UF'
FROM pagamento
JOIN agendamento_servico USING (age_id)
JOIN cliente USING (cli_id)
JOIN prestador USING (pre_id)
JOIN servico USING (ser_id)
LEFT JOIN categoria_servico USING (cat_id)
JOIN endereco ON endereco.end_id = agendamento_servico.end_id;

-- 3.16) view_disputas_com_historico
CREATE OR REPLACE VIEW view_disputas_com_historico
AS
SELECT
  dsp_id                   AS 'ID da disputa',
  disputa_reembolso.age_id AS 'ID do agendamento',
  disputa_reembolso.cli_id AS 'ID do cliente',
  disputa_reembolso.pre_id AS 'ID do prestador',
  dsp_status               AS 'Status da disputa',
  dsp_motivo               AS 'Motivo da disputa',
  dsp_valor                AS 'Valor em disputa',
  dsp_abertura             AS 'Data de abertura',
  dsp_fechamento           AS 'Data de fechamento',
  cli_nome                 AS 'Nome do cliente',
  cli_email                AS 'E-mail do cliente',
  pre_nome                 AS 'Nome do prestador',
  pre_email                AS 'E-mail do prestador',
  age_data                 AS 'Data do agendamento',
  age_status               AS 'Status do agendamento',
  age_valor                AS 'Valor do agendamento',
  ser_nome                 AS 'Nome do serviço',
  his_id                   AS 'ID do histórico',
  his_status_ant           AS 'Status anterior',
  his_status_novo          AS 'Status novo',
  his_data                 AS 'Data da alteração',
  not_id                   AS 'ID da notificação',
  not_tipo                 AS 'Tipo de notificação',
  not_msg                  AS 'Mensagem da notificação',
  not_data                 AS 'Data da notificação'
FROM disputa_reembolso
JOIN agendamento_servico USING (age_id)
LEFT JOIN cliente ON cliente.cli_id = disputa_reembolso.cli_id
LEFT JOIN prestador ON prestador.pre_id = disputa_reembolso.pre_id
JOIN servico USING (ser_id)
LEFT JOIN historico_status_agendamento
       ON historico_status_agendamento.age_id = disputa_reembolso.age_id
LEFT JOIN notificacao
       ON notificacao.age_id = disputa_reembolso.age_id
      AND notificacao.not_tipo = 'Disputa';

-- 3.17) view_notificacoes_detalhadas
CREATE OR REPLACE VIEW view_notificacoes_detalhadas
AS
SELECT
  not_id              AS 'ID da notificação',
  not_tipo            AS 'Tipo da notificação',
  not_msg             AS 'Mensagem',
  not_enviado         AS 'Enviado',
  not_data            AS 'Data da notificação',
  notificacao.cli_id  AS 'ID do cliente',
  cli_nome            AS 'Nome do cliente',
  cli_email           AS 'E-mail do cliente',
  cli_telefone        AS 'Telefone do cliente',
  notificacao.pre_id  AS 'ID do prestador',
  pre_nome            AS 'Nome do prestador',
  pre_email           AS 'E-mail do prestador',
  pre_telefone        AS 'Telefone do prestador',
  notificacao.age_id  AS 'ID do agendamento',
  age_data            AS 'Data do agendamento',
  age_status          AS 'Status do agendamento',
  age_valor           AS 'Valor do agendamento',
  ser_nome            AS 'Nome do serviço',
  ser_descricao       AS 'Descrição do serviço'
FROM notificacao
LEFT JOIN cliente ON cliente.cli_id = notificacao.cli_id
LEFT JOIN prestador ON prestador.pre_id = notificacao.pre_id
LEFT JOIN agendamento_servico USING (age_id)
LEFT JOIN servico USING (ser_id)
ORDER BY not_data DESC, not_id DESC;

-- 3.18) view_historico_status_completo
CREATE OR REPLACE VIEW view_historico_status_completo
AS
SELECT
  his_id                      AS 'ID do histórico',
  historico_status_agendamento.age_id AS 'ID do agendamento',
  his_status_ant              AS 'Status anterior',
  his_status_novo             AS 'Status novo',
  his_data                    AS 'Data da alteração',
  agendamento_servico.cli_id  AS 'ID do cliente',
  cli_nome                    AS 'Nome do cliente',
  cli_email                   AS 'E-mail do cliente',
  agendamento_servico.pre_id  AS 'ID do prestador',
  pre_nome                    AS 'Nome do prestador',
  pre_email                   AS 'E-mail do prestador',
  age_data                    AS 'Data do agendamento',
  age_janela                  AS 'Janela de horário',
  age_status                  AS 'Status atual',
  age_valor                   AS 'Valor do agendamento',
  servico.ser_id              AS 'ID do serviço',
  ser_nome                    AS 'Nome do serviço',
  ser_descricao               AS 'Descrição do serviço'
FROM historico_status_agendamento
JOIN agendamento_servico USING (age_id)
JOIN cliente ON cliente.cli_id = agendamento_servico.cli_id
JOIN prestador ON prestador.pre_id = agendamento_servico.pre_id
JOIN servico USING (ser_id)
ORDER BY his_data DESC, his_id DESC;

-- 3.19) view_receita_por_prestador
CREATE OR REPLACE VIEW view_receita_por_prestador
AS
SELECT
  prestador.pre_id        AS 'ID do prestador',
  prestador.pre_nome      AS 'Nome do prestador',
  prestador.pre_email     AS 'E-mail do prestador',
  prestador.pre_telefone  AS 'Telefone do prestador',
  COUNT(DISTINCT agendamento_servico.age_id) AS 'Total de agendamentos',
  COUNT(pagamento.pag_id) AS 'Total de pagamentos',
  SUM(pagamento.pag_valor) AS 'Receita total (R$)',
  AVG(pagamento.pag_valor) AS 'Ticket médio (R$)'
FROM prestador
LEFT JOIN agendamento_servico
       ON agendamento_servico.pre_id = prestador.pre_id
LEFT JOIN pagamento
       ON pagamento.age_id = agendamento_servico.age_id
GROUP BY
  prestador.pre_id,
  prestador.pre_nome,
  prestador.pre_email,
  prestador.pre_telefone
ORDER BY SUM(pagamento.pag_valor) DESC;

-- 3.20) view_receita_por_periodo
CREATE OR REPLACE VIEW view_receita_por_periodo
AS
SELECT
  DATE_FORMAT(pagamento.pag_data, '%Y-%m') AS 'Período (Ano-Mês)',
  YEAR(pagamento.pag_data)                AS 'Ano',
  MONTH(pagamento.pag_data)               AS 'Mês',
  COUNT(DISTINCT pagamento.pag_id)        AS 'Total de pagamentos',
  COUNT(DISTINCT agendamento_servico.age_id) AS 'Total de agendamentos',
  SUM(pagamento.pag_valor)                AS 'Receita total (R$)',
  AVG(pagamento.pag_valor)                AS 'Ticket médio (R$)',
  MIN(pagamento.pag_data)                 AS 'Primeira data',
  MAX(pagamento.pag_data)                 AS 'Última data'
FROM pagamento
LEFT JOIN agendamento_servico
       ON agendamento_servico.age_id = pagamento.age_id
GROUP BY
  DATE_FORMAT(pagamento.pag_data, '%Y-%m'),
  YEAR(pagamento.pag_data),
  MONTH(pagamento.pag_data)
ORDER BY
  YEAR(pagamento.pag_data) DESC,
  MONTH(pagamento.pag_data) DESC;

-- =========================================================
-- 4) PROCEDURES
-- =========================================================

DROP PROCEDURE IF EXISTS pesquisar_clientes_por_nome_exato;
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

-- 4.1) pesquisar_clientes_por_nome_exato
CREATE PROCEDURE pesquisar_clientes_por_nome_exato(
  IN p_nome VARCHAR(80)
)
BEGIN
  SELECT
    cli_id,
    cli_nome,
    cli_cpf
  FROM cliente
  WHERE cli_nome = p_nome
  ORDER BY cli_nome;
END $$

-- 4.2) listar_agendamentos_por_id_de_cliente
CREATE PROCEDURE listar_agendamentos_por_id_de_cliente(
  IN p_cli_id INT
)
BEGIN
  SELECT
    age_id,
    age_data,
    age_status,
    ser_nome,
    age_valor
  FROM agendamento_servico
  JOIN servico USING (ser_id)
  WHERE cli_id = p_cli_id
  ORDER BY age_data;
END $$

-- 4.3) inserir_agendamento_de_servico
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

-- 4.4) cancelar_agendamento_de_servico
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

-- 4.5) registrar_avaliacao_de_prestador
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

-- 4.6) inserir_cliente
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

-- 4.7) inserir_prestador
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

-- 4.8) listar_agendamentos_por_periodo_e_status
CREATE PROCEDURE listar_agendamentos_por_periodo_e_status(
  IN p_ini DATE,
  IN p_fim DATE,
  IN p_status VARCHAR(20)
)
BEGIN
  SELECT
    age_id,
    age_data,
    age_status,
    ser_nome,
    cli_nome
  FROM agendamento_servico
  JOIN servico USING (ser_id)
  JOIN cliente USING (cli_id)
  WHERE age_data BETWEEN p_ini AND p_fim
    AND age_status = p_status
  ORDER BY age_data;
END $$

-- 4.9) abrir_disputa
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

-- 4.10) fechar_disputa
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

-- 4.11) inserir_pagamento
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

-- 5.1) trigger_pos_inserir_agendamento_registrar_status_inicial
CREATE TRIGGER trigger_pos_inserir_agendamento_registrar_status_inicial
AFTER INSERT ON agendamento_servico
FOR EACH ROW
BEGIN
  INSERT INTO historico_status_agendamento
    (age_id, his_status_ant, his_status_novo, his_data)
  VALUES
    (NEW.age_id, 'Criado', NEW.age_status, CURDATE());
END $$

-- 5.2) trigger_pos_atualizar_agendamento_registrar_mudanca_de_status
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

-- 5.3) trigger_pos_inserir_avaliacao_criar_notificacao
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

-- 5.4) trigger_pos_inserir_pagamento_confirmado_criar_notificacao
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

-- 5.5) trigger_pos_inserir_agendamento_notificar_prestador
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

-- 5.6) trigger_pos_inserir_disputa_aberta_criar_notificacao
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
