package com.meli.mutants.service;

import com.meli.mutants.model.AnalyzedDna;
import com.meli.mutants.model.Dna;

public interface MutantService {
    AnalyzedDna analyzeDna(Dna dna);
}
