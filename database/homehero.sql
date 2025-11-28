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
-- 3) VIEWS (LISTAGENS)
-- =========================================================

-- 3.1) view_lista_categorias
CREATE VIEW view_lista_categorias AS
SELECT
  cat_id,
  cat_nome,
  cat_descricao
FROM categoria_servico
ORDER BY cat_nome;

-- 3.2) view_lista_servicos (INNER JOIN pois todo serviço tem categoria)
CREATE VIEW view_lista_servicos AS
SELECT
  servico.ser_id,
  servico.ser_nome,
  servico.ser_descricao,
  servico.ser_preco_base,
  servico.ser_ativo,
  categoria_servico.cat_id,
  categoria_servico.cat_nome,
  categoria_servico.cat_descricao
FROM servico
JOIN categoria_servico USING (cat_id)
ORDER BY servico.ser_nome;

-- 3.3) view_servicos_oferecidos_por_prestadores
CREATE VIEW view_servicos_oferecidos_por_prestadores AS
SELECT
  prestador.pre_id,
  prestador.pre_nome,
  prestador.pre_email,
  prestador.pre_telefone,
  servico.ser_id,
  servico.ser_nome,
  servico.ser_descricao,
  prestador_servico.prs_id,
  prestador_servico.prs_preco,
  prestador_servico.prs_ativo,
  prestador_servico.prs_data
FROM prestador
JOIN prestador_servico USING (pre_id)
JOIN servico USING (ser_id)
ORDER BY prestador.pre_nome, servico.ser_nome;

-- 3.4) view_agendamentos_por_status
CREATE VIEW view_agendamentos_por_status AS
SELECT
  agendamento_servico.age_status,
  COUNT(*)       AS total_agendamentos,
  SUM(age_valor) AS soma_valores,
  AVG(age_valor) AS valor_medio
FROM agendamento_servico
GROUP BY agendamento_servico.age_status
ORDER BY COUNT(*) DESC;

-- 3.5) view_agendamentos_por_periodo
CREATE VIEW view_agendamentos_por_periodo AS
SELECT
  agendamento_servico.age_janela,
  COUNT(*)       AS total_agendamentos,
  SUM(age_valor) AS soma_valores,
  AVG(age_valor) AS valor_medio
FROM agendamento_servico
GROUP BY agendamento_servico.age_janela
ORDER BY COUNT(*) DESC;

-- 3.6) view_agendamentos_por_regiao_cliente
CREATE VIEW view_agendamentos_por_regiao_cliente AS
SELECT
  regiao.reg_id,
  regiao.reg_nome,
  regiao.reg_cidade,
  regiao.reg_uf,
  COUNT(agendamento_servico.age_id)  AS total_agendamentos,
  SUM(agendamento_servico.age_valor) AS soma_valores,
  AVG(agendamento_servico.age_valor) AS valor_medio
FROM regiao
JOIN registro_regiao USING (reg_id)
JOIN cliente USING (cli_id)
JOIN agendamento_servico USING (cli_id)
GROUP BY regiao.reg_id, regiao.reg_nome, regiao.reg_cidade, regiao.reg_uf
ORDER BY COUNT(agendamento_servico.age_id) DESC;

-- 3.7) view_pagamentos_por_status
CREATE VIEW view_pagamentos_por_status AS
SELECT
  pagamento.pag_status,
  COUNT(*)       AS quantidade_pagamentos,
  SUM(pag_valor) AS soma_valores,
  AVG(pag_valor) AS ticket_medio
FROM pagamento
GROUP BY pagamento.pag_status
ORDER BY COUNT(*) DESC;

-- 3.8) view_media_de_avaliacao_por_prestador
CREATE VIEW view_media_de_avaliacao_por_prestador AS
SELECT
  prestador.pre_id,
  prestador.pre_nome,
  prestador.pre_email,
  COUNT(avaliacao.ava_id) AS quantidade_avaliacoes,
  AVG(avaliacao.ava_nota) AS media_notas
FROM avaliacao
JOIN prestador USING (pre_id)
GROUP BY prestador.pre_id, prestador.pre_nome, prestador.pre_email
ORDER BY AVG(avaliacao.ava_nota) DESC;

