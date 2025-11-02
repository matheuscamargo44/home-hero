# 💻 EXEMPLOS PRÁTICOS PARA DEMONSTRAÇÃO
## Script de Código para Apresentação ao Vivo

---

## 📝 EXEMPLO 1: Criar e Salvar um Cliente

```java
// ============================================
// DEMONSTRAÇÃO 1: Criar um Cliente
// ============================================

// 1. Criar objeto Java
Cliente cliente = new Cliente();
cliente.setNomeCompleto("João Silva");
cliente.setCpf("123.456.789-00");
cliente.setDataNascimento(LocalDate.of(1990, 5, 15));
cliente.setFormaPagamentoPreferida("PIX");

// 2. Salvar no banco (SQL gerado automaticamente)
Cliente clienteSalvo = clienteRepository.save(cliente);

// SQL executado (com show-sql=true):
// Hibernate: INSERT INTO cliente 
//            (cli_nome_completo, cli_cpf, cli_data_nascimento, cli_forma_pagamento_preferida) 
//            VALUES (?, ?, ?, ?)
// Hibernate: binding parameter [1] as [VARCHAR] - [João Silva]
// Hibernate: binding parameter [2] as [VARCHAR] - [123.456.789-00]
// ...

// 3. O banco gerou o ID automaticamente
System.out.println("Cliente criado com ID: " + clienteSalvo.getId());
// Output: Cliente criado com ID: 1
```

**Ponto para enfatizar:**
- ✅ Não escrevemos SQL manualmente
- ✅ Spring Boot gerou o SQL automaticamente
- ✅ O ID foi gerado automaticamente pelo banco (AUTO_INCREMENT)

---

## 📝 EXEMPLO 2: Buscar Cliente por CPF

```java
// ============================================
// DEMONSTRAÇÃO 2: Buscar Cliente por CPF
// ============================================

// Buscar cliente por CPF
Optional<Cliente> clienteOpt = clienteRepository.findByCpf("123.456.789-00");

// SQL gerado automaticamente:
// Hibernate: SELECT * FROM cliente WHERE cli_cpf = ?

if (clienteOpt.isPresent()) {
    Cliente cliente = clienteOpt.get();
    System.out.println("Cliente encontrado: " + cliente.getNomeCompleto());
    // Output: Cliente encontrado: João Silva
} else {
    System.out.println("Cliente não encontrado");
}

// Verificar se CPF existe (sem buscar o objeto inteiro)
boolean existe = clienteRepository.existsByCpf("123.456.789-00");
// SQL: SELECT COUNT(*) > 0 FROM cliente WHERE cli_cpf = ?

System.out.println("CPF existe? " + existe);
// Output: CPF existe? true
```

**Ponto para enfatizar:**
- ✅ Método `findByCpf()` criado automaticamente
- ✅ Spring gera SQL baseado no nome do método
- ✅ `Optional` evita NullPointerException

---

## 📝 EXEMPLO 3: Criar Relacionamento (Cliente + Email)

```java
// ============================================
// DEMONSTRAÇÃO 3: Criar Relacionamento 1:N
// ============================================

// 1. Buscar cliente existente
Cliente cliente = clienteRepository.findById(1).orElse(null);

// 2. Criar email
Email email = new Email();
email.setEnderecoEmail("joao@gmail.com");
email.setCliente(cliente);  // ← Define o relacionamento

// 3. Salvar email (chave estrangeira criada automaticamente)
emailRepository.save(email);

// SQL executado:
// Hibernate: INSERT INTO email (ema_endereco_email, ema_cli_id) 
//        VALUES (?, ?)
// Hibernate: binding parameter [1] as [VARCHAR] - [joao@gmail.com]
// Hibernate: binding parameter [2] as [INTEGER] - [1]

// 4. Buscar todos os emails do cliente (lazy loading)
List<Email> emails = clienteRepository.findById(1).get().getEmails();
// SQL: SELECT * FROM email WHERE ema_cli_id = ?

for (Email e : emails) {
    System.out.println("Email: " + e.getEnderecoEmail());
}
// Output: Email: joao@gmail.com
```

