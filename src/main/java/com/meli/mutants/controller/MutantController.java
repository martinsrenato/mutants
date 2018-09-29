package com.meli.mutants.controller;

import com.meli.mutants.model.AnalyzedDna;
import com.meli.mutants.model.Dna;
import com.meli.mutants.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
public class MutantController {

    private final MutantService mutantService;

    @Autowired
    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @PostMapping("/mutant")
    public ResponseEntity<String> analyzeDna(@RequestBody Dna dna) {
        AnalyzedDna analyzedDna = mutantService.analizeDna(dna);

        return analyzedDna.getIsMutant() ?
                ResponseEntity.ok("Dna is mutant!") : ResponseEntity.status(FORBIDDEN).build();
    }
}
