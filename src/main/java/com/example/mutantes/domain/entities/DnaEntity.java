package com.example.mutantes.domain.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class DnaEntity extends Base {
    private String dna;
    private boolean isMutant;
}