**Ponto para enfatizar:**
- ✅ Relacionamento criado automaticamente
- ✅ Chave estrangeira (`ema_cli_id`) criada pelo JPA
- ✅ Lazy loading: emails só são carregados quando pedidos

---

## 📝 EXEMPLO 4: Criar Cliente Completo (Cliente + Endereço + Email)

```java
// ============================================
// DEMONSTRAÇÃO 4: Criar Cliente Completo
// ============================================

// 1. Criar endereço
Endereco endereco = new Endereco();
endereco.setLogradouro("Rua das Flores");
endereco.setNumero("123");
endereco.setBairro("Centro");
endereco.setCidade("São Paulo");
endereco.setUf("SP");
endereco.setCep("01234-567");
Endereco enderecoSalvo = enderecoRepository.save(endereco);
// SQL: INSERT INTO endereco (...)

// 2. Criar cliente com endereço
Cliente cliente = new Cliente();
cliente.setNomeCompleto("Maria Santos");
cliente.setCpf("987.654.321-00");
cliente.setEndereco(enderecoSalvo);  // ← Relacionamento N:1
Cliente clienteSalvo = clienteRepository.save(cliente);
// SQL: INSERT INTO cliente (..., cli_endereco_id) VALUES (..., 1)

// 3. Criar emails para o cliente
Email email1 = new Email();
email1.setEnderecoEmail("maria@gmail.com");
email1.setCliente(clienteSalvo);
emailRepository.save(email1);

Email email2 = new Email();
email2.setEnderecoEmail("maria@hotmail.com");
email2.setCliente(clienteSalvo);
emailRepository.save(email2);

// 4. Verificar relacionamentos
System.out.println("Cliente: " + clienteSalvo.getNomeCompleto());
System.out.println("Endereço: " + clienteSalvo.getEndereco().getLogradouro());
System.out.println("Emails: " + clienteSalvo.getEmails().size());
// Output: 
// Cliente: Maria Santos
// Endereço: Rua das Flores
// Emails: 2
```

---

## 📝 EXEMPLO 5: Usar Stored Procedure

```java
// ============================================
// DEMONSTRAÇÃO 5: Chamar Stored Procedure
// ============================================

// Chamar stored procedure para pesquisar clientes
List<Object[]> resultados = clienteRepository.pesquisarClientesPorNomeExato("João Silva");

// SQL executado:
// Hibernate: CALL pesquisar_clientes_por_nome_exato(?)

// Processar resultados
for (Object[] row : resultados) {
    Integer id = (Integer) row[0];
    String nome = (String) row[1];
    String cpf = (String) row[2];
    
    System.out.println("ID: " + id + ", Nome: " + nome + ", CPF: " + cpf);
}
// Output:
// ID: 1, Nome: João Silva, CPF: 123.456.789-00
```

**Ponto para enfatizar:**
- ✅ Chamamos stored procedures do banco via Java
- ✅ `EntityManager` executa SQL nativo
- ✅ Resultados voltam como `Object[]` (ou podemos criar DTOs)

---

## 📝 EXEMPLO 6: Ver Trigger em Ação

```java
// ============================================
// DEMONSTRAÇÃO 6: Trigger Automático
// ============================================

// 1. Criar agendamento
AgendamentoServico agendamento = new AgendamentoServico();
agendamento.setCliente(clienteRepository.findById(1).get());
agendamento.setServico(servicoRepository.findById(1).get());
agendamento.setData(LocalDate.now());
agendamento.setStatus("Pendente");
agendamento.setValor(100.0f);

// 2. Salvar agendamento
AgendamentoServico agendamentoSalvo = agendamentoRepository.save(agendamento);
// SQL: INSERT INTO agendamento_servico (...)

// 3. O TRIGGER executa automaticamente!
// O Listener @PostPersist cria um registro no histórico

// 4. Verificar histórico criado automaticamente
List<HistoricoStatusAgendamento> historicos = 
    historicoRepository.findByAgendamentoId(agendamentoSalvo.getId());

System.out.println("Históricos criados automaticamente: " + historicos.size());
// Output: Históricos criados automaticamente: 1

HistoricoStatusAgendamento historico = historicos.get(0);
System.out.println("Status anterior: " + historico.getStatusAnterior());
System.out.println("Status novo: " + historico.getStatusNovo());
// Output:
// Status anterior: Criado
// Status novo: Pendente
```