-- 3.9) view_disputas_abertas
CREATE VIEW view_disputas_abertas AS
SELECT
  disputa_reembolso.dsp_id,
  disputa_reembolso.age_id,
  disputa_reembolso.cli_id,
  disputa_reembolso.pre_id,
  disputa_reembolso.dsp_status,
  disputa_reembolso.dsp_motivo,
  disputa_reembolso.dsp_valor,
  disputa_reembolso.dsp_abertura,
  disputa_reembolso.dsp_fechamento,
  cliente.cli_nome,
  cliente.cli_email,
  prestador.pre_nome,
  prestador.pre_email,
  agendamento_servico.age_data,
  agendamento_servico.age_status,
  agendamento_servico.age_valor
FROM disputa_reembolso
LEFT JOIN cliente USING (cli_id)
LEFT JOIN prestador USING (pre_id)
LEFT JOIN agendamento_servico USING (age_id)
WHERE disputa_reembolso.dsp_status != 'Resolvida'
ORDER BY disputa_reembolso.dsp_abertura DESC;

-- 3.10) view_disponibilidade_de_prestadores
CREATE VIEW view_disponibilidade_de_prestadores AS
SELECT
  prestador.pre_id,
  prestador.pre_nome,
  prestador.pre_email,
  prestador.pre_telefone,
  disponibilidade_prestador.dis_id,
  disponibilidade_prestador.dis_dia,
  disponibilidade_prestador.dis_janela,
  disponibilidade_prestador.dis_ativo
FROM prestador
JOIN disponibilidade_prestador USING (pre_id)
ORDER BY prestador.pre_nome,
         disponibilidade_prestador.dis_dia,
         disponibilidade_prestador.dis_janela;

-- 3.11) view_receita_por_prestador
CREATE VIEW view_receita_por_prestador AS
SELECT
  prestador.pre_id,
  prestador.pre_nome,
  prestador.pre_email,
  prestador.pre_telefone,
  COUNT(DISTINCT agendamento_servico.age_id) AS total_agendamentos,
  COUNT(pagamento.pag_id)                    AS total_pagamentos,
  SUM(pagamento.pag_valor)                   AS receita_total
FROM prestador
LEFT JOIN agendamento_servico
       ON agendamento_servico.pre_id = prestador.pre_id
LEFT JOIN pagamento
       ON pagamento.age_id = agendamento_servico.age_id
GROUP BY prestador.pre_id,
         prestador.pre_nome,
         prestador.pre_email,
         prestador.pre_telefone
ORDER BY SUM(pagamento.pag_valor) DESC;

-- 3.12) view_receita_por_periodo
CREATE VIEW view_receita_por_periodo AS
SELECT
  YEAR(pagamento.pag_data)  AS ano,
  MONTH(pagamento.pag_data) AS mes,
  COUNT(pagamento.pag_id)   AS total_pagamentos,
  SUM(pagamento.pag_valor)  AS receita_total,
  AVG(pagamento.pag_valor)  AS ticket_medio
FROM pagamento
GROUP BY YEAR(pagamento.pag_data),
         MONTH(pagamento.pag_data)
ORDER BY YEAR(pagamento.pag_data) DESC,
         MONTH(pagamento.pag_data) DESC;

-- 3.13) view_historico_status_agendamento (para mostrar as triggers)
CREATE VIEW view_historico_status_agendamento AS
SELECT
  historico_status_agendamento.his_id,
  historico_status_agendamento.age_id,
  historico_status_agendamento.his_status_ant,
  historico_status_agendamento.his_status_novo,
  historico_status_agendamento.his_data,
  agendamento_servico.age_data,
  agendamento_servico.age_janela,
  agendamento_servico.age_status,
  cliente.cli_nome,
  prestador.pre_nome,
  servico.ser_nome
FROM historico_status_agendamento
JOIN agendamento_servico USING (age_id)
JOIN cliente USING (cli_id)
JOIN prestador USING (pre_id)
JOIN servico USING (ser_id)
ORDER BY historico_status_agendamento.his_data DESC, historico_status_agendamento.his_id DESC;

-- =========================================================
-- 4) PROCEDURES
-- =========================================================

