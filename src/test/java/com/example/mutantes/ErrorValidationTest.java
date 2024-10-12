package com.example.mutantes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ErrorValidationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void contextLoads() {}
    
    // Test A: Recibir un array vacío
    @Test
    public void testEmptyArray() throws Exception {
        mockMvc.perform(post("/api/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"dna\": [] }"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dna").value("El array de ADN no puede estar vacío"));
    }
    
    // Test B: Recibir un array NxM en vez de NxN
    @Test
    public void testArrayNxM() throws Exception {
        mockMvc.perform(post("/api/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"dna\": [\"ATCG\", \"TAGC\", \"CTGA\"] }"))  // NxM, no cuadrado
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dna").value("El array de ADN debe ser NxN"));
    }
    
    // Test C: Recibir un array de números
    @Test
    public void testArrayOfNumbers() throws Exception {
        mockMvc.perform(post("/api/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"dna\": [\"1234\", \"5678\", \"9101\", \"1121\"] }"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dna").value("El ADN solo puede contener los caracteres A, T, C, G"));
    }
    
    // Test D: Recibir null
    @Test
    public void testNullArray() throws Exception {
        mockMvc.perform(post("/api/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"dna\": null }"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dna").value("El array de ADN no puede ser null"));
    }
    
    // Test E: Recibir un array de NxN de nulls
    @Test
    public void testArrayOfNulls() throws Exception {
        mockMvc.perform(post("/api/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"dna\": [null, null, null, null] }"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dna").value("Cada cadena de ADN debe ser no nula y contener los caracteres A, T, C, G"));
    }
    
    // Test F: Recibir un array de NxN con letras distintas a las propuestas
    @Test
    public void testArrayWithInvalidLetters() throws Exception {
        mockMvc.perform(post("/api/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"dna\": [\"BHCG\", \"TAGC\", \"CTGA\", \"BHTC\"] }"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dna").value("El ADN solo puede contener los caracteres A, T, C, G"));
    }
}
