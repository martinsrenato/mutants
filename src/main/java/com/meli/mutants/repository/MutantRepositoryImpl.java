package com.meli.mutants.repository;

import com.meli.mutants.domain.tables.records.DnaRecord;
import com.meli.mutants.model.AnalyzedDna;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.meli.mutants.domain.tables.Dna.DNA;
import static org.jooq.impl.DSL.count;

@Repository
public class MutantRepositoryImpl implements MutantRepository {

    private DSLContext dslContext;

    public MutantRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Optional<DnaRecord> getAnalyzedDna(String dnaSequence) {
        return dslContext.selectFrom(DNA)
                .where(DNA.DNA_SEQUENCE.eq(dnaSequence))
                .fetchOptional();
    }

    @Override
    public Integer saveAnalyzedDna(AnalyzedDna dna) {
        return dslContext.insertInto(DNA)
                .set(DNA.DNA_SEQUENCE, dna.getDnaSequence())
                .set(DNA.IS_MUTANT, dna.getIsMutant())
                .execute();
    }

    @Override
    public Result<Record2<Integer, Boolean>> getStats() {
        return dslContext.select(count(DNA.DNA_SEQUENCE), DNA.IS_MUTANT)
                .from(DNA)
                .groupBy(DNA.IS_MUTANT)
                .fetch();
    }
}