DROP PROCEDURE IF EXISTS pesquisar_clientes_por_nome_exato;
DROP PROCEDURE IF EXISTS listar_agendamentos_por_id_de_cliente;
DROP PROCEDURE IF EXISTS inserir_agendamento_de_servico;
DROP PROCEDURE IF EXISTS cancelar_agendamento_de_servico;
DROP PROCEDURE IF EXISTS confirmar_agendamento_de_servico;
DROP PROCEDURE IF EXISTS registrar_avaliacao_de_prestador;
DROP PROCEDURE IF EXISTS inserir_cliente;
DROP PROCEDURE IF EXISTS inserir_prestador;
DROP PROCEDURE IF EXISTS listar_agendamentos_por_periodo_e_status;
DROP PROCEDURE IF EXISTS abrir_disputa;
DROP PROCEDURE IF EXISTS fechar_disputa;
DROP PROCEDURE IF EXISTS inserir_pagamento;
DROP PROCEDURE IF EXISTS buscar_dados_pessoais_cliente;
DROP PROCEDURE IF EXISTS buscar_relacionamentos_cliente;
DROP PROCEDURE IF EXISTS buscar_dados_pessoais_prestador;
DROP PROCEDURE IF EXISTS buscar_relacionamentos_prestador;

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

-- 4.3) inserir_agendamento_de_servico (status inicial = 'Pendente')
CREATE PROCEDURE inserir_agendamento_de_servico(
  IN p_cli_id  INT,
  IN p_ser_id  INT,
  IN p_pre_id  INT,
  IN p_data    DATE,
  IN p_periodo VARCHAR(20),
  IN p_end_id  INT,
  IN p_valor   FLOAT
)
BEGIN
  INSERT INTO agendamento_servico
    (cli_id, ser_id, pre_id,
     age_data, age_janela, end_id, age_status, age_valor, age_pago)
  VALUES
    (p_cli_id, p_ser_id, p_pre_id,
     p_data, p_periodo, p_end_id, 'Pendente', p_valor, 0);
END $$

-- 4.4) cancelar_agendamento_de_servico
CREATE PROCEDURE cancelar_agendamento_de_servico(
  IN p_age_id INT,
  IN p_motivo VARCHAR(120)
)
BEGIN
  UPDATE agendamento_servico
     SET age_status      = 'Cancelado',
         age_data_cancel = CURDATE(),
         age_motivo      = p_motivo
   WHERE age_id = p_age_id
     AND age_status != 'Cancelado';
END $$

-- 4.5) confirmar_agendamento_de_servico
CREATE PROCEDURE confirmar_agendamento_de_servico(
  IN p_age_id INT
)
BEGIN
  UPDATE agendamento_servico
     SET age_status = 'Confirmado'
   WHERE age_id = p_age_id
     AND age_status != 'Cancelado';
END $$

-- 4.6) registrar_avaliacao_de_prestador
CREATE PROCEDURE registrar_avaliacao_de_prestador(
  IN p_age_id INT,
  IN p_cli_id INT,
  IN p_pre_id INT,
  IN p_nota   INT,
  IN p_coment VARCHAR(400)
)
BEGIN
  INSERT INTO avaliacao
    (age_id, cli_id, pre_id, ava_nota, ava_coment, ava_data)
  VALUES
    (p_age_id, p_cli_id, p_pre_id, p_nota, p_coment, CURDATE());
END $$

-- 4.7) inserir_cliente (cria endereço + cliente)
CREATE PROCEDURE inserir_cliente(
    IN p_nome            VARCHAR(80),
    IN p_cpf             VARCHAR(14),
    IN p_nasc            DATE,
    IN p_senha           VARCHAR(60),
    IN p_end_logradouro  VARCHAR(100),
    IN p_end_numero      VARCHAR(15),
    IN p_end_complemento VARCHAR(60),
    IN p_end_bairro      VARCHAR(60),
    IN p_end_cidade      VARCHAR(60),
    IN p_end_uf          VARCHAR(2),
    IN p_end_cep         VARCHAR(10),
    IN p_email           VARCHAR(120),
    IN p_telefone        VARCHAR(20)
)
BEGIN
    INSERT INTO endereco (
        end_logradouro,
        end_numero,
        end_complemento,
        end_bairro,
        end_cidade,
        end_uf,
        end_cep
    ) VALUES (
        p_end_logradouro,
        p_end_numero,
        p_end_complemento,
        p_end_bairro,
        p_end_cidade,
        p_end_uf,
        p_end_cep
    );

    INSERT INTO cliente (
        cli_nome,
        cli_cpf,
        cli_nascimento,
        cli_senha,
        end_id,
        cli_email,
        cli_telefone
    ) VALUES (
        p_nome,
        p_cpf,
        p_nasc,
        p_senha,
        LAST_INSERT_ID(),
        p_email,
        p_telefone
    );
END $$

