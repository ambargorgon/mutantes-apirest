package com.example.mutantes.services;

import com.example.mutantes.domain.entities.DnaEntity;
import com.example.mutantes.repositories.DnaRepository;
import com.example.mutantes.validator.DnaValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DnaService {
    private final DnaRepository dnaRepository;
    
    @Autowired
    public DnaService(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }
    
    
    public static boolean isMutant(String[] matrix) {
        List<String> matrix2 = List.of(matrix);
        
        
        //Buscar y contar coincidencias horizontales
        int coincidenceCounter = 0;
        coincidenceCounter = coincidenceAnalysis(matrix2, coincidenceCounter);
        
        
        //Buscar y contar coincidencias verticales
        if (coincidenceCounter < 2) {
            coincidenceCounter = getVertical(matrix2, coincidenceCounter);
        }
        
        //Buscar y contar coincidencias diagonales
        if (coincidenceCounter < 2) {
            coincidenceCounter = getDiagonal(matrix2, coincidenceCounter);
        }
        
        //Buscar y contar coincidencias diagonales inversas
        if (coincidenceCounter < 2) {
            coincidenceCounter = getDiagonal(mirrorRows(matrix2), coincidenceCounter);
        }

      return coincidenceCounter >= 2;
    }
    
    
    public static int getVertical(List<String> matrix, int coincidenceCounter) {
        int n = matrix.size();
        List<String> new_vertical_matrix = new ArrayList<>();
        
        //Crear nueva matriz con las columnas dispuestas en filas
        for (int j = 0; j < n; j++) {
            StringBuilder column = new StringBuilder();
            for (int i = 0; i < n; i++) {
                column.append(matrix.get(i).charAt(j));
            }
            new_vertical_matrix.add(column.toString());
        }
        //Enviar al analizador de filas
        return coincidenceAnalysis(new_vertical_matrix, coincidenceCounter);
    }
    
    public static int getDiagonal(List<String> matrix, int coincidenceCounter) {
        int n = matrix.size();
        List<String> newDiagonalMatrix = new ArrayList<>();
        
        //Crear nueva matriz con las diagonales dispuestas en filas
        for (int d = -n + 1; d < n; d++) {
            StringBuilder diagonal = new StringBuilder();
            for (int i = 0; i < n; i++) {
                int j = i - d;
                if (j >= 0 && j < n) {
                    diagonal.append(matrix.get(i).charAt(j));
                }
            }
            if (diagonal.length() >= 4) {
                newDiagonalMatrix.add(diagonal.toString());
            }
        }
        //Enviar al analizador de filas
        return coincidenceAnalysis(newDiagonalMatrix, coincidenceCounter);
    }
    
    public static List<String> mirrorRows(List<String> matrix) {
        List<String> mirror_matrix = new ArrayList<>();
        
        //Espejar diagonales
        for (String row : matrix) {
            mirror_matrix.add(new StringBuilder(row).reverse().toString());
        }
        
        return mirror_matrix;
    }
    
    public static int coincidenceAnalysis(List<String> matrix, int coincidenceCounter) {
        //Buscar coincidencia de 4 letras seguidas
        for (String row : matrix) {
            int range_i = row.length() - 3;
            for (int i = 0; i < range_i; i++) {
                if (row.charAt(i) == row.charAt(i + 1)) { //Comparar 1 y 2
                    if (row.charAt(i) == row.charAt(i + 2)) {//Comparar 1 y 3
                        if (row.charAt(i) == row.charAt(i + 3)) { //Comparar 1 y 4
                            coincidenceCounter++; //Coincidencia encontrada
                            break;
                        }
                    }
                }
            }
        }
        return coincidenceCounter;
    }
    
    public boolean analyzeDna(String[] dna) {
        String dnaSequence = String.join(",", dna);
        
        // Verificamos si el ADN ya existe en la base de datos
        Optional<DnaEntity> existingDna = dnaRepository.findByDna(dnaSequence);
        if (existingDna.isPresent()) {
            // Si el ADN ya fue analizado, retornamos el resultado
            return existingDna.get().isMutant();
        }
        
        // Determinamos si el ADN es mutante y lo guardamos en la base de datos
        boolean isMutant = isMutant(dna);
        DnaEntity dnaEntity = DnaEntity.builder()
                                .dna(dnaSequence)
                                .isMutant(isMutant)
                                .build();
        dnaRepository.save(dnaEntity);
        
        return isMutant;  // Retornamos el valor ya calculado
    }
}