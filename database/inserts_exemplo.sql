-- =========================================================
-- INSERTS DE EXEMPLO PARA O BANCO HOMEHERO
-- Execute este script após criar o banco com dbHomeHero.sql
-- =========================================================

USE homehero;

-- =========================================================
-- 0.1) ADMINISTRADOR
-- =========================================================
INSERT INTO admin (adm_nome, adm_email, adm_senha) VALUES
('Administrador', 'admin@homehero.com', 'admin123')
ON DUPLICATE KEY UPDATE adm_nome = adm_nome;

-- Senha padrão: admin123

-- =========================================================
-- 1) ENDEREÇOS
-- =========================================================
INSERT INTO endereco (end_logradouro, end_numero, end_complemento, end_bairro, end_cidade, end_uf, end_cep) VALUES
('Rua das Flores', '123', 'Apto 101', 'Centro', 'São Paulo', 'SP', '01310-100'),
('Avenida Paulista', '1578', 'Sala 45', 'Bela Vista', 'São Paulo', 'SP', '01310-200'),
('Rua Augusta', '500', NULL, 'Consolação', 'São Paulo', 'SP', '01305-000'),
('Avenida Atlântica', '200', 'Cobertura', 'Copacabana', 'Rio de Janeiro', 'RJ', '22021-000'),
('Rua do Ouvidor', '50', NULL, 'Centro', 'Rio de Janeiro', 'RJ', '20040-030'),
('Rua da Praia', '789', 'Casa', 'Barra da Tijuca', 'Rio de Janeiro', 'RJ', '22620-000'),
('Avenida Beira Mar', '1000', 'Apto 302', 'Meireles', 'Fortaleza', 'CE', '60165-121'),
('Rua do Sol', '456', NULL, 'Centro', 'Recife', 'PE', '50020-000'),
('Avenida Boa Viagem', '800', 'Apto 15', 'Boa Viagem', 'Recife', 'PE', '51021-000'),
('Rua das Acácias', '321', 'Casa 2', 'Jardim América', 'Belo Horizonte', 'MG', '30130-000'),
('Avenida Afonso Pena', '2000', 'Sala 10', 'Centro', 'Belo Horizonte', 'MG', '30130-000'),
('Rua dos Carijós', '600', NULL, 'Centro', 'Belo Horizonte', 'MG', '30120-000');

-- =========================================================
-- 2) REGIÕES
-- =========================================================
INSERT INTO regiao (reg_nome, reg_cidade, reg_uf) VALUES
('Grande São Paulo', 'São Paulo', 'SP'),
('Zona Sul RJ', 'Rio de Janeiro', 'RJ'),
('Zona Norte RJ', 'Rio de Janeiro', 'RJ'),
('Centro Fortaleza', 'Fortaleza', 'CE'),
('Zona Sul Recife', 'Recife', 'PE'),
('Centro BH', 'Belo Horizonte', 'MG');

-- =========================================================
-- 3) CATEGORIAS DE SERVIÇO
-- =========================================================
INSERT INTO categoria_servico (cat_nome, cat_descricao) VALUES
('Limpeza', 'Serviços de limpeza residencial e comercial'),
('Reforma', 'Serviços de reforma e construção'),
('Elétrica', 'Serviços elétricos e instalações'),
('Encanamento', 'Serviços de encanamento e hidráulica'),
('Pintura', 'Serviços de pintura residencial e comercial'),
('Jardinagem', 'Serviços de jardinagem e paisagismo'),
('Manutenção', 'Serviços gerais de manutenção'),
('Montagem', 'Montagem de móveis e equipamentos');