-- 4.8) inserir_prestador (cria endereço + prestador)
CREATE PROCEDURE inserir_prestador(
    IN p_nome            VARCHAR(80),
    IN p_cpf             VARCHAR(14),
    IN p_nasc            DATE,
    IN p_areas           VARCHAR(120),
    IN p_exp             VARCHAR(255),
    IN p_certs           VARCHAR(255),
    IN p_senha           VARCHAR(60),
    IN p_end_logradouro  VARCHAR(100),
    IN p_end_numero      VARCHAR(15),
    IN p_end_complemento VARCHAR(60),
    IN p_end_bairro      VARCHAR(60),
    IN p_end_cidade      VARCHAR(60),
    IN p_end_uf          VARCHAR(2),
    IN p_end_cep         VARCHAR(10),
    IN p_email           VARCHAR(120),
    IN p_telefone        VARCHAR(20)
)
BEGIN
    INSERT INTO endereco (
        end_logradouro,
        end_numero,
        end_complemento,
        end_bairro,
        end_cidade,
        end_uf,
        end_cep
    ) VALUES (
        p_end_logradouro,
        p_end_numero,
        p_end_complemento,
        p_end_bairro,
        p_end_cidade,
        p_end_uf,
        p_end_cep
    );

    INSERT INTO prestador (
        pre_nome,
        pre_cpf,
        pre_nascimento,
        pre_areas,
        pre_experiencia,
        pre_certificados,
        pre_senha,
        end_id,
        pre_email,
        pre_telefone
    ) VALUES (
        p_nome,
        p_cpf,
        p_nasc,
        p_areas,
        p_exp,
        p_certs,
        p_senha,
        LAST_INSERT_ID(),
        p_email,
        p_telefone
    );
END $$

-- 4.9) listar_agendamentos_por_periodo_e_status
CREATE PROCEDURE listar_agendamentos_por_periodo_e_status(
  IN p_ini    DATE,
  IN p_fim    DATE,
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

-- 4.10) abrir_disputa
CREATE PROCEDURE abrir_disputa(
  IN p_age_id INT,
  IN p_cli_id INT,
  IN p_pre_id INT,
  IN p_motivo VARCHAR(255),
  IN p_valor  FLOAT
)
BEGIN
  INSERT INTO disputa_reembolso
    (age_id, cli_id, pre_id, dsp_motivo, dsp_status,
     dsp_valor, dsp_abertura)
  VALUES
    (p_age_id, p_cli_id, p_pre_id, p_motivo, 'Aberta',
     p_valor, CURDATE());
END $$

-- 4.11) fechar_disputa
CREATE PROCEDURE fechar_disputa(
  IN p_dsp_id INT
)
BEGIN
  UPDATE disputa_reembolso
     SET dsp_status    = 'Resolvida',
         dsp_fechamento = CURDATE()
   WHERE dsp_id = p_dsp_id
     AND dsp_status != 'Resolvida';
END $$

-- 4.12) inserir_pagamento
CREATE PROCEDURE inserir_pagamento(
  IN p_age_id INT,
  IN p_forma  VARCHAR(20),
  IN p_valor  FLOAT,
  IN p_status VARCHAR(20),
  IN p_ref    VARCHAR(60),
  IN p_data   DATE
)
BEGIN
  INSERT INTO pagamento
    (age_id, pag_forma, pag_valor, pag_status, pag_ref, pag_data)
  VALUES
    (p_age_id, p_forma, p_valor, p_status, p_ref, p_data);
END $$

-- 4.13) buscar_dados_pessoais_cliente
CREATE PROCEDURE buscar_dados_pessoais_cliente(
  IN p_cli_id INT
)
BEGIN
  SELECT
    cliente.cli_id,
    cliente.cli_nome,
    cliente.cli_cpf,
    cliente.cli_nascimento,
    cliente.cli_email,
    cliente.cli_telefone,
    cliente.end_id,
    endereco.end_logradouro,
    endereco.end_numero,
    endereco.end_complemento,
    endereco.end_bairro,
    endereco.end_cidade,
    endereco.end_uf,
    endereco.end_cep
  FROM cliente
  JOIN endereco USING (end_id)
  WHERE cliente.cli_id = p_cli_id;
END $$

