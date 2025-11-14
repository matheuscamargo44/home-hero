-- =========================================================
-- 1) RESET DO BANCO
-- =========================================================
DROP DATABASE IF EXISTS homehero;
CREATE DATABASE homehero CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE homehero;

-- =========================================================
-- 2) TABELAS
-- =========================================================

-- 2.1) TABELA REGIAO
CREATE TABLE regiao (
  reg_id INT PRIMARY KEY AUTO_INCREMENT,
  reg_nome VARCHAR(80) NOT NULL,
  reg_cidade VARCHAR(60),
  reg_uf VARCHAR(2)
);

-- 2.2) TABELA ENDERECO
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

-- 2.3) TABELA CLIENTE
CREATE TABLE cliente (
  cli_id INT PRIMARY KEY AUTO_INCREMENT,
  cli_nome_completo VARCHAR(80) NOT NULL,
  cli_cpf VARCHAR(14) NOT NULL UNIQUE,
  cli_data_nascimento DATE NOT NULL,
  cli_senha VARCHAR(60) NOT NULL,
  cli_endereco_id INT NOT NULL,
  cli_email VARCHAR(120) NOT NULL UNIQUE,
  cli_telefone VARCHAR(20) NOT NULL UNIQUE,
  FOREIGN KEY (cli_endereco_id) REFERENCES endereco(end_id)
);

-- 2.4) TABELA PRESTADOR
CREATE TABLE prestador (
  pre_id INT PRIMARY KEY AUTO_INCREMENT,
  pre_nome_completo VARCHAR(80) NOT NULL,
  pre_cpf VARCHAR(14) NOT NULL UNIQUE,
  pre_data_nascimento DATE NOT NULL,
  pre_areas_atuacao VARCHAR(120),
  pre_experiencia VARCHAR(255),
  pre_certificados VARCHAR(255),
  pre_senha VARCHAR(60) NOT NULL,
  pre_endereco_id INT NOT NULL,
  pre_email VARCHAR(120) NOT NULL UNIQUE,
  pre_telefone VARCHAR(20) NOT NULL UNIQUE,
  FOREIGN KEY (pre_endereco_id) REFERENCES endereco(end_id)
);

-- 2.5) TABELA CATEGORIA_SERVICO
CREATE TABLE categoria_servico (
  cat_id INT PRIMARY KEY AUTO_INCREMENT,
  cat_nome VARCHAR(60) NOT NULL,
  cat_descricao VARCHAR(255)
);

-- 2.6) TABELA SERVICO
CREATE TABLE servico (
  ser_id INT PRIMARY KEY AUTO_INCREMENT,
  ser_nome VARCHAR(80) NOT NULL,
  ser_descricao VARCHAR(255),
  ser_preco_base FLOAT,
  ser_ativo BOOLEAN,
  ser_cat_id INT,
  FOREIGN KEY (ser_cat_id) REFERENCES categoria_servico(cat_id)
);

-- 2.7) TABELA REGISTRO_REGIAO
CREATE TABLE registro_regiao (
  rre_id INT PRIMARY KEY AUTO_INCREMENT,
  rre_reg_id INT NOT NULL,
  rre_cli_id INT,
  rre_pre_id INT,
  rre_data_registro DATE,
  FOREIGN KEY (rre_reg_id) REFERENCES regiao(reg_id),
  FOREIGN KEY (rre_cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (rre_pre_id) REFERENCES prestador(pre_id)
);

-- 2.8) TABELA PRESTADOR_SERVICO
CREATE TABLE prestador_servico (
  prs_id INT PRIMARY KEY AUTO_INCREMENT,
  prs_pre_id INT NOT NULL,
  prs_ser_id INT NOT NULL,
  prs_preco_oferta FLOAT,
  prs_ativo BOOLEAN,
  prs_data_cadastro DATE,
  FOREIGN KEY (prs_pre_id) REFERENCES prestador(pre_id),
  FOREIGN KEY (prs_ser_id) REFERENCES servico(ser_id)
);

