package com.meli.mutants.repository;

import com.meli.mutants.model.AnalyzedDna;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.meli.mutants.domain.tables.Dna.DNA;

@Repository
public class MutantRepositoryImpl implements MutantRepository {

    private DSLContext dslContext;

    public MutantRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Boolean saveAnalyzedDna(AnalyzedDna dna) {
        return dslContext.insertInto(DNA)
                .set(DNA.DNA_SEQUENCE, dna.getDnaSequence())
                .set(DNA.IS_MUTANT, dna.getIsMutant())
                .set(DNA.MUTANT_SEQUENCES, dna.getMutantSequences())
                .execute() > 0;
    }
}
