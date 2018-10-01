package com.meli.mutants.service;

import com.meli.mutants.domain.tables.records.DnaRecord;
import com.meli.mutants.exception.DnaException;
import com.meli.mutants.model.AnalyzedDna;
import com.meli.mutants.model.Stats;
import com.meli.mutants.repository.MutantRepository;
import com.meli.mutants.service.impl.MutantServiceImpl;
import com.meli.mutants.service.impl.StatsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.meli.mutants.utils.DnaMockUtils.mockDna;
import static java.util.Optional.empty;
import static org.assertj.core.util.Arrays.array;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("it")
public class MutantServiceIT {

    @Autowired
    MutantServiceImpl mutantService;

    @Autowired
    StatsServiceImpl statsService;

    @Test
    public void analyzeMutantDna() {
        String[] sequences = array("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");

        AnalyzedDna analyzedDna = mutantService.analyzeDna(mockDna(sequences));
        assertNotNull(analyzedDna);
        assertTrue(analyzedDna.getIsMutant());
    }

    @Test
    public void analyzeHumanDna() {
        String[] sequences = array("ACTCAA","CAGTTC","CGATGT","AGCTGG","CTGCTA","TCACTG");

        AnalyzedDna analyzedDna = mutantService.analyzeDna(mockDna(sequences));
        assertNotNull(analyzedDna);
        assertFalse(analyzedDna.getIsMutant());
    }

    @Test
    public void analyzeExistingMutantDna() {
        analyzeMutantDna();

        Stats previousStats = statsService.getStats();
        assertNotNull(previousStats);
        assertNotNull(previousStats.getCountMutantDna());

        Integer previousMutantCount = previousStats.getCountMutantDna();

        String[] sequences = array("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");

        AnalyzedDna analyzedDna = mutantService.analyzeDna(mockDna(sequences));
        assertNotNull(analyzedDna);
        assertTrue(analyzedDna.getIsMutant());

        Stats stats = statsService.getStats();
        assertNotNull(stats);
        assertEquals(previousMutantCount, stats.getCountMutantDna());
    }
}


