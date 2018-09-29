package com.meli.mutants.service.impl;

import com.meli.mutants.model.AnalyzedDna;
import com.meli.mutants.model.Dna;
import com.meli.mutants.repository.MutantRepository;
import com.meli.mutants.service.MutantService;
import com.meli.mutants.utils.DnaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MutantServiceImpl implements MutantService {

    @Autowired
    MutantRepository mutantRepository;

    @Override
    public AnalyzedDna analizeDna(Dna dna) {
        DnaUtils.validateDna(dna);

        AnalyzedDna analyzedDna = DnaUtils.analyzeDna(dna);

        //mutantRepository.saveAnalyzedDna(analyzedDna);

        return analyzedDna;
    }
}
