package com.meli.mutants.service.impl;

import com.meli.mutants.exception.StatsException;
import com.meli.mutants.model.Stats;
import com.meli.mutants.repository.MutantRepository;
import com.meli.mutants.repository.mapper.StatsResultToStatsConverter;
import com.meli.mutants.service.StatsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class StatsServiceImpl implements StatsService {

    private MutantRepository mutantRepository;
    private StatsResultToStatsConverter converter;

    @Autowired
    public StatsServiceImpl(MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
        this.converter = new StatsResultToStatsConverter();
    }


    @Override
    public Stats getStats() {
        try {
            return converter.apply(mutantRepository.getStats());
        } catch (Exception e) {
            throw new StatsException("Error getting mutant stats.", e);
        }
    }
}
