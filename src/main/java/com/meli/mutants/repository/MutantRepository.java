package com.meli.mutants.repository;

import com.meli.mutants.domain.tables.records.DnaRecord;
import com.meli.mutants.model.AnalyzedDna;
import org.jooq.Record2;
import org.jooq.Result;

import java.util.Optional;

public interface MutantRepository {
    Optional<DnaRecord> getAnalyzedDna(String dnaAsSingleSequence);
    Integer saveAnalyzedDna(AnalyzedDna dna);
    Result<Record2<Integer, Boolean>> getStats();
}
