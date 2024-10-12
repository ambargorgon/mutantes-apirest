package com.example.mutantes.presentation.controllers;

import com.example.mutantes.domain.dtos.DnaRequest;
import com.example.mutantes.domain.dtos.DnaResponse;
import com.example.mutantes.services.DnaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/mutant")
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
