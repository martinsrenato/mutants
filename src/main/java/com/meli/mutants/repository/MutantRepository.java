package com.meli.mutants.repository;

import com.meli.mutants.model.AnalyzedDna;

public interface MutantRepository {
    Boolean saveAnalyzedDna(AnalyzedDna dna);
}
