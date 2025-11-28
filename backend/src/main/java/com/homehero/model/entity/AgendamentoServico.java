package com.homehero.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Entidade que representa a tabela agendamento_servico.
 * Agendamentos de serviços entre clientes e prestadores.
 */
@Entity
@Table(name = "agendamento_servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "age_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cli_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ser_id", nullable = false)
    private Servico servico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_id")
    private Prestador prestador;

    @Column(name = "age_data")
    private LocalDate data;

    @Column(name = "age_janela", length = 20)
    private String janela; // Manhã, Tarde, Noite

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_id")
    private Endereco endereco;

    @Column(name = "age_status", length = 20)
    private String status; // Pendente, Confirmado, Cancelado, Concluído

    @Column(name = "age_valor")
    private Float valor;

    @Column(name = "age_pago")
    private Boolean pago;

    @Column(name = "age_data_cancel")
    private LocalDate dataCancelamento;

    @Column(name = "age_motivo", length = 120)
    private String motivo;

    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos;

    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL)
    private List<DisputaReembolso> disputas;

    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes;

    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL)
    private List<HistoricoStatusAgendamento> historicoStatus;
}
