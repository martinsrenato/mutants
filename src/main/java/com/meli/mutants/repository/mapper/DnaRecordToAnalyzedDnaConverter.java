package com.meli.mutants.repository.mapper;

import com.meli.mutants.domain.tables.records.DnaRecord;
import com.meli.mutants.model.AnalyzedDna;
import org.jooq.Record;
import org.jooq.RecordMapper;

import java.util.function.Function;

public class DnaRecordToAnalyzedDnaConverter implements Function<DnaRecord, AnalyzedDna>, RecordMapper<Record, AnalyzedDna> {

    @Override
    public AnalyzedDna apply(DnaRecord dnaRecord) {
        return AnalyzedDna.builder()
                .dnaSequence(dnaRecord.getDnaSequence())
                .isMutant(dnaRecord.getIsMutant())
                .build();
    }

    @Override
    public AnalyzedDna map(Record record) {
        return apply((DnaRecord) record);
    }
}