-- =========================================================
-- 4) CLIENTES
-- =========================================================
INSERT INTO cliente (cli_nome, cli_cpf, cli_nascimento, cli_senha, end_id, cli_email, cli_telefone) VALUES
('Maria Silva', '123.456.789-00', '1985-05-15', 'senha123', 1, 'maria.silva@email.com', '(11) 98765-4321'),
('João Santos', '234.567.890-11', '1990-08-20', 'senha123', 2, 'joao.santos@email.com', '(11) 97654-3210'),
('Ana Costa', '345.678.901-22', '1988-03-10', 'senha123', 3, 'ana.costa@email.com', '(11) 96543-2109'),
('Carlos Oliveira', '456.789.012-33', '1992-11-25', 'senha123', 4, 'carlos.oliveira@email.com', '(21) 95432-1098'),
('Fernanda Lima', '567.890.123-44', '1987-07-05', 'senha123', 5, 'fernanda.lima@email.com', '(21) 94321-0987'),
('Roberto Alves', '678.901.234-55', '1995-01-30', 'senha123', 6, 'roberto.alves@email.com', '(21) 93210-9876');

-- =========================================================
-- 5) PRESTADORES
-- =========================================================
INSERT INTO prestador (pre_nome, pre_cpf, pre_nascimento, pre_areas, pre_experiencia, pre_certificados, pre_senha, end_id, pre_email, pre_telefone) VALUES
('Pedro Souza', '789.012.345-66', '1980-04-12', 'Limpeza, Manutenção', '10 anos de experiência em limpeza residencial e comercial', 'Certificado em Limpeza Profissional', 'senha123', 7, 'pedro.souza@email.com', '(85) 92109-8765'),
('Lucas Ferreira', '890.123.456-77', '1985-09-18', 'Elétrica, Reforma', '15 anos trabalhando com instalações elétricas', 'Eletricista Certificado CREA', 'senha123', 8, 'lucas.ferreira@email.com', '(81) 91098-7654'),
('Juliana Rocha', '901.234.567-88', '1990-12-22', 'Pintura, Reforma', '8 anos de experiência em pintura residencial', 'Curso de Pintura Profissional', 'senha123', 9, 'juliana.rocha@email.com', '(81) 90987-6543'),
('Marcos Pereira', '012.345.678-99', '1983-06-08', 'Encanamento, Manutenção', '12 anos trabalhando com hidráulica', 'Encanador Certificado', 'senha123', 10, 'marcos.pereira@email.com', '(31) 89876-5432'),
('Patricia Mendes', '123.456.789-10', '1988-02-14', 'Jardinagem, Paisagismo', '7 anos de experiência em jardinagem', 'Curso de Paisagismo', 'senha123', 11, 'patricia.mendes@email.com', '(31) 88765-4321'),
('Ricardo Gomes', '234.567.890-21', '1982-10-28', 'Montagem, Reforma', '9 anos montando móveis e equipamentos', 'Certificado em Montagem', 'senha123', 12, 'ricardo.gomes@email.com', '(31) 87654-3210');

-- =========================================================
-- 6) SERVIÇOS
-- =========================================================
INSERT INTO servico (ser_nome, ser_descricao, ser_preco_base, ser_ativo, cat_id) VALUES
('Limpeza Residencial', 'Limpeza completa de residência', 150.00, TRUE, 1),
('Limpeza Comercial', 'Limpeza de escritórios e lojas', 300.00, TRUE, 1),
('Instalação Elétrica', 'Instalação e manutenção elétrica', 250.00, TRUE, 3),
('Reparo Elétrico', 'Reparo de problemas elétricos', 180.00, TRUE, 3),
('Pintura Interna', 'Pintura de paredes internas', 200.00, TRUE, 5),
('Pintura Externa', 'Pintura de fachadas', 350.00, TRUE, 5),
('Desentupimento', 'Desentupimento de canos e esgotos', 120.00, TRUE, 4),
('Instalação Hidráulica', 'Instalação de encanamento', 280.00, TRUE, 4),
('Poda de Árvores', 'Poda e manutenção de jardins', 100.00, TRUE, 6),
('Paisagismo', 'Projeto e execução de paisagismo', 500.00, TRUE, 6),
('Montagem de Móveis', 'Montagem de móveis planejados', 150.00, TRUE, 8),
('Reforma Completa', 'Reforma completa de ambientes', 5000.00, TRUE, 2);

