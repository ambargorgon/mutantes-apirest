package com.example.mutantes.domain.dtos;

import com.example.mutantes.validator.ValidDna;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DnaRequest {
    @ValidDna
    private String[] dna;
}
