package com.example.mutantes.presentation.controllers;

import com.example.mutantes.domain.dtos.DnaRequest;
import com.example.mutantes.domain.dtos.DnaResponse;
import com.example.mutantes.domain.dtos.StatsResponse;
import com.example.mutantes.services.DnaService;
import com.example.mutantes.services.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // Indica la clase es un controlador REST en Spring Boot que gestiona peticiones HTTP y devolviendo respuestas en formato JSON.
@RequestMapping("/mutant") // Define la ruta base para las solicitudes a este controlador.
@Validated
public class DnaController {
    
    private final DnaService dnaService;
    
    public DnaController(DnaService dnaService) {
        this.dnaService = dnaService;
    }
    
    @PostMapping
    public ResponseEntity<DnaResponse> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = dnaService.analyzeDna(dnaRequest.getDna());
        DnaResponse dnaResponse = new DnaResponse(isMutant);
        if (isMutant) {
            return ResponseEntity.ok(dnaResponse);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dnaResponse);
        }
    }
    
}
