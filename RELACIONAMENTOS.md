# 📊 Documentação de Relacionamentos - HomeHero

Este documento descreve todos os relacionamentos entre as entidades do sistema HomeHero e explica o motivo de cada relacionamento.

## 📖 Índice

1. [Entidades de Usuário](#1-entidades-de-usuário)
2. [Entidades de Localização](#2-entidades-de-localização)
3. [Entidades de Contato](#3-entidades-de-contato)
4. [Entidades de Serviços](#4-entidades-de-serviços)
5. [Entidades de Agendamento](#5-entidades-de-agendamento)
6. [Entidades de Gestão](#6-entidades-de-gestão)
7. [Entidades de Pagamento e Disputas](#7-entidades-de-pagamento-e-disputas)
8. [Entidades de Comunicação](#8-entidades-de-comunicação)

---

## 1. Entidades de Usuário

### 1.1 Cliente

#### Cliente → Endereco (N:1)
- **Relacionamento**: `@ManyToOne` com `Endereco`
- **Motivo**: Um cliente pode ter apenas um endereço cadastrado, mas o mesmo endereço pode ser compartilhado por múltiplos clientes. Permite que clientes tenham endereços padrão para agendamentos.
- **Coluna**: `cli_endereco_id`

#### Cliente → Email (1:N)
- **Relacionamento**: `@OneToMany` com `Email`
- **Motivo**: Um cliente pode ter múltiplos emails (pessoal, trabalho, etc.). Relacionamento multivalorado permite flexibilidade de contato.
- **Coluna**: `ema_cli_id` (em Email)

#### Cliente → Telefone (1:N)
- **Relacionamento**: `@OneToMany` com `Telefone`
- **Motivo**: Um cliente pode ter múltiplos telefones (celular, residencial, comercial). Relacionamento multivalorado permite diferentes formas de contato.
- **Coluna**: `tel_cli_id` (em Telefone)

#### Cliente → AgendamentoServico (1:N)
- **Relacionamento**: `@OneToMany` com `AgendamentoServico`
- **Motivo**: Um cliente pode fazer múltiplos agendamentos ao longo do tempo. Cada agendamento pertence a um único cliente.
- **Coluna**: `age_cli_id` (em AgendamentoServico)

#### Cliente → RegistroRegiao (1:N)
- **Relacionamento**: `@OneToMany` com `RegistroRegiao`
- **Motivo**: Um cliente pode se registrar em múltiplas regiões de atendimento, permitindo buscar serviços em diferentes áreas.
- **Coluna**: `rre_cli_id` (em RegistroRegiao)

#### Cliente → Avaliacao (1:N)
- **Relacionamento**: `@OneToMany` com `Avaliacao`
- **Motivo**: Um cliente pode fazer múltiplas avaliações de diferentes serviços. Cada avaliação é feita por um cliente específico.
- **Coluna**: `ava_cli_id` (em Avaliacao)

#### Cliente → Notificacao (1:N)
- **Relacionamento**: `@OneToMany` com `Notificacao`
- **Motivo**: Um cliente pode receber múltiplas notificações sobre agendamentos, pagamentos, cancelamentos, etc.
- **Coluna**: `not_cli_id` (em Notificacao)

#### Cliente → DisputaReembolso (1:N)
- **Relacionamento**: `@OneToMany` com `DisputaReembolso`
- **Motivo**: Um cliente pode abrir múltiplas disputas relacionadas a diferentes agendamentos.
- **Coluna**: `dsp_cli_id` (em DisputaReembolso)

---

### 1.2 Prestador

#### Prestador → Endereco (N:1)
- **Relacionamento**: `@ManyToOne` com `Endereco`
- **Motivo**: Um prestador tem um endereço cadastral, mas o mesmo endereço pode ser compartilhado (ex: escritório compartilhado).
- **Coluna**: `pre_endereco_id`

#### Prestador → Email (1:N)
- **Relacionamento**: `@OneToMany` com `Email`
- **Motivo**: Um prestador pode ter múltiplos emails para diferentes finalidades (contato, suporte, comercial).
- **Coluna**: `ema_pre_id` (em Email)

#### Prestador → Telefone (1:N)
- **Relacionamento**: `@OneToMany` com `Telefone`
- **Motivo**: Um prestador pode ter múltiplos telefones (celular pessoal, celular comercial, fixo).
- **Coluna**: `tel_pre_id` (em Telefone)

#### Prestador → AgendamentoServico (1:N)
- **Relacionamento**: `@OneToMany` com `AgendamentoServico`
- **Motivo**: Um prestador pode ter múltiplos agendamentos ao longo do tempo. Cada agendamento pode ser com prestador OU empresa (mutuamente exclusivo).
- **Coluna**: `age_pre_id` (em AgendamentoServico)

#### Prestador → RegistroRegiao (1:N)
- **Relacionamento**: `@OneToMany` com `RegistroRegiao`
- **Motivo**: Um prestador pode atender em múltiplas regiões, permitindo ampliar sua área de atuação.
- **Coluna**: `rre_pre_id` (em RegistroRegiao)

#### Prestador → PrestadorServico (1:N)
- **Relacionamento**: `@OneToMany` com `PrestadorServico`
- **Motivo**: Um prestador pode oferecer múltiplos serviços, cada um com seu próprio preço e disponibilidade.
- **Coluna**: `prs_pre_id` (em PrestadorServico)

#### Prestador → CertificacaoPrestador (1:N)
- **Relacionamento**: `@OneToMany` com `CertificacaoPrestador`
- **Motivo**: Um prestador pode ter múltiplas certificações que comprovam suas qualificações.
- **Coluna**: `cer_pre_id` (em CertificacaoPrestador)

#### Prestador → DisponibilidadePrestador (1:N)
- **Relacionamento**: `@OneToMany` com `DisponibilidadePrestador`
- **Motivo**: Um prestador pode definir diferentes horários de disponibilidade (por dia da semana, janelas de horário).
- **Coluna**: `dis_pre_id` (em DisponibilidadePrestador)

#### Prestador → Avaliacao (1:N)
- **Relacionamento**: `@OneToMany` com `Avaliacao`
- **Motivo**: Um prestador pode receber múltiplas avaliações de diferentes clientes ao longo do tempo.
- **Coluna**: `ava_pre_id` (em Avaliacao)

#### Prestador → Notificacao (1:N)
- **Relacionamento**: `@OneToMany` com `Notificacao`
- **Motivo**: Um prestador pode receber notificações sobre novos agendamentos, cancelamentos, avaliações, etc.
- **Coluna**: `not_pre_id` (em Notificacao)

#### Prestador → DisputaReembolso (1:N)
- **Relacionamento**: `@OneToMany` com `DisputaReembolso`
- **Motivo**: Um prestador pode estar envolvido em múltiplas disputas relacionadas a agendamentos.
- **Coluna**: `dsp_pre_id` (em DisputaReembolso)

---

### 1.3 Empresa

#### Empresa → Endereco (N:1)
- **Relacionamento**: `@ManyToOne` com `Endereco`
- **Motivo**: Uma empresa tem um endereço sede, mas o mesmo endereço pode ser compartilhado por múltiplas empresas.
- **Coluna**: `emp_endereco_id`

#### Empresa → Email (1:N)
- **Relacionamento**: `@OneToMany` com `Email`
- **Motivo**: Uma empresa pode ter múltiplos emails (comercial, suporte, financeiro, etc.).
- **Coluna**: `ema_emp_id` (em Email)

#### Empresa → Telefone (1:N)
- **Relacionamento**: `@OneToMany` com `Telefone`
- **Motivo**: Uma empresa pode ter múltiplos telefones (central, departamentos, suporte).
- **Coluna**: `tel_emp_id` (em Telefone)

#### Empresa → AgendamentoServico (1:N)
- **Relacionamento**: `@OneToMany` com `AgendamentoServico`
- **Motivo**: Uma empresa pode ter múltiplos agendamentos. Cada agendamento é com prestador OU empresa (mutuamente exclusivo).
- **Coluna**: `age_emp_id` (em AgendamentoServico)

#### Empresa → FuncionarioEmpresa (1:N)
- **Relacionamento**: `@OneToMany` com `FuncionarioEmpresa`
- **Motivo**: Uma empresa tem múltiplos funcionários que podem ser atribuídos a agendamentos.
- **Coluna**: `fun_emp_id` (em FuncionarioEmpresa)

#### Empresa → RegistroRegiao (1:N)
- **Relacionamento**: `@OneToMany` com `RegistroRegiao`
- **Motivo**: Uma empresa pode atender em múltiplas regiões, expandindo sua área de cobertura.
- **Coluna**: `rre_emp_id` (em RegistroRegiao)

#### Empresa → EmpresaServico (1:N)
- **Relacionamento**: `@OneToMany` com `EmpresaServico`
- **Motivo**: Uma empresa pode oferecer múltiplos serviços, cada um com seu próprio preço e disponibilidade.
- **Coluna**: `ems_emp_id` (em EmpresaServico)

#### Empresa → Avaliacao (1:N)
- **Relacionamento**: `@OneToMany` com `Avaliacao`
- **Motivo**: Uma empresa pode receber múltiplas avaliações de diferentes clientes. Permite avaliar empresas além de prestadores individuais.
- **Coluna**: `ava_emp_id` (em Avaliacao)

#### Empresa → Notificacao (1:N)
- **Relacionamento**: `@OneToMany` com `Notificacao`
- **Motivo**: Uma empresa pode receber notificações sobre agendamentos, atribuições de funcionários, avaliações, etc.
- **Coluna**: `not_emp_id` (em Notificacao)

---

## 2. Entidades de Localização

### 2.1 Endereco

#### Endereco → Cliente (1:N)
- **Relacionamento**: `@OneToMany` (inverso) - Não mapeado diretamente
- **Motivo**: Um endereço pode ser usado por múltiplos clientes (ex: endereço corporativo). O relacionamento é gerenciado pela entidade Cliente.

#### Endereco → Prestador (1:N)
- **Relacionamento**: `@OneToMany` (inverso) - Não mapeado diretamente
- **Motivo**: Um endereço pode ser usado por múltiplos prestadores (escritório compartilhado). O relacionamento é gerenciado pela entidade Prestador.

#### Endereco → Empresa (1:N)
- **Relacionamento**: `@OneToMany` (inverso) - Não mapeado diretamente
- **Motivo**: Um endereço pode ser usado por múltiplas empresas (centro comercial). O relacionamento é gerenciado pela entidade Empresa.

#### Endereco → AgendamentoServico (1:N)
- **Relacionamento**: `@OneToMany` (inverso) - Não mapeado diretamente
- **Motivo**: Um agendamento pode ter um endereço específico diferente do endereço cadastral do cliente (ex: prestação de serviço em outro local).
- **Coluna**: `age_end_id` (em AgendamentoServico)

---

### 2.2 Regiao

#### Regiao → RegistroRegiao (1:N)
- **Relacionamento**: `@OneToMany` (inverso) - Não mapeado diretamente
- **Motivo**: Uma região pode ter múltiplos registros de clientes, prestadores e empresas. Permite gerenciar a cobertura de atendimento.
- **Coluna**: `rre_reg_id` (em RegistroRegiao)

---

### 2.3 RegistroRegiao

#### RegistroRegiao → Regiao (N:1)
- **Relacionamento**: `@ManyToOne` com `Regiao`
- **Motivo**: Um registro sempre pertence a uma região específica. Múltiplos registros podem referenciar a mesma região.
- **Coluna**: `rre_reg_id` (obrigatório)

#### RegistroRegiao → Cliente (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Cliente`
- **Motivo**: Um registro pode ser de um cliente, prestador OU empresa (mutuamente exclusivo). Permite que clientes se cadastrem em regiões para receber ofertas.
- **Coluna**: `rre_cli_id` (opcional)

#### RegistroRegiao → Prestador (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Prestador`
- **Motivo**: Permite que prestadores se registrem em regiões onde desejam atender.
- **Coluna**: `rre_pre_id` (opcional)

#### RegistroRegiao → Empresa (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Empresa`
- **Motivo**: Permite que empresas se registrem em regiões onde desejam atender.
- **Coluna**: `rre_emp_id` (opcional)

---

## 3. Entidades de Contato

### 3.1 Email

#### Email → Cliente (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Cliente`
- **Motivo**: Email multivalorado permite que clientes tenham múltiplos emails (pessoal, trabalho). O email pode pertencer a cliente, prestador OU empresa.
- **Coluna**: `ema_cli_id` (opcional)

#### Email → Prestador (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Prestador`
- **Motivo**: Prestadores podem ter múltiplos emails para diferentes finalidades.
- **Coluna**: `ema_pre_id` (opcional)

#### Email → Empresa (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Empresa`
- **Motivo**: Empresas podem ter múltiplos emails (comercial, suporte, financeiro).
- **Coluna**: `ema_emp_id` (opcional)

**Nota**: Apenas um dos três relacionamentos (Cliente, Prestador, Empresa) deve estar preenchido por email.

---

### 3.2 Telefone

#### Telefone → Cliente (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Cliente`
- **Motivo**: Telefone multivalorado permite que clientes tenham múltiplos telefones (celular, residencial, comercial). O telefone pode pertencer a cliente, prestador OU empresa.
- **Coluna**: `tel_cli_id` (opcional)

#### Telefone → Prestador (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Prestador`
- **Motivo**: Prestadores podem ter múltiplos telefones para diferentes finalidades.
- **Coluna**: `tel_pre_id` (opcional)

#### Telefone → Empresa (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Empresa`
- **Motivo**: Empresas podem ter múltiplos telefones (central, departamentos).
- **Coluna**: `tel_emp_id` (opcional)

**Nota**: Apenas um dos três relacionamentos (Cliente, Prestador, Empresa) deve estar preenchido por telefone.

---

## 4. Entidades de Serviços

### 4.1 CategoriaServico

#### CategoriaServico → Servico (1:N)
- **Relacionamento**: `@OneToMany` (inverso) - Não mapeado diretamente
- **Motivo**: Uma categoria pode conter múltiplos serviços. Organiza os serviços em grupos (ex: "Limpeza", "Elétrica", "Encanamento").
- **Coluna**: `ser_cat_id` (em Servico) - Opcional

---

### 4.2 Servico

#### Servico → CategoriaServico (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `CategoriaServico`
- **Motivo**: Um serviço pode pertencer a uma categoria para facilitar organização e busca. É opcional para permitir serviços sem categoria definida.
- **Coluna**: `ser_cat_id` (opcional)

#### Servico → PrestadorServico (1:N)
- **Relacionamento**: `@OneToMany` com `PrestadorServico`
- **Motivo**: Um serviço pode ser oferecido por múltiplos prestadores, cada um com seu próprio preço e disponibilidade.
- **Coluna**: `prs_ser_id` (em PrestadorServico)

#### Servico → EmpresaServico (1:N)
- **Relacionamento**: `@OneToMany` com `EmpresaServico`
- **Motivo**: Um serviço pode ser oferecido por múltiplas empresas, cada uma com seu próprio preço e disponibilidade.
- **Coluna**: `ems_ser_id` (em EmpresaServico)

#### Servico → AgendamentoServico (1:N)
- **Relacionamento**: `@OneToMany` com `AgendamentoServico`
- **Motivo**: Um serviço pode ter múltiplos agendamentos ao longo do tempo.
- **Coluna**: `age_ser_id` (em AgendamentoServico)

---

### 4.3 PrestadorServico

#### PrestadorServico → Prestador (N:1)
- **Relacionamento**: `@ManyToOne` com `Prestador`
- **Motivo**: Cada oferta de serviço pertence a um prestador específico. Permite que prestadores definam preços personalizados por serviço.
- **Coluna**: `prs_pre_id` (obrigatório)

#### PrestadorServico → Servico (N:1)
- **Relacionamento**: `@ManyToOne` com `Servico`
- **Motivo**: Cada oferta referencia um serviço específico. Permite que múltiplos prestadores ofereçam o mesmo serviço.
- **Coluna**: `prs_ser_id` (obrigatório)

**Motivo da Tabela**: Esta tabela intermediária permite que prestadores ofereçam serviços com preços próprios (`prs_preco_oferta`) e controle de disponibilidade (`prs_ativo`), diferente do preço base do serviço.

---

### 4.4 EmpresaServico

#### EmpresaServico → Empresa (N:1)
- **Relacionamento**: `@ManyToOne` com `Empresa`
- **Motivo**: Cada oferta de serviço pertence a uma empresa específica. Permite que empresas definam preços personalizados por serviço.
- **Coluna**: `ems_emp_id` (obrigatório)

#### EmpresaServico → Servico (N:1)
- **Relacionamento**: `@ManyToOne` com `Servico`
- **Motivo**: Cada oferta referencia um serviço específico. Permite que múltiplas empresas ofereçam o mesmo serviço.
- **Coluna**: `ems_ser_id` (obrigatório)

**Motivo da Tabela**: Similar ao PrestadorServico, permite que empresas ofereçam serviços com preços próprios (`ems_preco_oferta`) e controle de disponibilidade (`ems_ativo`).

---

## 5. Entidades de Agendamento

### 5.1 AgendamentoServico

#### AgendamentoServico → Cliente (N:1)
- **Relacionamento**: `@ManyToOne` com `Cliente`
- **Motivo**: Todo agendamento pertence a um cliente específico que contrata o serviço.
- **Coluna**: `age_cli_id` (obrigatório)

#### AgendamentoServico → Servico (N:1)
- **Relacionamento**: `@ManyToOne` com `Servico`
- **Motivo**: Todo agendamento referencia um serviço específico que será prestado.
- **Coluna**: `age_ser_id` (obrigatório)

#### AgendamentoServico → Prestador (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Prestador`
- **Motivo**: Um agendamento pode ser com um prestador individual OU com uma empresa (mutuamente exclusivo).
- **Coluna**: `age_pre_id` (opcional)

#### AgendamentoServico → Empresa (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Empresa`
- **Motivo**: Um agendamento pode ser com uma empresa OU com um prestador individual (mutuamente exclusivo).
- **Coluna**: `age_emp_id` (opcional)

#### AgendamentoServico → Endereco (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Endereco`
- **Motivo**: Um agendamento pode ter um endereço específico diferente do endereço cadastral do cliente (ex: prestação em outro local).
- **Coluna**: `age_end_id` (opcional)

#### AgendamentoServico → HistoricoStatusAgendamento (1:N)
- **Relacionamento**: `@OneToMany` com `HistoricoStatusAgendamento`
- **Motivo**: Um agendamento pode ter múltiplas mudanças de status ao longo do tempo (Pendente → Confirmado → Em Andamento → Concluído → Cancelado).
- **Coluna**: `his_age_id` (em HistoricoStatusAgendamento)

#### AgendamentoServico → AtribuicaoServico (1:N)
- **Relacionamento**: `@OneToMany` com `AtribuicaoServico`
- **Motivo**: Quando um agendamento é com uma empresa, pode haver múltiplas atribuições a diferentes funcionários (caso precise de troca ou múltiplos funcionários).
- **Coluna**: `atr_age_id` (em AtribuicaoServico)

#### AgendamentoServico → Pagamento (1:N)
- **Relacionamento**: `@OneToMany` com `Pagamento`
- **Motivo**: Um agendamento pode ter múltiplos pagamentos (ex: entrada + parcelas, ou múltiplas tentativas de pagamento).
- **Coluna**: `pag_age_id` (em Pagamento)

#### AgendamentoServico → DisputaReembolso (1:N)
- **Relacionamento**: `@OneToMany` com `DisputaReembolso`
- **Motivo**: Um agendamento pode ter múltiplas disputas ao longo do tempo (ex: disputa inicial → recurso → resolução).
- **Coluna**: `dsp_age_id` (em DisputaReembolso)

#### AgendamentoServico → Avaliacao (1:N)
- **Relacionamento**: `@OneToMany` com `Avaliacao`
- **Motivo**: Um agendamento pode ter múltiplas avaliações (ex: avaliação do cliente e resposta do prestador).
- **Coluna**: `ava_age_id` (em Avaliacao)

#### AgendamentoServico → Notificacao (1:N)
- **Relacionamento**: `@OneToMany` (inverso) - Não mapeado diretamente
- **Motivo**: Um agendamento pode gerar múltiplas notificações para diferentes partes envolvidas.
- **Coluna**: `not_age_id` (em Notificacao)

#### AgendamentoServico → ChatMensagem (1:N)
- **Relacionamento**: `@OneToMany` (inverso) - Não mapeado diretamente
- **Motivo**: Um agendamento pode ter múltiplas mensagens de chat entre cliente e prestador/empresa.
- **Coluna**: `cha_age_id` (em ChatMensagem)

#### AgendamentoServico → ChatbotLog (1:N)
- **Relacionamento**: `@OneToMany` (inverso) - Não mapeado diretamente
- **Motivo**: Um agendamento pode ter múltiplas interações com chatbot relacionadas.
- **Coluna**: `cbt_age_id` (em ChatbotLog)

---

### 5.2 AtribuicaoServico

#### AtribuicaoServico → AgendamentoServico (N:1)
- **Relacionamento**: `@ManyToOne` com `AgendamentoServico`
- **Motivo**: Uma atribuição sempre pertence a um agendamento específico. Permite rastrear qual funcionário foi designado para cada agendamento de empresa.
- **Coluna**: `atr_age_id` (obrigatório)

#### AtribuicaoServico → FuncionarioEmpresa (N:1)
- **Relacionamento**: `@ManyToOne` com `FuncionarioEmpresa`
- **Motivo**: Uma atribuição sempre designa um funcionário específico da empresa para realizar o serviço.
- **Coluna**: `atr_fun_id` (obrigatório)

**Motivo da Tabela**: Quando um agendamento é feito com uma empresa, a empresa precisa atribuir um funcionário específico. Esta tabela permite rastrear essas atribuições e até mesmo realizar trocas de funcionário se necessário.

---

### 5.3 HistoricoStatusAgendamento

#### HistoricoStatusAgendamento → AgendamentoServico (N:1)
- **Relacionamento**: `@ManyToOne` com `AgendamentoServico`
- **Motivo**: Cada histórico de mudança de status pertence a um agendamento específico. Permite auditoria e rastreamento de todas as mudanças de status.
- **Coluna**: `his_age_id` (obrigatório)

**Motivo da Tabela**: Esta tabela permite manter um histórico completo de todas as mudanças de status de um agendamento, facilitando auditoria, suporte e análise de padrões.

---

## 6. Entidades de Gestão

### 6.1 FuncionarioEmpresa

#### FuncionarioEmpresa → Empresa (N:1)
- **Relacionamento**: `@ManyToOne` com `Empresa`
- **Motivo**: Todo funcionário pertence a uma empresa específica. Permite que empresas gerenciem seu quadro de funcionários.
- **Coluna**: `fun_emp_id` (obrigatório)

#### FuncionarioEmpresa → AtribuicaoServico (1:N)
- **Relacionamento**: `@OneToMany` com `AtribuicaoServico`
- **Motivo**: Um funcionário pode ser atribuído a múltiplos agendamentos ao longo do tempo. Permite rastrear a carga de trabalho de cada funcionário.
- **Coluna**: `atr_fun_id` (em AtribuicaoServico)

**Motivo da Entidade**: Empresas precisam gerenciar funcionários que serão atribuídos aos agendamentos. Isso permite controle de disponibilidade, especialidades e histórico de trabalho.

---

### 6.2 DisponibilidadePrestador

#### DisponibilidadePrestador → Prestador (N:1)
- **Relacionamento**: `@ManyToOne` com `Prestador`
- **Motivo**: Um prestador pode definir múltiplos horários de disponibilidade (por dia da semana, janelas de horário). Permite que clientes vejam quando o prestador está disponível.
- **Coluna**: `dis_pre_id` (obrigatório)

**Motivo da Entidade**: Prestadores individuais precisam definir quando estão disponíveis para trabalhar. Isso permite que o sistema verifique disponibilidade antes de permitir agendamentos.

---

### 6.3 CertificacaoPrestador

#### CertificacaoPrestador → Prestador (N:1)
- **Relacionamento**: `@ManyToOne` com `Prestador`
- **Motivo**: Um prestador pode ter múltiplas certificações que comprovam suas qualificações (cursos, licenças, certificados). Isso aumenta a confiança dos clientes.
- **Coluna**: `cer_pre_id` (obrigatório)

**Motivo da Entidade**: Prestadores podem querer exibir suas qualificações e certificações para destacar sua experiência e competência, aumentando suas chances de serem contratados.

---

## 7. Entidades de Pagamento e Disputas

### 7.1 Pagamento

#### Pagamento → AgendamentoServico (N:1)
- **Relacionamento**: `@ManyToOne` com `AgendamentoServico`
- **Motivo**: Um pagamento sempre está relacionado a um agendamento específico. Um agendamento pode ter múltiplos pagamentos (ex: entrada + parcelas, tentativas de pagamento falhadas).
- **Coluna**: `pag_age_id` (obrigatório)

**Motivo da Entidade**: Permite rastrear todos os pagamentos relacionados a um agendamento, incluindo múltiplas tentativas, parcelas e reembolsos.

---

### 7.2 DisputaReembolso

#### DisputaReembolso → AgendamentoServico (N:1)
- **Relacionamento**: `@ManyToOne` com `AgendamentoServico`
- **Motivo**: Uma disputa sempre está relacionada a um agendamento específico. Permite que clientes ou prestadores abram disputas sobre serviços prestados.
- **Coluna**: `dsp_age_id` (obrigatório)

#### DisputaReembolso → Cliente (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Cliente`
- **Motivo**: Um cliente pode abrir uma disputa sobre um serviço recebido. Facilita identificação de quem abriu a disputa.
- **Coluna**: `dsp_cli_id` (opcional)

#### DisputaReembolso → Prestador (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Prestador`
- **Motivo**: Um prestador pode estar envolvido em uma disputa (seja como parte reclamante ou reclamada).
- **Coluna**: `dsp_pre_id` (opcional)

**Motivo da Entidade**: Permite gerenciar disputas entre clientes e prestadores, incluindo solicitações de reembolso, com rastreamento de status e valores.

---

## 8. Entidades de Comunicação

### 8.1 Avaliacao

#### Avaliacao → AgendamentoServico (N:1)
- **Relacionamento**: `@ManyToOne` com `AgendamentoServico`
- **Motivo**: Uma avaliação sempre está relacionada a um agendamento específico. Permite que clientes avaliem serviços recebidos.
- **Coluna**: `ava_age_id` (obrigatório)

#### Avaliacao → Cliente (N:1)
- **Relacionamento**: `@ManyToOne` com `Cliente`
- **Motivo**: Uma avaliação sempre é feita por um cliente específico. Permite identificar quem avaliou.
- **Coluna**: `ava_cli_id` (obrigatório)

#### Avaliacao → Prestador (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Prestador`
- **Motivo**: Uma avaliação pode ser sobre um prestador individual OU uma empresa (mutuamente exclusivo).
- **Coluna**: `ava_pre_id` (opcional)

#### Avaliacao → Empresa (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Empresa`
- **Motivo**: Uma avaliação pode ser sobre uma empresa OU um prestador individual (mutuamente exclusivo).
- **Coluna**: `ava_emp_id` (opcional)

**Motivo da Entidade**: Permite que clientes avaliem e deixem comentários sobre serviços recebidos, ajudando outros clientes a tomar decisões e incentivando qualidade.

---

### 8.2 Notificacao

#### Notificacao → Cliente (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Cliente`
- **Motivo**: Uma notificação pode ser enviada a um cliente, prestador OU empresa. Permite notificações personalizadas por tipo de usuário.
- **Coluna**: `not_cli_id` (opcional)

#### Notificacao → Prestador (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Prestador`
- **Motivo**: Prestadores recebem notificações sobre novos agendamentos, cancelamentos, avaliações, etc.
- **Coluna**: `not_pre_id` (opcional)

#### Notificacao → Empresa (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Empresa`
- **Motivo**: Empresas recebem notificações sobre agendamentos, atribuições de funcionários, avaliações, etc.
- **Coluna**: `not_emp_id` (opcional)

#### Notificacao → AgendamentoServico (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `AgendamentoServico`
- **Motivo**: Uma notificação pode estar relacionada a um agendamento específico (ex: "Seu agendamento foi confirmado").
- **Coluna**: `not_age_id` (opcional)

**Motivo da Entidade**: Sistema centralizado de notificações para manter todos os usuários informados sobre eventos importantes (agendamentos, pagamentos, avaliações, etc.).

---

### 8.3 ChatMensagem

#### ChatMensagem → AgendamentoServico (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `AgendamentoServico`
- **Motivo**: Mensagens de chat geralmente estão relacionadas a um agendamento específico, permitindo comunicação entre cliente e prestador/empresa sobre o serviço.
- **Coluna**: `cha_age_id` (opcional)

**Motivo da Entidade**: Permite comunicação direta entre clientes e prestadores/empresas, facilitando esclarecimentos, ajustes de horário e resolução de dúvidas.

**Nota**: Os campos `cha_remetente_tipo`, `cha_remetente_id`, `cha_destinatario_tipo`, `cha_destinatario_id` permitem flexibilidade para que qualquer tipo de usuário possa enviar mensagens para qualquer outro tipo.

---

### 8.4 ChatbotLog

#### ChatbotLog → Cliente (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Cliente`
- **Motivo**: Um log de chatbot pode estar relacionado a um cliente específico que interagiu com o chatbot.
- **Coluna**: `cbt_cli_id` (opcional)

#### ChatbotLog → Prestador (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Prestador`
- **Motivo**: Prestadores também podem interagir com o chatbot para obter informações.
- **Coluna**: `cbt_pre_id` (opcional)

#### ChatbotLog → Empresa (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `Empresa`
- **Motivo**: Empresas também podem interagir com o chatbot.
- **Coluna**: `cbt_emp_id` (opcional)

#### ChatbotLog → AgendamentoServico (N:1) - Opcional
- **Relacionamento**: `@ManyToOne` com `AgendamentoServico`
- **Motivo**: Interações com chatbot podem estar relacionadas a um agendamento específico (ex: cliente pergunta sobre status do agendamento).
- **Coluna**: `cbt_age_id` (opcional)

**Motivo da Entidade**: Permite rastrear todas as interações com o chatbot, facilitando análise, melhorias e debugging do sistema de atendimento automatizado.

---

## 🔑 Padrões e Convenções

### Relacionamentos Mutuamente Exclusivos

Alguns relacionamentos são mutuamente exclusivos, ou seja, apenas um deve estar preenchido:

- **AgendamentoServico**: `age_pre_id` OU `age_emp_id` (não ambos)
- **Avaliacao**: `ava_pre_id` OU `ava_emp_id` (não ambos)
- **RegistroRegiao**: `rre_cli_id` OU `rre_pre_id` OU `rre_emp_id` (apenas um)
- **Email**: `ema_cli_id` OU `ema_pre_id` OU `ema_emp_id` (apenas um)
- **Telefone**: `tel_cli_id` OU `tel_pre_id` OU `tel_emp_id` (apenas um)

### FetchType.LAZY

Todos os relacionamentos `@OneToMany` utilizam `FetchType.LAZY` para melhor performance, carregando os dados relacionados apenas quando necessário.

### CascadeType.ALL

A maioria dos relacionamentos utiliza `CascadeType.ALL`, permitindo que operações (salvar, atualizar, deletar) sejam propagadas automaticamente para entidades relacionadas.

---

## 📝 Resumo de Relacionamentos por Cardinalidade

| Cardinalidade | Exemplos |
|---------------|----------|
| **1:N** (Um para Muitos) | Cliente → Agendamentos, Servico → Agendamentos |
| **N:1** (Muitos para Um) | Agendamento → Cliente, Email → Cliente |
| **N:N** (Muitos para Muitos via intermediária) | Prestador ↔ Servico (via PrestadorServico), Empresa ↔ Servico (via EmpresaServico) |

---

Este documento fornece uma visão completa de todos os relacionamentos do sistema HomeHero e seus propósitos. Para mais detalhes sobre a implementação, consulte o código-fonte das entidades.