-- 4.14) buscar_relacionamentos_cliente
CREATE PROCEDURE buscar_relacionamentos_cliente(
  IN p_cli_id INT
)
BEGIN
  SELECT
    agendamento_servico.age_id,
    agendamento_servico.age_data,
    agendamento_servico.age_status,
    agendamento_servico.age_valor,
    agendamento_servico.age_janela,
    servico.ser_nome,
    pagamento.pag_id,
    pagamento.pag_valor,
    pagamento.pag_status,
    disputa_reembolso.dsp_id,
    disputa_reembolso.dsp_status,
    disputa_reembolso.dsp_valor,
    avaliacao.ava_id,
    avaliacao.ava_nota,
    avaliacao.ava_coment
  FROM agendamento_servico
  JOIN servico USING (ser_id)
  LEFT JOIN pagamento USING (age_id)
  LEFT JOIN disputa_reembolso USING (age_id)
  LEFT JOIN avaliacao USING (age_id)
  WHERE agendamento_servico.cli_id = p_cli_id
  ORDER BY agendamento_servico.age_data DESC;
END $$

-- 4.15) buscar_dados_pessoais_prestador
CREATE PROCEDURE buscar_dados_pessoais_prestador(
  IN p_pre_id INT
)
BEGIN
  SELECT
    prestador.pre_id,
    prestador.pre_nome,
    prestador.pre_cpf,
    prestador.pre_nascimento,
    prestador.pre_areas,
    prestador.pre_experiencia,
    prestador.pre_certificados,
    prestador.pre_email,
    prestador.pre_telefone,
    prestador.end_id,
    endereco.end_logradouro,
    endereco.end_numero,
    endereco.end_complemento,
    endereco.end_bairro,
    endereco.end_cidade,
    endereco.end_uf,
    endereco.end_cep
  FROM prestador
  JOIN endereco USING (end_id)
  WHERE prestador.pre_id = p_pre_id;
END $$

-- 4.16) buscar_relacionamentos_prestador
CREATE PROCEDURE buscar_relacionamentos_prestador(
  IN p_pre_id INT
)
BEGIN
  SELECT
    agendamento_servico.age_id,
    agendamento_servico.age_data,
    agendamento_servico.age_status,
    agendamento_servico.age_valor,
    agendamento_servico.age_janela,
    cliente.cli_nome,
    cliente.cli_email,
    servico.ser_nome,
    pagamento.pag_id,
    pagamento.pag_valor,
    pagamento.pag_status,
    disputa_reembolso.dsp_id,
    disputa_reembolso.dsp_status,
    disputa_reembolso.dsp_valor,
    avaliacao.ava_id,
    avaliacao.ava_nota,
    avaliacao.ava_coment
  FROM agendamento_servico
  JOIN cliente USING (cli_id)
  JOIN servico USING (ser_id)
  LEFT JOIN pagamento USING (age_id)
  LEFT JOIN disputa_reembolso USING (age_id)
  LEFT JOIN avaliacao USING (age_id)
  WHERE agendamento_servico.pre_id = p_pre_id
  ORDER BY agendamento_servico.age_data DESC;
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

INSERT INTO regiao (reg_nome, reg_cidade, reg_uf) VALUES
('Zona Oeste', 'Brasilia', 'DF'),
('Centro Historico', 'Recife', 'PE'),
('Jardins', 'Campinas', 'SP'),
('Praia de Ipanema', 'Florianopolis', 'SC'),
('Alphaville', 'Barueri', 'SP');

-- ============================================================
-- 2) ENDERECO (sem dependencias)
-- ============================================================
INSERT INTO endereco (end_logradouro, end_numero, end_complemento, end_bairro, end_cidade, end_uf, end_cep) VALUES
('Avenida W3 Sul', '504', 'Bloco A', 'Asa Sul', 'Brasilia', 'DF', '70392-000'),
('Rua da Aurora', '789', 'Sobrado', 'Boa Vista', 'Recife', 'PE', '50050-000'),
('Avenida Barao de Itapura', '1500', 'Apto 301', 'Cambui', 'Campinas', 'SP', '13020-430'),
('Avenida Beira Mar Norte', '2000', 'Casa', 'Centro', 'Florianopolis', 'SC', '88015-700'),
('Alameda Rio Negro', '500', 'Sala 100', 'Alphaville', 'Barueri', 'SP', '06454-000'),
('Rua das Flores', '123', 'Apto 42', 'Batel', 'Curitiba', 'PR', '80000-000'),
('Avenida Paulista', '1000', 'Conjunto 501', 'Bela Vista', 'Sao Paulo', 'SP', '01310-100'),
('Rua do Comercio', '456', 'Loja 2', 'Centro', 'Rio de Janeiro', 'RJ', '20010-000'),
('Avenida Atlantica', '3000', 'Apto 1501', 'Copacabana', 'Rio de Janeiro', 'RJ', '22070-011'),
('Rua XV de Novembro', '789', 'Sala 205', 'Centro', 'Curitiba', 'PR', '80020-310');

