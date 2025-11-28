"""
Sistema de Menu Interativo - HomeHero Database
==============================================

Este script fornece uma interface de linha de comando para interagir com o banco de dados
HomeHero através de Views e Stored Procedures MySQL.

Pré-requisitos:
    - Python 3.x
    - mysql-connector-python (pip install mysql-connector-python)
    - MySQL Server com banco HomeHero configurado

Configuração:

set DB_HOST=localhost
set DB_PORT=3306
set DB_USER=root
set DB_PASSWORD=root
set DB_NAME=homehero
python homehero_menu.py

"""

import os
from contextlib import closing
from datetime import date
import warnings
import mysql.connector

# Suprime avisos de deprecação do mysql-connector-python
warnings.filterwarnings("ignore", category=DeprecationWarning)


# ============================================================================
# FUNÇÕES DE CONEXÃO E UTILITÁRIAS
# ============================================================================

def obter_conexao():
    """
    Estabelece conexão com o banco de dados MySQL usando variáveis de ambiente.
    
    Returns:
        mysql.connector.connection: Objeto de conexão MySQL
        
    Raises:
        mysql.connector.Error: Se houver erro na conexão
    """
    host = os.environ.get("DB_HOST", "localhost")
    port = int(os.environ.get("DB_PORT", "3306"))
    user = os.environ.get("DB_USER", "root")
    password = os.environ.get("DB_PASSWORD", "")
    database = os.environ.get("DB_NAME", "homehero")
    
    return mysql.connector.connect(
        host=host,
        port=port,
        user=user,
        password=password,
        database=database,
    )


def imprimir_registros(registros):
    """
    Formata e exibe registros retornados do banco de dados no console.
    Exibe os campos de forma vertical, um campo por linha, ideal para apresentações.
    
    Args:
        registros (list): Lista de dicionários contendo os registros
    """
    if not registros:
        print("\nNenhum registro encontrado.\n")
        return

    print()  # Linha em branco para melhor legibilidade
    
    # Exibe cada registro separadamente
    for idx, registro in enumerate(registros, 1):
        if len(registros) > 1:
            print(f"--- Registro {idx} ---")
        
        # Exibe cada campo em uma linha separada (formato vertical)
        for chave, valor in registro.items():
            valor_formatado = valor if valor is not None else "NULL"
            print(f"{chave}: {valor_formatado}")
        
        # Linha separadora entre registros (exceto no último)
        if idx < len(registros):
            print()

    print(f"\nTotal: {len(registros)} registro(s)")


# ============================================================================
# VIEWS - Consultas de Relatórios
# ============================================================================

def listar_categorias_servicos():
    """
    Lista todas as categorias de serviços usando a view view_lista_categorias.
    """
    # Abre a conexão com o banco de dados.
    # closing(obter_conexao()) garante que a conexão será fechada
    # automaticamente ao final do bloco with.
    with closing(obter_conexao()) as conexao, \
         closing(conexao.cursor(dictionary=True)) as cursor:
        # Cria um cursor em modo "dictionary=True", ou seja, cada linha
        # retornada vira um dicionário: {"cat_id": 1, "cat_nome": "Limpeza", ...}

        # Executa um SELECT simples sobre a view que já está criada no MySQL.
        # A view encapsula a lógica da consulta; aqui apenas lemos os dados.
        cursor.execute(
            "SELECT * FROM view_lista_categorias ORDER BY cat_nome"
        )

        # Recupera todas as linhas retornadas pela view.
        registros = cursor.fetchall()

        # Usa a função utilitária para imprimir os resultados no console.
        imprimir_registros(registros)


def listar_servicos_com_categorias():
    """
    Lista os serviços cadastrados junto com suas categorias,
    usando a view view_lista_servicos.
    """
    # Abre conexão e cria cursor em modo dicionário,
    # garantindo o fechamento automático com closing(...)
    with closing(obter_conexao()) as conexao, \
         closing(conexao.cursor(dictionary=True)) as cursor:

        # Executa a consulta na view que já junta serviço + categoria.
        cursor.execute(
            "SELECT * FROM view_lista_servicos ORDER BY ser_nome"
        )

        # Busca todas as linhas retornadas pela view.
        registros = cursor.fetchall()

        # Imprime os registros de forma organizada no terminal.
        imprimir_registros(registros)


