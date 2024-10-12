package com.example.mutantes.repositories;

import com.example.mutantes.domain.entities.DnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DnaRepository extends JpaRepository<DnaEntity, Long> {
    Optional<DnaEntity> findByDna(String dnaSequence);
    
    long countByIsMutant(boolean isMutant);
    
}