-- 2.9) TABELA DISPONIBILIDADE_PRESTADOR
CREATE TABLE disponibilidade_prestador (
  dis_id INT PRIMARY KEY AUTO_INCREMENT,
  dis_pre_id INT NOT NULL,
  dis_dia_semana VARCHAR(10),
  dis_janela VARCHAR(20),
  dis_ativo BOOLEAN,
  FOREIGN KEY (dis_pre_id) REFERENCES prestador(pre_id)
);

-- 2.10) TABELA CERTIFICACAO_PRESTADOR
CREATE TABLE certificacao_prestador (
  cer_id INT PRIMARY KEY AUTO_INCREMENT,
  cer_pre_id INT NOT NULL,
  cer_titulo VARCHAR(120),
  cer_instituicao VARCHAR(120),
  cer_data_conclusao DATE,
  cer_url VARCHAR(255),
  FOREIGN KEY (cer_pre_id) REFERENCES prestador(pre_id)
);

-- 2.11) TABELA AGENDAMENTO_SERVICO
CREATE TABLE agendamento_servico (
  age_id INT PRIMARY KEY AUTO_INCREMENT,
  age_cli_id INT NOT NULL,
  age_ser_id INT NOT NULL,
  age_pre_id INT,
  age_data DATE,
  age_janela VARCHAR(20),
  age_end_id INT,
  age_status VARCHAR(20),
  age_valor FLOAT,
  age_pago BOOLEAN,
  age_data_cancelamento DATE,
  age_motivo_cancelamento VARCHAR(120),
  FOREIGN KEY (age_cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (age_ser_id) REFERENCES servico(ser_id),
  FOREIGN KEY (age_pre_id) REFERENCES prestador(pre_id),
  FOREIGN KEY (age_end_id) REFERENCES endereco(end_id)
);

-- 2.12) TABELA PAGAMENTO
CREATE TABLE pagamento (
  pag_id INT PRIMARY KEY AUTO_INCREMENT,
  pag_age_id INT NOT NULL,
  pag_forma VARCHAR(20),
  pag_valor_total FLOAT,
  pag_status VARCHAR(20),
  pag_referencia_gateway VARCHAR(60),
  pag_data DATE,
  FOREIGN KEY (pag_age_id) REFERENCES agendamento_servico(age_id)
);

-- 2.13) TABELA DISPUTA_REEMBOLSO
CREATE TABLE disputa_reembolso (
  dsp_id INT PRIMARY KEY AUTO_INCREMENT,
  dsp_age_id INT NOT NULL,
  dsp_cli_id INT,
  dsp_pre_id INT,
  dsp_motivo VARCHAR(255),
  dsp_status VARCHAR(20),
  dsp_valor_reembolsar FLOAT,
  dsp_data_abertura DATE,
  dsp_data_fechamento DATE,
  FOREIGN KEY (dsp_age_id) REFERENCES agendamento_servico(age_id),
  FOREIGN KEY (dsp_cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (dsp_pre_id) REFERENCES prestador(pre_id)
);

-- 2.14) TABELA AVALIACAO
CREATE TABLE avaliacao (
  ava_id INT PRIMARY KEY AUTO_INCREMENT,
  ava_age_id INT NOT NULL,
  ava_cli_id INT NOT NULL,
  ava_pre_id INT,
  ava_nota INT,
  ava_comentario VARCHAR(400),
  ava_data DATE,
  FOREIGN KEY (ava_age_id) REFERENCES agendamento_servico(age_id),
  FOREIGN KEY (ava_cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (ava_pre_id) REFERENCES prestador(pre_id)
);

-- 2.15) TABELA NOTIFICACAO
CREATE TABLE notificacao (
  not_id INT PRIMARY KEY AUTO_INCREMENT,
  not_cli_id INT,
  not_pre_id INT,
  not_age_id INT,
  not_tipo VARCHAR(30),
  not_mensagem VARCHAR(255),
  not_enviado BOOLEAN,
  not_data DATE,
  FOREIGN KEY (not_cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (not_pre_id) REFERENCES prestador(pre_id),
  FOREIGN KEY (not_age_id) REFERENCES agendamento_servico(age_id)
);

-- 2.16) TABELA CHAT_MENSAGEM
CREATE TABLE chat_mensagem (
  cha_id INT PRIMARY KEY AUTO_INCREMENT,
  cha_age_id INT,
  cha_remetente_tipo VARCHAR(20),
  cha_remetente_id INT,
  cha_destinatario_tipo VARCHAR(20),
  cha_destinatario_id INT,
  cha_mensagem VARCHAR(500),
  cha_data DATE,
  FOREIGN KEY (cha_age_id) REFERENCES agendamento_servico(age_id)
);