**Ponto para enfatizar:**
- ✅ Trigger executado automaticamente
- ✅ Não precisamos criar histórico manualmente
- ✅ Evento `@PostPersist` captura a inserção

---

## 📝 EXEMPLO 7: Atualizar e Verificar Mudança de Status

```java
// ============================================
// DEMONSTRAÇÃO 7: Atualizar Status (Trigger)
// ============================================

// 1. Buscar agendamento
AgendamentoServico agendamento = agendamentoRepository.findById(1).get();

// 2. Atualizar status
agendamento.setStatus("Confirmado");
agendamentoRepository.save(agendamento);
// SQL: UPDATE agendamento_servico SET age_status = ? WHERE age_id = ?

// 3. O TRIGGER @PostUpdate executa automaticamente!
// Se o status mudou, cria novo registro no histórico

// 4. Verificar histórico
List<HistoricoStatusAgendamento> historicos = 
    historicoRepository.findByAgendamentoId(1);

System.out.println("Total de mudanças no histórico: " + historicos.size());
// Output: Total de mudanças no histórico: 2

// Primeira mudança (ao criar)
System.out.println("1ª mudança: " + historicos.get(0).getStatusAnterior() + 
                  " → " + historicos.get(0).getStatusNovo());
// Output: 1ª mudança: Criado → Pendente

// Segunda mudança (ao atualizar)
System.out.println("2ª mudança: " + historicos.get(1).getStatusAnterior() + 
                  " → " + historicos.get(1).getStatusNovo());
// Output: 2ª mudança: Pendente → Confirmado
```

---

## 📝 EXEMPLO 8: Buscar com Filtros Múltiplos

```java
// ============================================
// DEMONSTRAÇÃO 8: Filtros Complexos
// ============================================

// Buscar agendamentos de um cliente com status específico
List<AgendamentoServico> agendamentos = 
    agendamentoRepository.findByClienteIdAndStatus(1, "Pendente");

// SQL gerado automaticamente:
// SELECT * FROM agendamento_servico 
// WHERE age_cli_id = ? AND age_status = ?

for (AgendamentoServico a : agendamentos) {
    System.out.println("Agendamento ID: " + a.getId() + 
                      ", Status: " + a.getStatus() + 
                      ", Data: " + a.getData());
}

// Buscar agendamentos de um prestador em uma data específica
List<AgendamentoServico> agendamentosPrestador = 
    agendamentoRepository.findByPrestadorIdAndData(1, LocalDate.now());

// SQL gerado:
// SELECT * FROM agendamento_servico 
// WHERE age_pre_id = ? AND age_data = ? AND age_status != 'Cancelado'
```

---

## 📝 EXEMPLO 9: Cascade - Deletar com Relacionamentos

```java
// ============================================
// DEMONSTRAÇÃO 9: Cascade Delete
// ============================================

// Se deletarmos um cliente, os emails são deletados automaticamente
// (porque temos cascade = CascadeType.ALL)

Cliente cliente = clienteRepository.findById(1).get();

// Deletar cliente
clienteRepository.delete(cliente);
// SQL executado:
// 1. DELETE FROM email WHERE ema_cli_id = 1  (cascade)
// 2. DELETE FROM cliente WHERE cli_id = 1

// Verificar se emails foram deletados
boolean emailsExistem = emailRepository.findByClienteId(1).isEmpty();
System.out.println("Emails foram deletados automaticamente? " + emailsExistem);
// Output: Emails foram deletados automaticamente? true
```

