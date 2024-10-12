package com.example.mutantes.services;

import com.example.mutantes.domain.dtos.StatsResponse;
import com.example.mutantes.repositories.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    public final DnaRepository dnaRepository;
    
    @Autowired
    public StatsService(DnaRepository dnaRepository){
        this.dnaRepository = dnaRepository;
    }
    
    public StatsResponse getStats(){
        //Contador de mutantes/humanos
        long countMutantDna = dnaRepository.countByIsMutant(true);
        long countHumanDna = dnaRepository.countByIsMutant(false);
        
        //Calculo de radio
        double ratio = countHumanDna == 0 ? 0: (double) countMutantDna / countHumanDna;
        return new StatsResponse(countMutantDna, countHumanDna, ratio);
}
}