-- 2.17) TABELA CHATBOT_LOG
CREATE TABLE chatbot_log (
  cbt_id INT PRIMARY KEY AUTO_INCREMENT,
  cbt_cli_id INT,
  cbt_pre_id INT,
  cbt_age_id INT,
  cbt_pergunta VARCHAR(300),
  cbt_resposta VARCHAR(500),
  cbt_data DATE,
  FOREIGN KEY (cbt_cli_id) REFERENCES cliente(cli_id),
  FOREIGN KEY (cbt_pre_id) REFERENCES prestador(pre_id),
  FOREIGN KEY (cbt_age_id) REFERENCES agendamento_servico(age_id)
);

-- 2.18) TABELA HISTORICO_STATUS_AGENDAMENTO
CREATE TABLE historico_status_agendamento (
  his_id INT PRIMARY KEY AUTO_INCREMENT,
  his_age_id INT NOT NULL,
  his_status_anterior VARCHAR(20),
  his_status_novo VARCHAR(20),
  his_data_alteracao DATE,
  FOREIGN KEY (his_age_id) REFERENCES agendamento_servico(age_id)
);

USE homehero;

-- =========================================================
-- 3) VIEWS
-- =========================================================

-- 3.1) VIEW_DADOS_DE_CLIENTES
CREATE OR REPLACE VIEW view_dados_de_clientes
AS
SELECT
  cli_id,
  cli_nome_completo,
  cli_email,
  cli_telefone
FROM cliente
ORDER BY cli_nome_completo;

-- 3.2) VIEW_DADOS_DE_PRESTADORES
CREATE OR REPLACE VIEW view_dados_de_prestadores
AS
SELECT
  pre_id,
  pre_nome_completo,
  pre_email,
  pre_telefone
FROM prestador
ORDER BY pre_nome_completo;

-- 3.3) VIEW_SERVICOS_OFERECIDOS_POR_PRESTADORES
CREATE OR REPLACE VIEW view_servicos_oferecidos_por_prestadores
AS
SELECT
  prestador.pre_id,
  prestador.pre_nome_completo,
  servico.ser_id,
  servico.ser_nome,
  categoria_servico.cat_nome,
  prestador_servico.prs_preco_oferta,
  prestador_servico.prs_ativo
FROM prestador
JOIN prestador_servico
  ON prestador_servico.prs_pre_id = prestador.pre_id
JOIN servico
  ON servico.ser_id = prestador_servico.prs_ser_id
JOIN categoria_servico
  ON categoria_servico.cat_id = servico.ser_cat_id
ORDER BY prestador.pre_nome_completo;

-- 3.4) VIEW_DETALHES_DE_AGENDAMENTOS
CREATE OR REPLACE VIEW view_detalhes_de_agendamentos
AS
SELECT
  agendamento_servico.age_id,
  agendamento_servico.age_data,
  agendamento_servico.age_status,
  cliente.cli_nome_completo AS nome_do_cliente,
  prestador.pre_nome_completo AS nome_do_prestador,
  servico.ser_nome AS nome_do_servico,
  agendamento_servico.age_valor AS valor_do_agendamento
