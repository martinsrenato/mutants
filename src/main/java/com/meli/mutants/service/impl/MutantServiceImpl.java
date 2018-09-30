package com.meli.mutants.service.impl;

import com.meli.mutants.exception.DnaException;
import com.meli.mutants.model.AnalyzedDna;
import com.meli.mutants.model.Dna;
import com.meli.mutants.repository.MutantRepository;
import com.meli.mutants.repository.mapper.DnaRecordToAnalyzedDnaConverter;
import com.meli.mutants.service.MutantService;
import com.meli.mutants.utils.DnaUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class MutantServiceImpl implements MutantService {

    private MutantRepository mutantRepository;
    private DnaRecordToAnalyzedDnaConverter converter;

    @Autowired
    public MutantServiceImpl(MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
        this.converter = new DnaRecordToAnalyzedDnaConverter();
    }

    @Override
    public AnalyzedDna analyzeDna(Dna dna) {
        DnaUtils.validateDna(dna);

        Optional<AnalyzedDna> dnaAlreadyAnalyzed =
                mutantRepository.getAnalyzedDna(DnaUtils.getDnaAsSingleSequence(dna)).map(converter);
        if (dnaAlreadyAnalyzed.isPresent()) {
            return dnaAlreadyAnalyzed.get();
        }

        log.info("Analyzing new Dna [{}]", dna);
        AnalyzedDna analyzedDna = DnaUtils.analyzeDna(dna);
        saveAnalyzedDna(analyzedDna);

        return analyzedDna;
    }

    private void saveAnalyzedDna(AnalyzedDna analyzedDna) {
        log.info("Saving new AnalyzedDna [{}]", analyzedDna);
        Integer saved = mutantRepository.saveAnalyzedDna(analyzedDna);
        if (saved == 0) {
            log.error("Error persisting analyzedDna [{}]", analyzedDna);
            throw new DnaException("Error analysing Dna, please try again.");
        }
    }
}