-- =========================================================
-- 7) REGISTRO DE REGIÃO
-- =========================================================
INSERT INTO registro_regiao (reg_id, cli_id, pre_id, rre_data) VALUES
(1, 1, NULL, '2024-01-15'),
(1, 2, NULL, '2024-01-16'),
(1, 3, NULL, '2024-01-17'),
(2, 4, NULL, '2024-01-18'),
(2, 5, NULL, '2024-01-19'),
(2, 6, NULL, '2024-01-20'),
(4, NULL, 1, '2024-01-10'),
(5, NULL, 2, '2024-01-11'),
(5, NULL, 3, '2024-01-12'),
(6, NULL, 4, '2024-01-13'),
(6, NULL, 5, '2024-01-14'),
(6, NULL, 6, '2024-01-15');

-- =========================================================
-- 8) PRESTADOR SERVIÇO
-- =========================================================
INSERT INTO prestador_servico (pre_id, ser_id, prs_preco, prs_ativo, prs_data) VALUES
(1, 1, 140.00, TRUE, '2024-01-10'),
(1, 2, 280.00, TRUE, '2024-01-10'),
(2, 3, 240.00, TRUE, '2024-01-11'),
(2, 4, 170.00, TRUE, '2024-01-11'),
(3, 5, 190.00, TRUE, '2024-01-12'),
(3, 6, 330.00, TRUE, '2024-01-12'),
(4, 7, 110.00, TRUE, '2024-01-13'),
(4, 8, 270.00, TRUE, '2024-01-13'),
(5, 9, 90.00, TRUE, '2024-01-14'),
(5, 10, 480.00, TRUE, '2024-01-14'),
(6, 11, 140.00, TRUE, '2024-01-15'),
(6, 12, 4800.00, TRUE, '2024-01-15');

-- =========================================================
-- 9) DISPONIBILIDADE PRESTADOR
-- =========================================================
INSERT INTO disponibilidade_prestador (pre_id, dis_dia, dis_janela, dis_ativo) VALUES
(1, 'Segunda', 'Manhã', TRUE),
(1, 'Segunda', 'Tarde', TRUE),
(1, 'Quarta', 'Manhã', TRUE),
(1, 'Quinta', 'Tarde', TRUE),
(2, 'Terça', 'Manhã', TRUE),
(2, 'Terça', 'Tarde', TRUE),
(2, 'Quinta', 'Manhã', TRUE),
(2, 'Sexta', 'Tarde', TRUE),
(3, 'Segunda', 'Tarde', TRUE),
(3, 'Quarta', 'Manhã', TRUE),
(3, 'Quarta', 'Tarde', TRUE),
(3, 'Sexta', 'Manhã', TRUE),
(4, 'Terça', 'Manhã', TRUE),
(4, 'Quarta', 'Tarde', TRUE),
(4, 'Quinta', 'Manhã', TRUE),
(4, 'Sexta', 'Tarde', TRUE),
(5, 'Segunda', 'Manhã', TRUE),
(5, 'Terça', 'Tarde', TRUE),
(5, 'Quinta', 'Manhã', TRUE),
(5, 'Sábado', 'Manhã', TRUE),
(6, 'Terça', 'Manhã', TRUE),
(6, 'Quarta', 'Tarde', TRUE),
(6, 'Quinta', 'Manhã', TRUE),
(6, 'Sexta', 'Tarde', TRUE);

