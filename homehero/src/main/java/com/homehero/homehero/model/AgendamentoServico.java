package com.homehero.homehero.model;

import com.homehero.homehero.listener.AgendamentoServicoListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "agendamento_servico")
@EntityListeners(AgendamentoServicoListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoServico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "age_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "age_cli_id", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "age_ser_id", nullable = false)
    private Servico servico;
    
    @ManyToOne
    @JoinColumn(name = "age_pre_id")
    private Prestador prestador;
    
    @ManyToOne
    @JoinColumn(name = "age_emp_id")
    private Empresa empresa;
    
    @Column(name = "age_data")
    private LocalDate data;
    
    @Column(name = "age_janela", length = 20)
    private String janela;
    
    @ManyToOne
    @JoinColumn(name = "age_end_id")
    private Endereco endereco;
    
    @Column(name = "age_status", length = 20)
    private String status;
    
    @Column(name = "age_valor")
    private Float valor;
    
    @Column(name = "age_pago")
    private Boolean pago = false;
    
    @Column(name = "age_data_cancelamento")
    private LocalDate dataCancelamento;
    
    @Column(name = "age_motivo_cancelamento", length = 120)
    private String motivoCancelamento;
    
    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoricoStatusAgendamento> historicosStatus;
    
    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AtribuicaoServico> atribuicoes;
    
    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pagamento> pagamentos;
    
    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DisputaReembolso> disputas;
    
    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Avaliacao> avaliacoes;
}

