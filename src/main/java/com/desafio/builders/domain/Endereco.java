package com.desafio.builders.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Endereco {

    private String logradouro;
    private String numeroEndereco;
    private String complemento;
    private String bairro;
    private String cidade;

    @Enumerated(EnumType.STRING)
    private Uf uf;
    private String cep;

}