**Ponto para enfatizar:**
- ✅ Cascade = Deletar o pai deleta os filhos automaticamente
- ✅ Evita dados órfãos no banco
- ✅ Mantém integridade referencial

---

## 📝 EXEMPLO 10: Usar Query Customizada

```java
// ============================================
// DEMONSTRAÇÃO 10: Query JPQL Customizada
// ============================================

// Buscar agendamentos com query customizada
// (já definida no repository)
List<AgendamentoServico> agendamentosCliente = 
    agendamentoRepository.findByClienteIdAndStatus(1, "Confirmado");

// SQL gerado (JPQL):
// SELECT a FROM AgendamentoServico a 
// WHERE a.cliente.id = ? AND a.status = ?

// Equivale a:
// SELECT * FROM agendamento_servico 
// WHERE age_cli_id = ? AND age_status = ?

for (AgendamentoServico a : agendamentosCliente) {
    System.out.println("Agendamento: " + a.getId() + 
                      " - Serviço: " + a.getServico().getNome());
}
```

---

## 📝 EXEMPLO 11: Demonstrar Lazy Loading

```java
// ============================================
// DEMONSTRAÇÃO 11: Lazy Loading
// ============================================

// 1. Buscar cliente (sem carregar emails)
Cliente cliente = clienteRepository.findById(1).get();
// SQL: SELECT * FROM cliente WHERE cli_id = ?

System.out.println("Cliente carregado: " + cliente.getNomeCompleto());

// 2. Acessar emails (agora carrega do banco)
List<Email> emails = cliente.getEmails();
// SQL: SELECT * FROM email WHERE ema_cli_id = ?

System.out.println("Emails carregados: " + emails.size());

// Ponto importante:
// Se você nunca acessar cliente.getEmails(),
// o banco nunca é consultado! (economiza memória e performance)
```

---

## 📝 EXEMPLO 12: Criar Prestador com Certificações

```java
// ============================================
// DEMONSTRAÇÃO 12: Relacionamentos Complexos
// ============================================

// 1. Criar prestador
Prestador prestador = new Prestador();
prestador.setNomeCompleto("Carlos Oliveira");
prestador.setCpf("111.222.333-44");
prestador.setAreasAtuacao("Elétrica, Hidráulica");
prestadorRepository.save(prestador);

// 2. Criar certificações
CertificacaoPrestador cert1 = new CertificacaoPrestador();
cert1.setPrestador(prestador);
cert1.setTitulo("Curso de Eletricista Residencial");
cert1.setInstituicao("SENAI");
cert1.setDataConclusao(LocalDate.of(2020, 3, 15));
cert1.setUrl("https://cloudinary.com/certificado1.pdf");
certificacaoRepository.save(cert1);

CertificacaoPrestador cert2 = new CertificacaoPrestador();
cert2.setPrestador(prestador);
cert2.setTitulo("Curso de Hidráulica");
cert2.setInstituicao("SENAC");
cert2.setDataConclusao(LocalDate.of(2021, 6, 20));
cert2.setUrl("https://cloudinary.com/certificado2.pdf");
certificacaoRepository.save(cert2);

// 3. Buscar prestador com certificações
Prestador prestadorCompleto = prestadorRepository.findById(prestador.getId()).get();
List<CertificacaoPrestador> certificacoes = prestadorCompleto.getCertificacoes();

System.out.println("Prestador: " + prestadorCompleto.getNomeCompleto());
System.out.println("Certificações: " + certificacoes.size());
for (CertificacaoPrestador c : certificacoes) {
    System.out.println("  - " + c.getTitulo() + " (" + c.getInstituicao() + ")");
}
```

---

## 📝 EXEMPLO 13: Criar Agendamento Completo