-- =========================================================
-- 10) CERTIFICAÇÃO PRESTADOR
-- =========================================================
INSERT INTO certificacao_prestador (pre_id, cer_titulo, cer_instituicao, cer_data, cer_url) VALUES
(1, 'Limpeza Profissional', 'Instituto de Limpeza', '2020-05-15', 'https://certificados.com/pedro-souza'),
(2, 'Eletricista Certificado', 'CREA-SP', '2015-03-20', 'https://crea.com.br/lucas-ferreira'),
(3, 'Pintura Profissional', 'Escola de Pintura', '2018-08-10', 'https://certificados.com/juliana-rocha'),
(4, 'Encanador Certificado', 'CREA-CE', '2012-11-05', 'https://crea.com.br/marcos-pereira'),
(5, 'Paisagismo', 'Instituto de Paisagismo', '2019-02-18', 'https://certificados.com/patricia-mendes'),
(6, 'Montagem Profissional', 'Escola de Montagem', '2017-07-22', 'https://certificados.com/ricardo-gomes');

-- =========================================================
-- 11) AGENDAMENTOS
-- =========================================================
INSERT INTO agendamento_servico (cli_id, ser_id, pre_id, age_data, age_janela, end_id, age_status, age_valor, age_pago, age_data_cancel, age_motivo) VALUES
(1, 1, 1, '2024-02-01', 'Manhã', 1, 'Agendado', 140.00, FALSE, NULL, NULL),
(2, 3, 2, '2024-02-02', 'Tarde', 2, 'Confirmado', 240.00, FALSE, NULL, NULL),
(3, 5, 3, '2024-02-03', 'Manhã', 3, 'Em Andamento', 190.00, TRUE, NULL, NULL),
(4, 7, 4, '2024-02-04', 'Tarde', 4, 'Concluído', 110.00, TRUE, NULL, NULL),
(5, 9, 5, '2024-02-05', 'Manhã', 5, 'Concluído', 90.00, TRUE, NULL, NULL),
(6, 11, 6, '2024-02-06', 'Tarde', 6, 'Cancelado', 140.00, FALSE, '2024-02-01', 'Cliente desistiu'),
(1, 2, 1, '2024-02-07', 'Manhã', 1, 'Agendado', 280.00, FALSE, NULL, NULL),
(2, 4, 2, '2024-02-08', 'Tarde', 2, 'Confirmado', 170.00, FALSE, NULL, NULL),
(3, 6, 3, '2024-02-09', 'Manhã', 3, 'Agendado', 330.00, FALSE, NULL, NULL),
(4, 8, 4, '2024-02-10', 'Tarde', 4, 'Confirmado', 270.00, FALSE, NULL, NULL);

-- =========================================================
-- 12) PAGAMENTOS
-- =========================================================
INSERT INTO pagamento (age_id, pag_forma, pag_valor, pag_status, pag_ref, pag_data) VALUES
(3, 'Cartão de Crédito', 190.00, 'Pago', 'CC-20240203-001', '2024-02-03'),
(4, 'PIX', 110.00, 'Pago', 'PIX-20240204-002', '2024-02-04'),
(5, 'Cartão de Débito', 90.00, 'Pago', 'CD-20240205-003', '2024-02-05'),
(1, 'Cartão de Crédito', 140.00, 'Pendente', 'CC-20240201-004', '2024-02-01'),
(2, 'PIX', 240.00, 'Pendente', 'PIX-20240202-005', '2024-02-02');

-- =========================================================
-- 13) AVALIAÇÕES
-- =========================================================
INSERT INTO avaliacao (age_id, cli_id, pre_id, ava_nota, ava_coment, ava_data) VALUES
(4, 4, 4, 5, 'Excelente serviço! Muito profissional e pontual.', '2024-02-04'),
(5, 5, 5, 5, 'Adorei o trabalho! Jardim ficou lindo.', '2024-02-05'),
(3, 3, 3, 4, 'Bom trabalho, mas demorou um pouco mais que o esperado.', '2024-02-03'),
(4, 4, 4, 5, 'Recomendo! Serviço de qualidade.', '2024-02-06');

