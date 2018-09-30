package com.meli.mutants.repository.mapper;

import com.meli.mutants.model.Stats;
import org.jooq.Record2;
import org.jooq.Result;

import java.util.function.Function;

public class StatsResultToStatsConverter implements Function<Result<Record2<Integer, Boolean>>, Stats> {

    @Override
    public Stats apply(Result<Record2<Integer, Boolean>> result) {
        Integer countMutantDna = 0;
        Integer countHumanDna = 0;

        for(Record2<Integer, Boolean> record : result) {
            if(record.value2()) {
                countMutantDna = record.value1();
            } else {
                countHumanDna = record.value1();
            }
        }

        return Stats.builder()
                .countMutantDna(countMutantDna)
                .countHumanDna(countHumanDna)
                .ratio(getRatio(countMutantDna, countHumanDna))
                .build();
    }

    private Double getRatio(Integer countMutantDna, Integer countHumanDna) {
        if(countHumanDna == 0) return 1d;

        return (double)countMutantDna/(double)countHumanDna;
    }
}
