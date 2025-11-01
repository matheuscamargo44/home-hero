package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    
    @ManyToOne
    @JoinColumn(name = "pag_age_id", nullable = false)
    private AgendamentoServico agendamento;
    
    @Column(name = "pag_forma", length = 20)
    private String forma; // Ex: Cartão Crédito, Cartão Débito, PIX, Boleto
    
    @Column(name = "pag_valor_total")
    private Float valorTotal;
    
    @Column(name = "pag_status", length = 20)
    private String status; // Ex: Pendente, Pago, Recusado, Reembolsado
    
    @Column(name = "pag_referencia_gateway", length = 60)
    private String referenciaGateway; // ID da transação no gateway externo
    
    @Column(name = "pag_data")
    private LocalDate data;
}