-- ============================================================
-- 3) CATEGORIA_SERVICO (sem dependencias)
-- ============================================================
INSERT INTO categoria_servico (cat_nome, cat_descricao) VALUES
('Pintura', 'Servicos de pintura residencial e comercial'),
('Marcenaria', 'Moveis sob medida e reparos em madeira'),
('Piscina', 'Limpeza e manutencao de piscinas'),
('Ar Condicionado', 'Instalacao e manutencao de ar condicionado'),
('Seguranca', 'Instalacao de sistemas de seguranca');

-- ============================================================
-- 4) CLIENTE (depende de endereco)
-- ============================================================
INSERT INTO cliente (cli_nome, cli_cpf, cli_nascimento, cli_senha, end_id, cli_email, cli_telefone) VALUES
('Roberto Almeida', '11122233344', '1987-04-22', 'senha001', 1, 'roberto.almeida@email.com', '(61)99999-1111'),
('Patricia Lima', '22233344455', '1991-09-15', 'senha002', 2, 'patricia.lima@email.com', '(81)98888-2222'),
('Marcos Pereira', '33344455566', '1989-12-08', 'senha003', 3, 'marcos.pereira@email.com', '(19)97777-3333'),
('Fernanda Rocha', '44455566677', '1993-06-30', 'senha004', 4, 'fernanda.rocha@email.com', '(48)96666-4444'),
('Ricardo Barbosa', '55566677788', '1986-11-18', 'senha005', 5, 'ricardo.barbosa@email.com', '(11)95555-5555');

-- ============================================================
-- 5) PRESTADOR (depende de endereco)
-- ============================================================
INSERT INTO prestador (pre_nome, pre_cpf, pre_nascimento, pre_areas, pre_experiencia, pre_certificados, pre_senha, end_id, pre_email, pre_telefone) VALUES
('Antonio Gomes', '99988877766', '1978-02-14', 'Pintura, Acabamento', '15 anos de experiencia', 'Certificado de Pintura Profissional', 'pre001', 6, 'antonio.gomes@email.com', '(61)99999-6666'),
('Sandra Martins', '88877766655', '1983-07-25', 'Marcenaria, Moveis', '12 anos de experiencia', 'Mestre Marceneiro', 'pre002', 7, 'sandra.martins@email.com', '(81)98888-7777'),
('Paulo Henrique', '77766655544', '1985-10-03', 'Piscina, Manutencao', '8 anos de experiencia', 'Tecnico em Piscinas', 'pre003', 8, 'paulo.henrique@email.com', '(19)97777-8888'),
('Lucia Santos', '66655544433', '1982-05-19', 'Ar Condicionado, Refrigeracao', '14 anos de experiencia', 'Tecnico em Refrigeracao', 'pre004', 9, 'lucia.santos@email.com', '(48)96666-9999'),
('Eduardo Costa', '55544433322', '1990-01-28', 'Seguranca, Eletronica', '7 anos de experiencia', 'Instalador de Sistemas de Seguranca', 'pre005', 10, 'eduardo.costa@email.com', '(11)95555-0000');

-- ============================================================
-- 6) SERVICO (depende de categoria_servico)
-- ============================================================
INSERT INTO servico (ser_nome, ser_descricao, ser_preco_base, ser_ativo, cat_id) VALUES
('Pintura Interna', 'Pintura completa de ambientes internos', 300.00, TRUE, 1),
('Mesa de Jantar', 'Fabricacao de mesa de jantar sob medida', 800.00, TRUE, 2),
('Limpeza de Piscina', 'Limpeza e tratamento quimico de piscina', 150.00, TRUE, 3),
('Instalacao Split', 'Instalacao de ar condicionado split', 400.00, TRUE, 4),
('Cameras de Seguranca', 'Instalacao de sistema de cameras', 600.00, TRUE, 5);