FROM agendamento_servico
JOIN cliente
  ON cliente.cli_id = agendamento_servico.age_cli_id
JOIN servico
  ON servico.ser_id = agendamento_servico.age_ser_id
LEFT JOIN prestador
  ON prestador.pre_id = agendamento_servico.age_pre_id
ORDER BY agendamento_servico.age_data;

-- 3.5) VIEW_MEDIA_DE_AVALIACAO_POR_PRESTADOR
CREATE OR REPLACE VIEW view_media_de_avaliacao_por_prestador
AS
SELECT
  ava_pre_id AS prestador_id,
  COUNT(*) AS quantidade_de_avaliacoes,
  AVG(ava_nota) AS media_de_nota
FROM avaliacao
WHERE ava_pre_id IS NOT NULL
GROUP BY ava_pre_id
ORDER BY media_de_nota DESC;

-- 3.6) VIEW_AGENDAMENTOS_POR_STATUS
CREATE OR REPLACE VIEW view_agendamentos_por_status
AS
SELECT
  age_status AS status,
  COUNT(*) AS total
FROM agendamento_servico
GROUP BY age_status
ORDER BY total DESC;

-- 3.7) VIEW_AGENDAMENTOS_POR_PERIODO
CREATE OR REPLACE VIEW view_agendamentos_por_periodo
AS
SELECT
  age_janela AS periodo,
  COUNT(*) AS total
FROM agendamento_servico
GROUP BY age_janela
ORDER BY total DESC;

-- 3.8) VIEW_AGENDAMENTOS_POR_REGIAO_CLIENTE
CREATE OR REPLACE VIEW view_agendamentos_por_regiao_cliente
AS
SELECT
  regiao.reg_nome,
  regiao.reg_cidade,
  regiao.reg_uf,
  COUNT(agendamento_servico.age_id) AS total_agendamentos
FROM agendamento_servico
JOIN cliente
  ON cliente.cli_id = agendamento_servico.age_cli_id
LEFT JOIN registro_regiao
  ON registro_regiao.rre_cli_id = cliente.cli_id
LEFT JOIN regiao
  ON regiao.reg_id = registro_regiao.rre_reg_id
GROUP BY regiao.reg_nome, regiao.reg_cidade, regiao.reg_uf
ORDER BY total_agendamentos DESC;

-- 3.9) VIEW_PAGAMENTOS_POR_STATUS
CREATE OR REPLACE VIEW view_pagamentos_por_status
AS
SELECT
  pag_status AS status_pagamento,
  COUNT(*) AS total,
  SUM(pag_valor_total) AS soma_valores
FROM pagamento
GROUP BY pag_status
ORDER BY total DESC;

-- 3.10) VIEW_DISPUTAS_ABERTAS
CREATE OR REPLACE VIEW view_disputas_abertas
AS
SELECT
  disputa_reembolso.dsp_id,
  disputa_reembolso.dsp_status,
  disputa_reembolso.dsp_motivo,
  disputa_reembolso.dsp_valor_reembolsar,
  disputa_reembolso.dsp_data_abertura,
  cliente.cli_nome_completo AS nome_cliente,
  prestador.pre_nome_completo AS nome_prestador
FROM disputa_reembolso
LEFT JOIN cliente
  ON cliente.cli_id = disputa_reembolso.dsp_cli_id
LEFT JOIN prestador
  ON prestador.pre_id = disputa_reembolso.dsp_pre_id
WHERE disputa_reembolso.dsp_status != 'Resolvida'
ORDER BY disputa_reembolso.dsp_data_abertura DESC;

-- 3.11) VIEW_DISPONIBILIDADE_DE_PRESTADORES
CREATE OR REPLACE VIEW view_disponibilidade_de_prestadores
AS
SELECT
  prestador.pre_id,
  prestador.pre_nome_completo,
  disponibilidade_prestador.dis_dia_semana,
  disponibilidade_prestador.dis_janela,
  disponibilidade_prestador.dis_ativo