```java
// ============================================
// DEMONSTRAÇÃO 13: Agendamento Completo
// ============================================

// 1. Buscar cliente, prestador, serviço e endereço
Cliente cliente = clienteRepository.findById(1).get();
Prestador prestador = prestadorRepository.findById(1).get();
Servico servico = servicoRepository.findById(1).get();
Endereco endereco = enderecoRepository.findById(1).get();

// 2. Criar agendamento
AgendamentoServico agendamento = new AgendamentoServico();
agendamento.setCliente(cliente);
agendamento.setPrestador(prestador);
agendamento.setServico(servico);
agendamento.setEndereco(endereco);
agendamento.setData(LocalDate.of(2024, 12, 25));
agendamento.setJanela("Manhã");
agendamento.setStatus("Pendente");
agendamento.setValor(150.0f);
agendamento.setPago(false);

// 3. Salvar (trigger cria histórico automaticamente)
AgendamentoServico agendamentoSalvo = agendamentoRepository.save(agendamento);

// SQL executado:
// INSERT INTO agendamento_servico 
// (age_cli_id, age_pre_id, age_ser_id, age_end_id, age_data, ...)
// VALUES (1, 1, 1, 1, '2024-12-25', ...)

// 4. Verificar histórico criado pelo trigger
List<HistoricoStatusAgendamento> historicos = 
    agendamentoSalvo.getHistoricosStatus();

System.out.println("Agendamento criado com ID: " + agendamentoSalvo.getId());
System.out.println("Histórico criado automaticamente: " + historicos.size());
// Output:
// Agendamento criado com ID: 1
// Histórico criado automaticamente: 1
```

---

## 📝 EXEMPLO 14: Usar Procedure de Inserção

```java
// ============================================
// DEMONSTRAÇÃO 14: Procedure de Inserção
// ============================================

// Chamar stored procedure para inserir agendamento
Integer novoAgendamentoId = agendamentoRepository.inserirAgendamentoSimples(
    1,  // clienteId
    1,  // servicoId
    1,  // prestadorId
    null,  // empresaId (opcional)
    LocalDate.of(2024, 12, 26),  // data
    "Tarde",  // janela
    1,  // enderecoId
    "Pendente",  // status
    200.0f  // valor
);

// SQL executado:
// CALL inserir_agendamento_de_servico_simples(?, ?, ?, ?, ?, ?, ?, ?, ?)

System.out.println("Novo agendamento criado via procedure com ID: " + novoAgendamentoId);
// Output: Novo agendamento criado via procedure com ID: 2
```

---

## 📝 EXEMPLO 15: Cancelar Agendamento via Procedure

```java
// ============================================
// DEMONSTRAÇÃO 15: Procedure de Cancelamento
// ============================================

// Cancelar agendamento usando stored procedure
agendamentoRepository.cancelarAgendamento(1, "Cliente solicitou cancelamento");

// SQL executado:
// CALL cancelar_agendamento_de_servico(?, ?)

// Verificar se foi cancelado
AgendamentoServico agendamento = agendamentoRepository.findById(1).get();
System.out.println("Status do agendamento: " + agendamento.getStatus());
System.out.println("Data cancelamento: " + agendamento.getDataCancelamento());
System.out.println("Motivo: " + agendamento.getMotivoCancelamento());
// Output:
// Status do agendamento: Cancelado
// Data cancelamento: 2024-12-20
// Motivo: Cliente solicitou cancelamento
```

---

## 📝 EXEMPLO 16: Registrar Avaliação (com Trigger)

