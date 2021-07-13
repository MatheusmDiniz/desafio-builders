package com.desafio.builders.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuscarCriteria {
    private String key;
    private String operation;
    private String value;
}