-- ============================================================
-- 7) REGISTRO_REGIAO (depende de regiao, cliente, prestador)
-- ============================================================
INSERT INTO registro_regiao (reg_id, cli_id, pre_id, rre_data) VALUES
(1, 1, 1, '2024-05-10'),
(2, 2, 2, '2024-05-15'),
(3, 3, 3, '2024-05-20'),
(4, 4, 4, '2024-05-25'),
(5, 5, 5, '2024-06-01');

-- ============================================================
-- 8) PRESTADOR_SERVICO (depende de prestador, servico)
-- ============================================================
INSERT INTO prestador_servico (pre_id, ser_id, prs_preco, prs_ativo, prs_data) VALUES
(1, 1, 300.00, TRUE, '2024-05-01'),
(2, 2, 800.00, TRUE, '2024-05-01'),
(3, 3, 150.00, TRUE, '2024-05-01'),
(4, 4, 400.00, TRUE, '2024-05-01'),
(5, 5, 600.00, TRUE, '2024-05-01');

-- ============================================================
-- 9) DISPONIBILIDADE_PRESTADOR (depende de prestador)
-- ============================================================
INSERT INTO disponibilidade_prestador (pre_id, dis_dia, dis_janela, dis_ativo) VALUES
(1, 'Segunda', 'Tarde', TRUE),
(2, 'Terca', 'Manha', TRUE),
(3, 'Quarta', 'Tarde', TRUE),
(4, 'Quinta', 'Manha', TRUE),
(5, 'Sexta', 'Tarde', TRUE);

-- ============================================================
-- 10) CERTIFICACAO_PRESTADOR (depende de prestador)
-- ============================================================
INSERT INTO certificacao_prestador (pre_id, cer_titulo, cer_instituicao, cer_data, cer_url) VALUES
(1, 'Certificado de Pintura Profissional', 'Academia de Pintura', '2019-03-10', 'https://certificados.com/pintura'),
(2, 'Mestre Marceneiro', 'Escola de Marcenaria', '2017-06-15', 'https://certificados.com/marcenaria'),
(3, 'Tecnico em Piscinas', 'Instituto de Piscinas', '2020-09-20', 'https://certificados.com/piscina'),
(4, 'Tecnico em Refrigeracao', 'Escola Tecnica de Refrigeracao', '2015-11-05', 'https://certificados.com/refrigeracao'),
(5, 'Instalador de Sistemas de Seguranca', 'Instituto de Seguranca', '2021-04-12', 'https://certificados.com/seguranca');

-- ============================================================
-- 11) AGENDAMENTO_SERVICO (depende de cliente, servico, prestador, endereco)
-- ============================================================
INSERT INTO agendamento_servico (cli_id, ser_id, pre_id, age_data, age_janela, end_id, age_status, age_valor, age_pago, age_data_cancel, age_motivo) VALUES
(1, 1, 1, '2024-06-10', 'Tarde', 1, 'Agendado', 300.00, FALSE, NULL, NULL),
(2, 2, 2, '2024-06-15', 'Manha', 2, 'Confirmado', 800.00, TRUE, NULL, NULL),
(3, 3, 3, '2024-06-20', 'Tarde', 3, 'Concluido', 150.00, TRUE, NULL, NULL),
(4, 4, 4, '2024-06-25', 'Manha', 4, 'Em andamento', 400.00, FALSE, NULL, NULL),
(5, 5, 5, '2024-07-01', 'Tarde', 5, 'Cancelado', 600.00, FALSE, '2024-06-28', 'Cliente mudou de ideia');

-- ============================================================
-- 12) PAGAMENTO (depende de agendamento_servico)
-- ============================================================
INSERT INTO pagamento (age_id, pag_forma, pag_valor, pag_status, pag_ref, pag_data) VALUES
(2, 'PIX', 800.00, 'Pago', 'PIX001', '2024-06-15'),
(3, 'Cartao', 150.00, 'Pago', 'CARD001', '2024-06-20'),
(1, 'Boleto', 300.00, 'Pendente', 'BOL001', '2024-06-10'),
(4, 'PIX', 400.00, 'Pendente', 'PIX002', '2024-06-25'),
(2, 'Cartao', 800.00, 'Pago', 'CARD002', '2024-06-15');