```java
// ============================================
// DEMONSTRAÇÃO 16: Trigger de Avaliação
// ============================================

// 1. Buscar agendamento concluído
AgendamentoServico agendamento = agendamentoRepository.findById(1).get();
Cliente cliente = agendamento.getCliente();
Prestador prestador = agendamento.getPrestador();

// 2. Criar avaliação
Avaliacao avaliacao = new Avaliacao();
avaliacao.setAgendamento(agendamento);
avaliacao.setCliente(cliente);
avaliacao.setPrestador(prestador);
avaliacao.setNota(5);
avaliacao.setComentario("Excelente atendimento! Muito profissional e pontual.");
avaliacao.setData(LocalDate.now());

// 3. Salvar avaliação (trigger cria notificação automaticamente)
Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);

// SQL executado:
// INSERT INTO avaliacao (...)

// 4. O TRIGGER @PostPersist executa automaticamente!
// Cria uma notificação para o prestador

// 5. Verificar notificação criada automaticamente
List<Notificacao> notificacoes = prestador.getNotificacoes();

System.out.println("Avaliação registrada com ID: " + avaliacaoSalva.getId());
System.out.println("Notificações criadas automaticamente: " + notificacoes.size());

Notificacao notificacao = notificacoes.get(notificacoes.size() - 1);
System.out.println("Tipo: " + notificacao.getTipo());
System.out.println("Mensagem: " + notificacao.getMensagem());
// Output:
// Avaliação registrada com ID: 1
// Notificações criadas automaticamente: 1
// Tipo: Avaliacao
// Mensagem: Nova avaliação registrada.
```

---

## 📝 DICAS PARA DEMONSTRAÇÃO AO VIVO

### ✅ Configurações Importantes

**1. Ativar show-sql no application.properties:**
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**2. Ter MySQL rodando:**
```bash
mysql -u root -p
USE HomeHero;
```

**3. Ter dados de teste no banco:**
```sql
-- Inserir dados de exemplo para demonstração
INSERT INTO endereco (end_logradouro, end_numero, end_bairro, end_cidade, end_uf)
VALUES ('Rua Teste', '123', 'Centro', 'São Paulo', 'SP');

INSERT INTO categoria_servico (cat_nome, cat_descricao)
VALUES ('Limpeza', 'Serviços de limpeza residencial');

INSERT INTO servico (ser_nome, ser_descricao, ser_preco_base, ser_cat_id)
VALUES ('Limpeza Completa', 'Limpeza geral da casa', 150.0, 1);
```

### 🎤 Script de Apresentação

**1. Mostrar a estrutura:**
- Abrir o projeto no IDE
- Mostrar as pastas model/, repository/, listener/

**2. Mostrar uma entidade simples:**
- Abrir `Endereco.java`
- Explicar cada anotação

**3. Mostrar uma entidade com relacionamentos:**
- Abrir `Cliente.java`
- Mostrar `@OneToMany` e `@ManyToOne`

**4. Mostrar application.properties:**
- Explicar configuração do banco
- Mostrar URL, usuário, senha

**5. Demonstrar ao vivo:**
- Executar `HomeheroApplication`
- Ver console com SQL gerado
- Criar um cliente no código
- Mostrar SQL gerado automaticamente

**6. Mostrar repository:**
- Abrir `ClienteRepository.java`
- Mostrar métodos customizados
- Explicar como Spring cria SQL

**7. Demonstrar buscar dados:**
- Executar `findByCpf()`
- Mostrar SQL gerado
- Mostrar resultado

---

## 📝 CHECKLIST PARA APRESENTAÇÃO

### ✅ Antes de Começar

- [ ] MySQL rodando e acessível
- [ ] Banco `HomeHero` criado
- [ ] Dados de teste inseridos
- [ ] `application.properties` configurado corretamente
- [ ] `show-sql=true` ativado
- [ ] Projeto compila sem erros
- [ ] Código de exemplo preparado
- [ ] IDE aberto com projeto

### ✅ Durante a Apresentação

- [ ] Mostrar estrutura do projeto
- [ ] Explicar `application.properties`
- [ ] Mostrar uma entidade simples (`Endereco.java`)
- [ ] Mostrar uma entidade com relacionamentos (`Cliente.java`)
- [ ] Explicar anotações principais
- [ ] Mostrar repository e métodos
- [ ] Executar exemplo ao vivo
- [ ] Mostrar SQL gerado no console
- [ ] Explicar procedures
- [ ] Explicar triggers
- [ ] Responder perguntas

---

**Boa apresentação! 🎓**

