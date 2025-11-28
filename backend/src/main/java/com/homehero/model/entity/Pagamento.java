package com.homehero.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidade que representa a tabela pagamento.
 * Pagamentos realizados para agendamentos de serviços.
 */
@Entity
@Table(name = "pagamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pag_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_id", nullable = false)
    private AgendamentoServico agendamento;

    @Column(name = "pag_forma", length = 20)
    private String forma; // Cartão, PIX, Boleto, etc.

    @Column(name = "pag_valor")
    private Float valor;

    @Column(name = "pag_status", length = 20)
    private String status; // Pendente, Confirmado, Cancelado

    @Column(name = "pag_ref", length = 60)
    private String referencia;

    @Column(name = "pag_data")
    private LocalDate data;
}