FROM prestador
JOIN disponibilidade_prestador
  ON disponibilidade_prestador.dis_pre_id = prestador.pre_id
ORDER BY prestador.pre_nome_completo, disponibilidade_prestador.dis_dia_semana;

-- =========================================================
-- 4) STORED PROCEDURES
-- =========================================================
DELIMITER $$

-- 4.1) PROCEDURE PESQUISAR_CLIENTES_POR_NOME_EXATO
CREATE PROCEDURE pesquisar_clientes_por_nome_exato(IN p_nome VARCHAR(80))
BEGIN
  SELECT
    cli_id,
    cli_nome_completo,
    cli_cpf
  FROM cliente
  WHERE cli_nome_completo = p_nome
  ORDER BY cli_nome_completo;
END $$

-- 4.2) PROCEDURE LISTAR_AGENDAMENTOS_POR_ID_DE_CLIENTE
CREATE PROCEDURE listar_agendamentos_por_id_de_cliente(IN p_cli_id INT)
BEGIN
  SELECT
    agendamento_servico.age_id,
    agendamento_servico.age_data,
    agendamento_servico.age_status,
    servico.ser_nome,
    agendamento_servico.age_valor
  FROM agendamento_servico
  JOIN servico
    ON servico.ser_id = agendamento_servico.age_ser_id
  WHERE agendamento_servico.age_cli_id = p_cli_id
  ORDER BY agendamento_servico.age_data;
END $$

-- 4.3) PROCEDURE INSERIR_AGENDAMENTO_DE_SERVICO
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
    (age_cli_id, age_ser_id, age_pre_id,
     age_data, age_janela, age_end_id, age_status, age_valor, age_pago)
  VALUES
    (p_cli_id, p_ser_id, p_pre_id,
     p_data, p_periodo, p_end_id, p_status_inicial, p_valor, 0);
END $$

-- 4.4) PROCEDURE CANCELAR_AGENDAMENTO_DE_SERVICO
CREATE PROCEDURE cancelar_agendamento_de_servico(
  IN p_age_id INT,
  IN p_motivo VARCHAR(120)
)
BEGIN
  UPDATE agendamento_servico
     SET age_status = 'Cancelado',
         age_data_cancelamento = CURDATE(),
         age_motivo_cancelamento = p_motivo
   WHERE age_id = p_age_id
     AND age_status != 'Cancelado';
END $$

-- 4.5) PROCEDURE REGISTRAR_AVALIACAO_DE_PRESTADOR
CREATE PROCEDURE registrar_avaliacao_de_prestador(
  IN p_age_id INT, IN p_cli_id INT, IN p_pre_id INT,
  IN p_nota INT, IN p_coment VARCHAR(400)
)
BEGIN
  INSERT INTO avaliacao
    (ava_age_id, ava_cli_id, ava_pre_id, ava_nota, ava_comentario, ava_data)
  VALUES
    (p_age_id, p_cli_id, p_pre_id, p_nota, p_coment, CURDATE());
END $$

-- 4.6) PROCEDURE INSERIR_CLIENTE
CREATE PROCEDURE inserir_cliente(
  IN p_nome VARCHAR(80), IN p_cpf VARCHAR(14),
  IN p_nasc DATE,
  IN p_senha VARCHAR(60), IN p_end_id INT,
  IN p_email VARCHAR(120), IN p_telefone VARCHAR(20)
)
BEGIN
  INSERT INTO cliente
    (cli_nome_completo, cli_cpf, cli_data_nascimento,
     cli_senha, cli_endereco_id,
     cli_email, cli_telefone)
  VALUES (p_nome, p_cpf, p_nasc,
          p_senha, p_end_id,
          p_email, p_telefone);
END $$

