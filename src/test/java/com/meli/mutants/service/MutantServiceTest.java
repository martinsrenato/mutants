package com.meli.mutants.service;

import com.meli.mutants.domain.tables.records.DnaRecord;
import com.meli.mutants.exception.DnaException;
import com.meli.mutants.model.AnalyzedDna;
import com.meli.mutants.repository.MutantRepository;
import com.meli.mutants.service.impl.MutantServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

@RunWith(MockitoJUnitRunner.class)
public class MutantServiceTest {

    @Mock
    MutantRepository mutantRepository;

    @InjectMocks
    MutantServiceImpl mutantService;

    @Test(expected = DnaException.class)
    public void invalidDnaNull() {
        mutantService.analyzeDna(null);
    }

    @Test(expected = DnaException.class)
    public void invalidDnaNullSequences() {
        mutantService.analyzeDna(mockDna(null));
    }

    @Test(expected = DnaException.class)
    public void invalidDnaOneNullSequence() {
        String[] sequences = array(null,"CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");
        mutantService.analyzeDna(mockDna(sequences));
    }

    @Test(expected = DnaException.class)
    public void invalidDnaSize() {
        String[] sequences = array("A","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");
        mutantService.analyzeDna(mockDna(sequences));
    }

    @Test(expected = DnaException.class)
    public void invalidDnaCharacter() {
        String[] sequences = array("XXXXXX","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");
        mutantService.analyzeDna(mockDna(sequences));
    }

    @Test
    public void analyzeMutantDna() {
        String[] sequences = array("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");

        when(mutantRepository.getAnalyzedDna(any())).thenReturn(empty());
        when(mutantRepository.saveAnalyzedDna(any())).thenReturn(1);

        AnalyzedDna analyzedDna = mutantService.analyzeDna(mockDna(sequences));
        assertNotNull(analyzedDna);
        assertTrue(analyzedDna.getIsMutant());

        verify(mutantRepository, times(1)).getAnalyzedDna(any());
        verify(mutantRepository, times(1)).saveAnalyzedDna(any());
    }

    @Test
    public void analyzeHumanDna() {
        String[] sequences = array("ACTCAA","CAGTTC","CGATGT","AGCTGG","CTGCTA","TCACTG");

        when(mutantRepository.getAnalyzedDna(any())).thenReturn(empty());
        when(mutantRepository.saveAnalyzedDna(any())).thenReturn(1);

        AnalyzedDna analyzedDna = mutantService.analyzeDna(mockDna(sequences));
        assertNotNull(analyzedDna);
        assertFalse(analyzedDna.getIsMutant());

        verify(mutantRepository, times(1)).getAnalyzedDna(any());
        verify(mutantRepository, times(1)).saveAnalyzedDna(any());
    }

    @Test
    public void analyzeExistingMutantDna() {
        DnaRecord dnaRecord = new DnaRecord();
        dnaRecord.setDnaSequence("ATGCGACAGTGCTTATGTAGAAGGCCCCTATCACTG");
        dnaRecord.setIsMutant(true);

        String[] sequences = array("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");

        when(mutantRepository.getAnalyzedDna(any())).thenReturn(Optional.of(dnaRecord));

        AnalyzedDna analyzedDna = mutantService.analyzeDna(mockDna(sequences));
        assertNotNull(analyzedDna);
        assertEquals(String.join("", sequences), analyzedDna.getDnaSequence());
        assertTrue(analyzedDna.getIsMutant());

        verify(mutantRepository, times(1)).getAnalyzedDna(any());
        verify(mutantRepository, times(0)).saveAnalyzedDna(any());
    }

    @Test(expected = DnaException.class)
    public void errorSavingDna() {
        String[] sequences = array("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");

        when(mutantRepository.getAnalyzedDna(any())).thenReturn(empty());
        when(mutantRepository.saveAnalyzedDna(any())).thenReturn(0);

        mutantService.analyzeDna(mockDna(sequences));
    }

    @Test
    public void analyzeSmallDna() {
        String[] sequences = array("ACT","CAG","CGA");

        when(mutantRepository.getAnalyzedDna(any())).thenReturn(empty());
        when(mutantRepository.saveAnalyzedDna(any())).thenReturn(1);

        AnalyzedDna analyzedDna = mutantService.analyzeDna(mockDna(sequences));
        assertNotNull(analyzedDna);
        assertFalse(analyzedDna.getIsMutant());

        verify(mutantRepository, times(1)).getAnalyzedDna(any());
        verify(mutantRepository, times(1)).saveAnalyzedDna(any());
    }
}