-- =========================================================
-- 14) DISPUTAS
-- =========================================================
INSERT INTO disputa_reembolso (age_id, cli_id, pre_id, dsp_motivo, dsp_status, dsp_valor, dsp_abertura, dsp_fechamento) VALUES
(6, 6, 6, 'Serviço não foi realizado conforme combinado', 'Aberta', 140.00, '2024-02-01', NULL),
(1, 1, 1, 'Prestador não compareceu no horário agendado', 'Resolvida', 140.00, '2024-02-01', '2024-02-02');

-- =========================================================
-- 15) NOTIFICAÇÕES (geradas automaticamente pelos triggers, mas podemos inserir algumas)
-- =========================================================
INSERT INTO notificacao (cli_id, pre_id, age_id, not_tipo, not_msg, not_enviado, not_data) VALUES
(1, 1, 1, 'Agendamento', 'Novo agendamento atribuído ao prestador.', FALSE, '2024-02-01'),
(2, 2, 2, 'Agendamento', 'Novo agendamento atribuído ao prestador.', FALSE, '2024-02-02'),
(4, 4, 4, 'Pagamento', 'Pagamento confirmado.', FALSE, '2024-02-04'),
(4, 4, 4, 'Avaliacao', 'Nova avaliacao registrada para prestador.', FALSE, '2024-02-04'),
(6, 6, 6, 'Disputa', 'Sua disputa foi aberta.', FALSE, '2024-02-01');

-- =========================================================
-- 16) HISTÓRICO DE STATUS (gerado automaticamente pelos triggers, mas podemos inserir alguns)
-- =========================================================
INSERT INTO historico_status_agendamento (age_id, his_status_ant, his_status_novo, his_data) VALUES
(1, 'Criado', 'Agendado', '2024-02-01'),
(2, 'Criado', 'Confirmado', '2024-02-02'),
(3, 'Criado', 'Em Andamento', '2024-02-03'),
(4, 'Criado', 'Concluído', '2024-02-04'),
(5, 'Criado', 'Concluído', '2024-02-05'),
(6, 'Criado', 'Cancelado', '2024-02-01'),
(2, 'Agendado', 'Confirmado', '2024-02-02'),
(3, 'Confirmado', 'Em Andamento', '2024-02-03'),
(3, 'Em Andamento', 'Concluído', '2024-02-03');

-- =========================================================
-- 17) CHAT MENSAGENS
-- =========================================================
INSERT INTO chat_mensagem (age_id, cha_remetente_tipo, cha_remetente_id, cha_destinatario_tipo, cha_destinatario_id, cha_msg, cha_data) VALUES
(1, 'Cliente', 1, 'Prestador', 1, 'Olá, confirmo o agendamento para amanhã às 9h.', '2024-02-01'),
(1, 'Prestador', 1, 'Cliente', 1, 'Perfeito! Estarei lá pontualmente.', '2024-02-01'),
(2, 'Cliente', 2, 'Prestador', 2, 'Preciso confirmar o horário do serviço.', '2024-02-02'),
(2, 'Prestador', 2, 'Cliente', 2, 'Sim, confirmado para às 14h.', '2024-02-02');

-- =========================================================
-- 18) CHATBOT LOG
-- =========================================================
INSERT INTO chatbot_log (cli_id, pre_id, age_id, cbt_pergunta, cbt_resposta, cbt_data) VALUES
(1, NULL, NULL, 'Como faço para agendar um serviço?', 'Você pode agendar um serviço através do nosso sistema. Escolha o serviço desejado e selecione um prestador disponível.', '2024-01-15'),
(2, NULL, NULL, 'Quais são os métodos de pagamento?', 'Aceitamos Cartão de Crédito, Cartão de Débito e PIX.', '2024-01-16'),
(NULL, 1, NULL, 'Como recebo os pagamentos?', 'Os pagamentos são processados automaticamente após a conclusão do serviço.', '2024-01-10');

-- =========================================================
-- FIM DOS INSERTS
-- =========================================================