def listar_servicos_por_prestador():
    """Consulta a view que lista os serviços oferecidos por cada prestador."""
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute("SELECT * FROM view_servicos_oferecidos_por_prestadores ORDER BY pre_nome, ser_nome")
        imprimir_registros(cursor.fetchall())


def resumo_agendamentos_por_status():
    """Consulta a view que exibe resumo de agendamentos agrupados por status."""
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute("SELECT * FROM view_agendamentos_por_status ORDER BY total_agendamentos DESC")
        imprimir_registros(cursor.fetchall())


def resumo_agendamentos_por_periodo():
    """Consulta a view que exibe resumo de agendamentos por período (manhã/tarde/noite)."""
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute("SELECT * FROM view_agendamentos_por_periodo ORDER BY total_agendamentos DESC")
        imprimir_registros(cursor.fetchall())


def resumo_agendamentos_por_regiao():
    """Consulta a view que exibe resumo de agendamentos agrupados por região do cliente."""
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute("SELECT * FROM view_agendamentos_por_regiao_cliente ORDER BY total_agendamentos DESC")
        imprimir_registros(cursor.fetchall())


def resumo_pagamentos_por_status():
    """Consulta a view que exibe resumo de pagamentos agrupados por status."""
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute("SELECT * FROM view_pagamentos_por_status ORDER BY quantidade_pagamentos DESC")
        imprimir_registros(cursor.fetchall())


def media_avaliacao_por_prestador():
    """Consulta a view que exibe a média de avaliações por prestador."""
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute("SELECT * FROM view_media_de_avaliacao_por_prestador ORDER BY media_notas DESC")
        imprimir_registros(cursor.fetchall())


def listar_disputas_abertas():
    """Consulta a view que lista todas as disputas de reembolso abertas."""
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute("SELECT * FROM view_disputas_abertas ORDER BY dsp_abertura DESC")
        imprimir_registros(cursor.fetchall())


def listar_disponibilidade_prestadores():
    """Consulta a view que lista a disponibilidade de cada prestador."""
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute("SELECT * FROM view_disponibilidade_de_prestadores ORDER BY pre_nome, dis_dia")
        imprimir_registros(cursor.fetchall())


def resumo_receita_por_prestador():
    """Consulta a view que exibe resumo de receita total por prestador."""
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute("SELECT * FROM view_receita_por_prestador ORDER BY receita_total DESC")
        imprimir_registros(cursor.fetchall())


def resumo_receita_por_periodo():
    """Consulta a view que exibe resumo de receita agrupada por período (ano/mês)."""
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute("SELECT * FROM view_receita_por_periodo ORDER BY ano DESC, mes DESC")
        imprimir_registros(cursor.fetchall())


def listar_historico_agendamentos():
    """Consulta a view que exibe o histórico completo de mudanças de status dos agendamentos."""
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute("SELECT * FROM view_historico_status_agendamento ORDER BY his_data DESC, his_id DESC")
        imprimir_registros(cursor.fetchall())


# ============================================================================
# STORED PROCEDURES - Operações de Dados
# ============================================================================

def inserir_cliente_procedure():
    """
    Insere um novo cliente no banco de dados através da stored procedure inserir_cliente.
    Toda a coleta de dados é feita via input no terminal.
    """
    # Título da operação para o usuário
    print("=== Inserir Cliente ===")

    # Leitura dos dados básicos do cliente
    nome = input("Nome: ").strip()
    cpf = input("CPF: ").strip()
    
    # Loop para garantir que a data seja informada em formato válido
    while True:
        try:
            # Converte a string digitada em um objeto date (formato YYYY-MM-DD)
            nascimento = date.fromisoformat(
                input("Data de nascimento (YYYY-MM-DD): ").strip()
            )
            break  # sai do loop se a conversão der certo
        except ValueError:
            # Caso o usuário digite em formato inválido
            print("Data inválida, use o formato YYYY-MM-DD.")
    
    # Senha de acesso do cliente
    senha = input("Senha: ").strip()
    
    # Dados do endereço associados ao cliente
    print("\n--- Dados do Endereço ---")
    end_logradouro  = input("Logradouro: ").strip()
    end_numero      = input("Número: ").strip()
    end_complemento = input("Complemento (opcional): ").strip()
    end_bairro      = input("Bairro: ").strip()
    end_cidade      = input("Cidade: ").strip()
    # UF em letras maiúsculas (ex.: SP, RJ)
    end_uf          = input("UF (2 letras): ").strip().upper()
    end_cep         = input("CEP: ").strip()
    
    # Dados de contato
    email    = input("\nE-mail: ").strip()
    telefone = input("Telefone: ").strip()

    # Abre conexão e cursor; closing(...) garante fechamento automático
    with closing(obter_conexao()) as conexao, \
         closing(conexao.cursor()) as cursor:
        # Chama a stored procedure 'inserir_cliente' passando todos
        # os parâmetros na mesma ordem em que foram definidos no MySQL.
        cursor.callproc(
            "inserir_cliente",
            [
                nome, cpf, nascimento, senha, 
                end_logradouro, end_numero, end_complemento, end_bairro,
                end_cidade, end_uf, end_cep,
                email, telefone
            ],
        )

        # Confirma a transação no banco
        conexao.commit()
        print("\nCliente inserido com sucesso.")


