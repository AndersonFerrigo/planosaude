package com.healthcare.beneficiary.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "beneficiario")
public class Beneficiario {

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private int id;
    private String nome;
    private String telefone;
    private String dataNascimento;
    private String dataInclusao;
    private String dataAtualizacao;

    @OneToMany(mappedBy = "beneficiario", cascade = CascadeType.REMOVE)
    private List<Documento> documentos;
}