-- 4.7) PROCEDURE INSERIR_PRESTADOR
CREATE PROCEDURE inserir_prestador(
  IN p_nome VARCHAR(80), IN p_cpf VARCHAR(14),
  IN p_nasc DATE, IN p_areas VARCHAR(120),
  IN p_exp VARCHAR(255), IN p_certs VARCHAR(255),
  IN p_senha VARCHAR(60), IN p_end_id INT,
  IN p_email VARCHAR(120), IN p_telefone VARCHAR(20)
)
BEGIN
  INSERT INTO prestador
    (pre_nome_completo, pre_cpf, pre_data_nascimento, pre_areas_atuacao,
     pre_experiencia, pre_certificados, pre_senha, pre_endereco_id,
     pre_email, pre_telefone)
  VALUES (p_nome, p_cpf, p_nasc, p_areas, p_exp, p_certs, p_senha, p_end_id,
          p_email, p_telefone);
END $$

-- 4.8) PROCEDURE LISTAR_AGENDAMENTOS_POR_PERIODO_E_STATUS
CREATE PROCEDURE listar_agendamentos_por_periodo_e_status(
  IN p_ini DATE, IN p_fim DATE, IN p_status VARCHAR(20)
)
BEGIN
  SELECT
    agendamento_servico.age_id,
    agendamento_servico.age_data,
    agendamento_servico.age_status,
    servico.ser_nome,
    cliente.cli_nome_completo
  FROM agendamento_servico
  JOIN servico
    ON servico.ser_id = agendamento_servico.age_ser_id
  JOIN cliente
    ON cliente.cli_id = agendamento_servico.age_cli_id
  WHERE agendamento_servico.age_data BETWEEN p_ini AND p_fim
    AND agendamento_servico.age_status = p_status
  ORDER BY agendamento_servico.age_data;
END $$

-- 4.9) PROCEDURE ABRIR_DISPUTA
CREATE PROCEDURE abrir_disputa(
  IN p_age_id INT, IN p_cli_id INT, IN p_pre_id INT,
  IN p_motivo VARCHAR(255), IN p_valor FLOAT
)
BEGIN
  INSERT INTO disputa_reembolso
    (dsp_age_id, dsp_cli_id, dsp_pre_id, dsp_motivo, dsp_status,
     dsp_valor_reembolsar, dsp_data_abertura)
  VALUES (p_age_id, p_cli_id, p_pre_id, p_motivo, 'Aberta', p_valor, CURDATE());
END $$

-- 4.10) PROCEDURE FECHAR_DISPUTA
CREATE PROCEDURE fechar_disputa(IN p_dsp_id INT)
BEGIN
  UPDATE disputa_reembolso
     SET dsp_status = 'Resolvida',
         dsp_data_fechamento = CURDATE()
   WHERE dsp_id = p_dsp_id
     AND dsp_status != 'Resolvida';
END $$

-- 4.11) PROCEDURE INSERIR_PAGAMENTO
CREATE PROCEDURE inserir_pagamento(
  IN p_age_id INT, IN p_forma VARCHAR(20), IN p_valor FLOAT,
  IN p_status VARCHAR(20), IN p_ref VARCHAR(60), IN p_data DATE
)
BEGIN
  INSERT INTO pagamento
    (pag_age_id, pag_forma, pag_valor_total, pag_status, pag_referencia_gateway, pag_data)
  VALUES (p_age_id, p_forma, p_valor, p_status, p_ref, p_data);
END $$

-- =========================================================
-- 5) TRIGGERS
-- =========================================================

-- 5.1) TRIGGER_POS_INSERIR_AGENDAMENTO_REGISTRAR_STATUS_INICIAL
CREATE TRIGGER trigger_pos_inserir_agendamento_registrar_status_inicial
AFTER INSERT ON agendamento_servico
FOR EACH ROW
BEGIN
  INSERT INTO historico_status_agendamento
    (his_age_id, his_status_anterior, his_status_novo, his_data_alteracao)
  VALUES
    (NEW.age_id, 'Criado', NEW.age_status, CURDATE());
END $$

