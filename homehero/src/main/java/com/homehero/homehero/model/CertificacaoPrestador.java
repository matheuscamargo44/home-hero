package com.homehero.homehero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "certificacao_prestador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificacaoPrestador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cer_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "cer_pre_id", nullable = false)
    private Prestador prestador;
    
    @Column(name = "cer_titulo", length = 120)
    private String titulo;
    
    @Column(name = "cer_instituicao", length = 120)
    private String instituicao;
    
    @Column(name = "cer_data_conclusao")
    private LocalDate dataConclusao;
    
    @Column(name = "cer_url", length = 255)
    private String url; // URL do arquivo no Cloudinary ou S3
}