def inserir_prestador_procedure():
    """
    Insere um novo prestador no banco de dados através da stored procedure.
    A procedure cria automaticamente o endereço associado.
    Solicita todos os dados necessários via input do usuário.
    """
    print("=== Inserir Prestador ===")
    nome = input("Nome: ").strip()
    cpf = input("CPF: ").strip()
    
    while True:
        try:
            nascimento = date.fromisoformat(input("Data de nascimento (YYYY-MM-DD): ").strip())
            break
        except ValueError:
            print("Data inválida, use o formato YYYY-MM-DD.")
    
    areas = input("Áreas de atuação: ").strip()
    experiencia = input("Experiência: ").strip()
    certificados = input("Certificados: ").strip()
    senha = input("Senha: ").strip()
    
    # Dados do endereço (a procedure cria o endereço automaticamente)
    print("\n--- Dados do Endereço ---")
    end_logradouro = input("Logradouro: ").strip()
    end_numero = input("Número: ").strip()
    end_complemento = input("Complemento (opcional): ").strip()
    end_bairro = input("Bairro: ").strip()
    end_cidade = input("Cidade: ").strip()
    end_uf = input("UF (2 letras): ").strip().upper()
    end_cep = input("CEP: ").strip()
    
    email = input("\nE-mail: ").strip()
    telefone = input("Telefone: ").strip()

    with closing(obter_conexao()) as conexao, closing(conexao.cursor()) as cursor:
        cursor.callproc(
            "inserir_prestador",
            [nome, cpf, nascimento, areas, experiencia, certificados, senha,
             end_logradouro, end_numero, end_complemento, end_bairro, end_cidade, end_uf, end_cep,
             email, telefone],
        )
        conexao.commit()
        print("\nPrestador inserido com sucesso.")