-- 5.2) TRIGGER_POS_ATUALIZAR_AGENDAMENTO_REGISTRAR_MUDANCA_DE_STATUS
CREATE TRIGGER trigger_pos_atualizar_agendamento_registrar_mudanca_de_status
AFTER UPDATE ON agendamento_servico
FOR EACH ROW
BEGIN
  IF OLD.age_status != NEW.age_status THEN
    INSERT INTO historico_status_agendamento
      (his_age_id, his_status_anterior, his_status_novo, his_data_alteracao)
    VALUES
      (NEW.age_id, OLD.age_status, NEW.age_status, CURDATE());
  END IF;
END $$

-- 5.3) TRIGGER_POS_INSERIR_AVALIACAO_CRIAR_NOTIFICACAO
CREATE TRIGGER trigger_pos_inserir_avaliacao_criar_notificacao
AFTER INSERT ON avaliacao
FOR EACH ROW
BEGIN
  IF NEW.ava_pre_id IS NOT NULL THEN
    INSERT INTO notificacao
      (not_cli_id, not_pre_id, not_age_id, not_tipo, not_mensagem, not_enviado, not_data)
    VALUES
      (NEW.ava_cli_id, NEW.ava_pre_id, NEW.ava_age_id,
       'Avaliacao', 'Nova avaliacao registrada para prestador.', 0, CURDATE());
  END IF;
END $$

-- 5.4) TRIGGER_POS_INSERIR_PAGAMENTO_CONFIRMADO_CRIAR_NOTIFICACAO
CREATE TRIGGER trigger_pos_inserir_pagamento_confirmado_criar_notificacao
AFTER INSERT ON pagamento
FOR EACH ROW
BEGIN
  IF NEW.pag_status = 'Pago' THEN
    INSERT INTO notificacao
      (not_cli_id, not_pre_id, not_age_id, not_tipo, not_mensagem, not_enviado, not_data)
    VALUES
      ((SELECT age_cli_id FROM agendamento_servico WHERE age_id = NEW.pag_age_id),
       (SELECT age_pre_id FROM agendamento_servico WHERE age_id = NEW.pag_age_id),
       NEW.pag_age_id,
       'Pagamento', 'Pagamento confirmado.', 0, CURDATE());
  END IF;
END $$

-- 5.5) TRIGGER_POS_INSERIR_AGENDAMENTO_NOTIFICAR_PRESTADOR
CREATE TRIGGER trigger_pos_inserir_agendamento_notificar_prestador
AFTER INSERT ON agendamento_servico
FOR EACH ROW
BEGIN
  IF NEW.age_pre_id IS NOT NULL THEN
    INSERT INTO notificacao
      (not_pre_id, not_age_id, not_tipo, not_mensagem, not_enviado, not_data)
    VALUES
      (NEW.age_pre_id, NEW.age_id,
       'Agendamento', 'Novo agendamento atribuído ao prestador.', 0, CURDATE());
  END IF;
END $$

-- 5.6) TRIGGER_POS_INSERIR_DISPUTA_ABERTA_CRIAR_NOTIFICACAO
CREATE TRIGGER trigger_pos_inserir_disputa_aberta_criar_notificacao
AFTER INSERT ON disputa_reembolso
FOR EACH ROW
BEGIN
  IF NEW.dsp_status = 'Aberta' THEN

    IF NEW.dsp_cli_id IS NOT NULL THEN
      INSERT INTO notificacao
        (not_cli_id, not_age_id, not_tipo, not_mensagem, not_enviado, not_data)
      VALUES
        (NEW.dsp_cli_id, NEW.dsp_age_id,
         'Disputa', 'Sua disputa foi aberta.', 0, CURDATE());
    END IF;

    IF NEW.dsp_pre_id IS NOT NULL THEN
      INSERT INTO notificacao
        (not_pre_id, not_age_id, not_tipo, not_mensagem, not_enviado, not_data)
      VALUES
        (NEW.dsp_pre_id, NEW.dsp_age_id,
         'Disputa', 'Uma disputa foi aberta neste agendamento.', 0, CURDATE());
    END IF;

  END IF;
END $$

DELIMITER ;