-- ============================================================
-- 13) DISPUTA_REEMBOLSO (depende de agendamento_servico, cliente, prestador)
-- ============================================================
INSERT INTO disputa_reembolso (age_id, cli_id, pre_id, dsp_motivo, dsp_status, dsp_valor, dsp_abertura, dsp_fechamento) VALUES
(5, 5, 5, 'Servico cancelado pelo cliente', 'Aberta', 600.00, '2024-06-28', NULL),
(1, 1, 1, 'Atraso no inicio do servico', 'Aberta', 300.00, '2024-06-11', NULL),
(2, 2, 2, 'Reembolso parcial solicitado', 'Resolvida', 400.00, '2024-06-16', '2024-06-17'),
(3, 3, 3, 'Problema com qualidade', 'Aberta', 150.00, '2024-06-21', NULL),
(4, 4, 4, 'Desacordo sobre prazo', 'Aberta', 400.00, '2024-06-26', NULL);

-- ============================================================
-- 14) AVALIACAO (depende de agendamento_servico, cliente, prestador)
-- ============================================================
INSERT INTO avaliacao (age_id, cli_id, pre_id, ava_nota, ava_coment, ava_data) VALUES
(2, 2, 2, 5, 'Trabalho impecavel! Recomendo muito.', '2024-06-16'),
(3, 3, 3, 4, 'Bom servico, pontual e eficiente.', '2024-06-21'),
(1, 1, 1, 3, 'Servico ok, mas poderia ser melhor.', '2024-06-11'),
(2, 2, 2, 5, 'Excelente profissional!', '2024-06-17'),
(3, 3, 3, 4, 'Atendeu bem as expectativas.', '2024-06-22');

-- ============================================================
-- 15) NOTIFICACAO (depende de cliente, prestador, agendamento_servico)
-- ============================================================
INSERT INTO notificacao (cli_id, pre_id, age_id, not_tipo, not_msg, not_enviado, not_data) VALUES
(1, 1, 1, 'Agendamento', 'Novo agendamento atribuido ao prestador.', FALSE, '2024-06-05'),
(2, 2, 2, 'Pagamento', 'Pagamento confirmado.', TRUE, '2024-06-15'),
(3, 3, 3, 'Avaliacao', 'Nova avaliacao registrada para prestador.', FALSE, '2024-06-21'),
(4, 4, 4, 'Disputa', 'Sua disputa foi aberta.', FALSE, '2024-06-26'),
(5, 5, 5, 'Agendamento', 'Novo agendamento atribuido ao prestador.', FALSE, '2024-06-28');

-- ============================================================
-- 16) CHAT_MENSAGEM (depende de agendamento_servico)
-- ============================================================
INSERT INTO chat_mensagem (age_id, cha_remetente_tipo, cha_remetente_id, cha_destinatario_tipo, cha_destinatario_id, cha_msg, cha_data) VALUES
(1, 'Cliente', 1, 'Prestador', 1, 'Bom dia! Confirmo o agendamento para a proxima semana.', '2024-06-05'),
(2, 'Prestador', 2, 'Cliente', 2, 'Perfeito! Estarei no local no horario combinado.', '2024-06-12'),
(3, 'Cliente', 3, 'Prestador', 3, 'Muito obrigado pelo excelente trabalho!', '2024-06-21'),
(4, 'Cliente', 4, 'Prestador', 4, 'Preciso adiar o agendamento, e possivel?', '2024-06-24'),
(5, 'Prestador', 5, 'Cliente', 5, 'Entendido. Podemos reagendar para quando for conveniente.', '2024-06-28');

-- ============================================================
-- 17) HISTORICO_STATUS_AGENDAMENTO (depende de agendamento_servico)
-- ============================================================
INSERT INTO historico_status_agendamento (age_id, his_status_ant, his_status_novo, his_data) VALUES
(1, 'Criado', 'Agendado', '2024-06-05'),
(1, 'Agendado', 'Agendado', '2024-06-08'),
(2, 'Criado', 'Confirmado', '2024-06-12'),
(3, 'Criado', 'Concluido', '2024-06-20'),
(5, 'Criado', 'Cancelado', '2024-06-28');

-- ============================================================
-- 18) ADMIN (sem dependencias)
-- ============================================================
INSERT INTO admin (adm_nome, adm_email, adm_senha) VALUES
('Admin Sistema', 'sistema@homehero.com', 'sistema123'),
('Admin Tecnico', 'tecnico@homehero.com', 'tecnico123'),
('Admin Comercial', 'comercial@homehero.com', 'comercial123'),
('Admin RH', 'rh@homehero.com', 'rh123'),
('Admin Diretor', 'diretor@homehero.com', 'diretor123');

-- =========================================================
-- Fim do script completo
-- =========================================================