def buscar_dados_cliente():
    """
    Busca e exibe os dados pessoais completos de um cliente pelo ID.
    Utiliza a stored procedure buscar_dados_pessoais_cliente.
    """
    # Loop para garantir que o usuário informe um número inteiro válido
    while True:
        try:
            cli_id = int(input("Informe o ID do cliente: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    # Abre conexão e cursor em modo dicionário para receber
    # cada linha como um dict (chave = nome da coluna)
    with closing(obter_conexao()) as conexao, \
         closing(conexao.cursor(dictionary=True)) as cursor:
        # Chamada da stored procedure no MySQL
        cursor.callproc("buscar_dados_pessoais_cliente", [cli_id])

        # A procedure pode retornar um ou mais conjuntos de resultados.
        # Percorremos cada um deles e imprimimos com a função utilitária.
        for resultado in cursor.stored_results():
            registros = resultado.fetchall()
            imprimir_registros(registros)


def listar_agendamentos_cliente_procedure():
    """
    Lista todos os agendamentos de um cliente específico pelo ID.
    Utiliza a stored procedure listar_agendamentos_por_id_de_cliente.
    """
    while True:
        try:
            cli_id = int(input("Informe o ID do cliente: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.callproc("listar_agendamentos_por_id_de_cliente", [cli_id])
        for resultado in cursor.stored_results():
            imprimir_registros(resultado.fetchall())


def buscar_dados_prestador():
    """
    Busca e exibe os dados pessoais completos de um prestador pelo ID.
    Utiliza a stored procedure buscar_dados_pessoais_prestador.
    """
    while True:
        try:
            pre_id = int(input("Informe o ID do prestador: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.callproc("buscar_dados_pessoais_prestador", [pre_id])
        for resultado in cursor.stored_results():
            imprimir_registros(resultado.fetchall())


def buscar_relacionamentos_cliente():
    """
    Busca e exibe todos os relacionamentos de um cliente (agendamentos, pagamentos, disputas, avaliações).
    Utiliza a stored procedure buscar_relacionamentos_cliente.
    """
    while True:
        try:
            cli_id = int(input("Informe o ID do cliente: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.callproc("buscar_relacionamentos_cliente", [cli_id])
        for resultado in cursor.stored_results():
            imprimir_registros(resultado.fetchall())


def buscar_relacionamentos_prestador():
    """
    Busca e exibe todos os relacionamentos de um prestador (agendamentos, pagamentos, disputas, avaliações).
    Utiliza a stored procedure buscar_relacionamentos_prestador.
    """
    while True:
        try:
            pre_id = int(input("Informe o ID do prestador: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.callproc("buscar_relacionamentos_prestador", [pre_id])
        for resultado in cursor.stored_results():
            imprimir_registros(resultado.fetchall())


def pesquisar_clientes_por_nome():
    """
    Pesquisa clientes por nome exato.
    Utiliza a stored procedure pesquisar_clientes_por_nome_exato.
    """
    nome = input("Informe o nome do cliente (busca exata): ").strip()
    
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.callproc("pesquisar_clientes_por_nome_exato", [nome])
        for resultado in cursor.stored_results():
            imprimir_registros(resultado.fetchall())


def inserir_agendamento_procedure():
    """
    Cria um novo agendamento de serviço através da stored procedure.
    Solicita todos os dados necessários via input do usuário.
    """
    print("=== Inserir Agendamento de Serviço ===")
    
    while True:
        try:
            cli_id = int(input("ID do cliente: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    while True:
        try:
            ser_id = int(input("ID do serviço: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    while True:
        try:
            pre_id = int(input("ID do prestador: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    while True:
        try:
            data_agendamento = date.fromisoformat(input("Data do agendamento (YYYY-MM-DD): ").strip())
            break
        except ValueError:
            print("Data inválida, use o formato YYYY-MM-DD.")
    
    periodo = input("Período (ex: Manhã): ").strip()
    
    while True:
        try:
            end_id = int(input("ID do endereço do atendimento: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    valor = float(input("Valor (use ponto para decimais): ").strip())

    with closing(obter_conexao()) as conexao, closing(conexao.cursor()) as cursor:
        cursor.callproc(
            "inserir_agendamento_de_servico",
            [cli_id, ser_id, pre_id, data_agendamento, periodo, end_id, valor],
        )
        conexao.commit()
        print("\nAgendamento criado com sucesso (status inicial: Pendente).")


def cancelar_agendamento_procedure():
    """
    Cancela um agendamento existente através da stored procedure.
    Solicita o ID do agendamento e motivo do cancelamento.
    """
    print("=== Cancelar Agendamento ===")
    
    while True:
        try:
            age_id = int(input("ID do agendamento: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    motivo = input("Motivo do cancelamento: ").strip()

    with closing(obter_conexao()) as conexao, closing(conexao.cursor()) as cursor:
        cursor.callproc("cancelar_agendamento_de_servico", [age_id, motivo])
        conexao.commit()
        print("\nAgendamento cancelado com sucesso.")


def confirmar_agendamento_procedure():
    """
    Confirma um agendamento existente através da stored procedure.
    Solicita apenas o ID do agendamento.
    """
    print("=== Confirmar Agendamento ===")
    
    while True:
        try:
            age_id = int(input("ID do agendamento: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")

    with closing(obter_conexao()) as conexao, closing(conexao.cursor()) as cursor:
        cursor.callproc("confirmar_agendamento_de_servico", [age_id])
        conexao.commit()
        print("\nAgendamento confirmado com sucesso.")


def listar_agendamentos_por_periodo_status():
    """
    Lista agendamentos filtrados por período de datas e status.
    Utiliza a stored procedure listar_agendamentos_por_periodo_e_status.
    Solicita data inicial, data final e status.
    """
    print("=== Listar Agendamentos por Período e Status ===")
    
    while True:
        try:
            data_inicio = date.fromisoformat(input("Data inicial (YYYY-MM-DD): ").strip())
            break
        except ValueError:
            print("Data inválida, use o formato YYYY-MM-DD.")
    
    while True:
        try:
            data_fim = date.fromisoformat(input("Data final (YYYY-MM-DD): ").strip())
            break
        except ValueError:
            print("Data inválida, use o formato YYYY-MM-DD.")
    
    status = input("Status: ").strip()
    
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.callproc("listar_agendamentos_por_periodo_e_status", [data_inicio, data_fim, status])
        for resultado in cursor.stored_results():
            imprimir_registros(resultado.fetchall())


def registrar_avaliacao_prestador():
    """
    Registra uma avaliação de prestador após um agendamento.
    Utiliza a stored procedure registrar_avaliacao_de_prestador.
    Solicita ID do agendamento, ID do cliente, ID do prestador, nota (1-5) e comentário opcional.
    """
    print("=== Registrar Avaliação de Prestador ===")
    
    while True:
        try:
            age_id = int(input("ID do agendamento: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    while True:
        try:
            cli_id = int(input("ID do cliente: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    while True:
        try:
            pre_id = int(input("ID do prestador: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    while True:
        try:
            nota = int(input("Nota (1-5): ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    comentario = input("Comentário (opcional): ").strip()

    with closing(obter_conexao()) as conexao, closing(conexao.cursor()) as cursor:
        cursor.callproc("registrar_avaliacao_de_prestador", [age_id, cli_id, pre_id, nota, comentario])
        conexao.commit()
        print("\nAvaliação registrada com sucesso.")


def abrir_disputa_reembolso():
    """
    Abre uma disputa de reembolso para um agendamento.
    Utiliza a stored procedure abrir_disputa.
    Solicita ID do agendamento, ID do cliente, ID do prestador, motivo e valor.
    """
    print("=== Abrir Disputa de Reembolso ===")
    
    while True:
        try:
            age_id = int(input("ID do agendamento: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    while True:
        try:
            cli_id = int(input("ID do cliente: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    while True:
        try:
            pre_id = int(input("ID do prestador: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    motivo = input("Motivo da disputa: ").strip()
    
    while True:
        try:
            valor = float(input("Valor do reembolso (use ponto para decimais): ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")

    with closing(obter_conexao()) as conexao, closing(conexao.cursor()) as cursor:
        cursor.callproc("abrir_disputa", [age_id, cli_id, pre_id, motivo, valor])
        conexao.commit()
        print("\nDisputa aberta com sucesso.")


def fechar_disputa_reembolso():
    """
    Fecha uma disputa de reembolso existente.
    Utiliza a stored procedure fechar_disputa.
    Solicita apenas o ID da disputa (a procedure marca como 'Resolvida' automaticamente).
    """
    print("=== Fechar Disputa de Reembolso ===")
    
    while True:
        try:
            dis_id = int(input("ID da disputa: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")

    with closing(obter_conexao()) as conexao, closing(conexao.cursor()) as cursor:
        cursor.callproc("fechar_disputa", [dis_id])
        conexao.commit()
        print("\nDisputa fechada com sucesso.")


def inserir_pagamento_agendamento():
    """
    Insere um pagamento associado a um agendamento.
    Utiliza a stored procedure inserir_pagamento.
    Solicita ID do agendamento, forma de pagamento, valor, status, referência e data.
    """
    print("=== Inserir Pagamento de Agendamento ===")
    
    while True:
        try:
            age_id = int(input("ID do agendamento: ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")
    
    forma = input("Forma de pagamento: ").strip()
    
    while True:
        try:
            valor = float(input("Valor do pagamento (use ponto para decimais): ").strip())
            break
        except ValueError:
            print("Valor inválido, tente novamente.")

    status = input("Status do pagamento: ").strip()
    referencia = input("Referência do pagamento (opcional): ").strip()

    while True:
        try:
            data_pagamento = date.fromisoformat(input("Data do pagamento (YYYY-MM-DD): ").strip())
            break
        except ValueError:
            print("Data inválida, use o formato YYYY-MM-DD.")

    with closing(obter_conexao()) as conexao, closing(conexao.cursor()) as cursor:
        cursor.callproc("inserir_pagamento", [age_id, forma, valor, status, referencia, data_pagamento])
        conexao.commit()
        print("\nPagamento inserido com sucesso.")


# ============================================================================
# INTERFACE DO MENU
# ============================================================================

def mostrar_menu():
    """Exibe o menu principal com todas as opções disponíveis."""
    print("===== Menu HomeHero MySQL =====")
    print("\n--- VIEWS ---")
    print("1  - Listar categorias de serviços")
    print("2  - Listar serviços cadastrados com suas categorias")
    print("3  - Listar serviços oferecidos por cada prestador")
    print("4  - Resumo de agendamentos por status")
    print("5  - Resumo de agendamentos por período")
    print("6  - Resumo de agendamentos por região do cliente")
    print("7  - Resumo de pagamentos por status")
    print("8  - Média de avaliação por prestador")
    print("9  - Listar disputas abertas")
    print("10 - Listar disponibilidade de prestadores")
    print("11 - Resumo de receita por prestador")
    print("12 - Resumo de receita por período")
    print("13 - Histórico de agendamentos (mudanças de status)")
    print("\n--- PROCEDURES ---")
    print("14 - Inserir cliente")
    print("15 - Inserir prestador")
    print("16 - Buscar dados pessoais do cliente (por ID)")
    print("17 - Listar agendamentos de um cliente (por ID)")
    print("18 - Buscar dados pessoais do prestador (por ID)")
    print("19 - Inserir novo agendamento de serviço")
    print("20 - Cancelar agendamento de serviço")
    print("21 - Confirmar agendamento de serviço")
    print("22 - Listar agendamentos por período e status")
    print("23 - Registrar avaliação de prestador")
    print("24 - Abrir disputa de reembolso")
    print("25 - Fechar disputa de reembolso")
    print("26 - Inserir pagamento de agendamento")
    print("27 - Pesquisar clientes por nome exato")
    print("28 - Buscar relacionamentos do cliente (por ID)")
    print("29 - Buscar relacionamentos do prestador (por ID)")
    print("\n0  - Sair")


# Dicionário que mapeia opções do menu para suas respectivas funções
OPCOES = {
    # Views
    "1": listar_categorias_servicos,
    "2": listar_servicos_com_categorias,
    "3": listar_servicos_por_prestador,
    "4": resumo_agendamentos_por_status,
    "5": resumo_agendamentos_por_periodo,
    "6": resumo_agendamentos_por_regiao,
    "7": resumo_pagamentos_por_status,
    "8": media_avaliacao_por_prestador,
    "9": listar_disputas_abertas,
    "10": listar_disponibilidade_prestadores,
    "11": resumo_receita_por_prestador,
    "12": resumo_receita_por_periodo,
    "13": listar_historico_agendamentos,
    # Procedures
    "14": inserir_cliente_procedure,
    "15": inserir_prestador_procedure,
    "16": buscar_dados_cliente,
    "17": listar_agendamentos_cliente_procedure,
    "18": buscar_dados_prestador,
    "19": inserir_agendamento_procedure,
    "20": cancelar_agendamento_procedure,
    "21": confirmar_agendamento_procedure,
    "22": listar_agendamentos_por_periodo_status,
    "23": registrar_avaliacao_prestador,
    "24": abrir_disputa_reembolso,
    "25": fechar_disputa_reembolso,
    "26": inserir_pagamento_agendamento,
    "27": pesquisar_clientes_por_nome,
    "28": buscar_relacionamentos_cliente,
    "29": buscar_relacionamentos_prestador,
}


def main():
    """
    Função principal que executa o loop do menu interativo.
    Gerencia a exibição do menu, captura de escolhas do usuário e execução
    das operações correspondentes.
    """
    while True:
        mostrar_menu()
        escolha = input("\nEscolha uma opção: ").strip()
        
        # Opção para sair do programa
        if escolha == "0":
            print("Encerrando...")
            break

        # Verifica se a opção escolhida existe
        acao = OPCOES.get(escolha)
        if not acao:
            print("Opção inválida.\n")
            continue

        # Executa a função correspondente à opção escolhida
        try:
            acao()
            # Pausa para o usuário visualizar os resultados antes do menu reaparecer
            input("\nPressione Enter para continuar...")
            print()
        except mysql.connector.Error as erro:
            # Tratamento específico para erros de banco de dados
            print(f"\nErro de banco de dados: {erro}\n")
            if "Access denied" in str(erro):
                print("Dica: Configure as variáveis de ambiente:")
                print("   set DB_HOST=localhost")
                print("   set DB_PORT=3306")
                print("   set DB_USER=root")
                print("   set DB_PASSWORD=sua_senha")
                print("   set DB_NAME=homehero\n")
            input("\nPressione Enter para continuar...")
            print()
        except Exception as erro_generico:
            # Tratamento genérico para outros erros
            print(f"\nErro inesperado: {erro_generico}\n")
            input("\nPressione Enter para continuar...")
            print()


if __name__ == "__main__":
    main()
