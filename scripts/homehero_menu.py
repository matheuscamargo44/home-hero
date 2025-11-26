"""
Menu simples para executar procedures e views do banco HomeHero.

Pré-requisitos:
    pip install mysql-connector-python

Use variáveis de ambiente para configurar a conexão:
    DB_HOST, DB_PORT, DB_USER, DB_PASSWORD, DB_NAME
"""

# Importa o módulo os para ler variáveis de ambiente.
import os
# Context manager utilitário para fechar conexões automaticamente.
from contextlib import closing
# date é usado para converter strings do menu em datas válidas.
from datetime import date

# Driver oficial MySQL para Python.
import mysql.connector


def obter_conexao():
    """Abre uma conexão usando mysql-connector-python."""
    return mysql.connector.connect(
        host=os.environ.get("DB_HOST", "localhost"),
        port=int(os.environ.get("DB_PORT", "3306")),
        user=os.environ.get("DB_USER", "root"),
        password=os.environ.get("DB_PASSWORD", ""),
        database=os.environ.get("DB_NAME", "homehero"),
    )


def imprimir_registros(registros):
    # Caso não existam registros, informa rapidamente e sai.
    if not registros:
        print("Nenhum registro encontrado.\n")
        return

    # Percorre cada registro retornado e imprime os campos no console.
    for registro in registros:
        linha = " | ".join(f"{chave}: {valor}" for chave, valor in registro.items())
        print(linha)
    print()


def listar_clientes_view():
    # 1) Abre conexão com o banco e executa a view dos clientes.
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute(
            "SELECT * FROM view_dados_de_clientes ORDER BY `Nome do cliente`"
        )
        imprimir_registros(cursor.fetchall())


def inserir_cliente_procedure():
    print("=== inserir_cliente ===")
    # Coleta cada campo necessário para a procedure.
    nome = input("Nome: ").strip()
    cpf = input("CPF: ").strip()
    nascimento = ler_data("Data de nascimento (YYYY-MM-DD): ")
    senha = input("Senha: ").strip()
    endereco_id = ler_inteiro("ID do endereço: ")
    email = input("E-mail: ").strip()
    telefone = input("Telefone: ").strip()

    # 1) Executa a procedure inserir_cliente com os parâmetros coletados.
    with closing(obter_conexao()) as conexao, closing(conexao.cursor()) as cursor:
        cursor.callproc(
            "inserir_cliente",
            [nome, cpf, nascimento, senha, endereco_id, email, telefone],
        )
        # 2) Confirma a transação para persistir os dados.
        conexao.commit()
        print("Cliente inserido com sucesso via procedure.\n")


def listar_agendamentos_cliente():
    cli_id = ler_inteiro("Informe o ID do cliente: ")
    # 1) Consulta a view completa de agendamentos filtrando pelo cliente.
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute(
            """
            SELECT *
            FROM view_agendamentos_completo
            WHERE `ID do cliente` = %s
            ORDER BY `Data do agendamento` DESC
            """,
            (cli_id,),
        )
        imprimir_registros(cursor.fetchall())


def criar_agendamento_procedure():
    print("=== inserir_agendamento_de_servico ===")
    # Coleta todos os parâmetros da procedure de agendamentos.
    # Cada input corresponde diretamente aos campos exigidos no banco.
    cli_id = ler_inteiro("ID do cliente: ")
    ser_id = ler_inteiro("ID do serviço: ")
    pre_id = ler_inteiro("ID do prestador: ")
    data_agendamento = ler_data("Data do agendamento (YYYY-MM-DD): ")
    periodo = input("Período (ex: Manhã): ").strip()
    end_id = ler_inteiro("ID do endereço do atendimento: ")
    status = input("Status inicial: ").strip()
    valor = float(input("Valor (use ponto para decimais): ").strip())

    # 1) Invoca a procedure inserir_agendamento_de_servico.
    with closing(obter_conexao()) as conexao, closing(conexao.cursor()) as cursor:
        cursor.callproc(
            "inserir_agendamento_de_servico",
            [cli_id, ser_id, pre_id, data_agendamento, periodo, end_id, status, valor],
        )
        # 2) Dá commit para que as triggers disparem no banco.
        conexao.commit()
        print("Agendamento criado. Triggers de status e notificação serão executadas automaticamente.\n")


def ver_historico_agendamento():
    age_id = ler_inteiro("Informe o ID do agendamento: ")
    # 1) Lê o histórico completo da view correspondente.
    with closing(obter_conexao()) as conexao, closing(conexao.cursor(dictionary=True)) as cursor:
        cursor.execute(
            """
            SELECT *
            FROM view_historico_status_completo
            WHERE `ID do agendamento` = %s
            ORDER BY `Data da alteração` DESC
            """,
            (age_id,),
        )
        imprimir_registros(cursor.fetchall())


def ler_inteiro(pergunta):
    # Loop até que o usuário informe um número válido.
    while True:
        try:
            return int(input(pergunta).strip())
        except ValueError:
            print("Valor inválido, tente novamente.")


def ler_data(pergunta):
    # Converte o input textual em um objeto date, repetindo em caso de erro.
    while True:
        try:
            return date.fromisoformat(input(pergunta).strip())
        except ValueError:
            print("Data inválida, use o formato YYYY-MM-DD.")


def mostrar_menu():
    # Imprime o menu textual disponível no terminal.
    print("===== Menu HomeHero MySQL =====")
    print("1 - Listar clientes (view)")
    print("2 - Inserir cliente (procedure)")
    print("3 - Listar agendamentos de um cliente (view)")
    print("4 - Criar agendamento (procedure)")
    print("5 - Ver histórico de um agendamento (view)")
    print("0 - Sair")


OPCOES = {
    "1": listar_clientes_view,
    "2": inserir_cliente_procedure,
    "3": listar_agendamentos_cliente,
    "4": criar_agendamento_procedure,
    "5": ver_historico_agendamento,
}


def main():
    # Loop principal do menu textual.
    while True:
        mostrar_menu()
        escolha = input("Escolha uma opção: ").strip()
        if escolha == "0":
            print("Encerrando...")
            break

        acao = OPCOES.get(escolha)
        if not acao:
            print("Opção inválida.\n")
            continue

        try:
            # 1) Executa a função associada à opção escolhida.
            acao()
        except mysql.connector.Error as erro:
            print(f"Erro de banco: {erro}\n")
        except Exception as erro_generico:
            print(f"Erro inesperado: {erro_generico}\n")


if __name__ == "__main__":
    main()

