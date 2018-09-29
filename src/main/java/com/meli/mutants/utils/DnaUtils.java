package com.meli.mutants.utils;

import com.meli.mutants.exception.DnaException;
import com.meli.mutants.model.AnalyzedDna;
import com.meli.mutants.model.Dna;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class DnaUtils {

    private static final String INVALID_CHARS_REGEX = ".*[^ATCG]+.*";

    public static final String MUTANT_SEQUENCE_REGEX = ".*(AAAA|CCCC|TTTT|GGGG)+.*";
    private static final int MINIMUM_MUTANT_SEQUENCE_SIZE = 4;

    public static void validateDna(Dna dna) {
        if(dna == null) {
            throw new DnaException("Dna must not be null");
        }
        if(dna.getSequences() == null) {
            throw new DnaException("Dna has no sequences");
        }
        if(Stream.of(dna.getSequences()).anyMatch(Objects::isNull)) {
            throw new DnaException("Dna has an empty sequence");
        }
        if(Stream.of(dna.getSequences()).anyMatch(s -> s.length() != dna.getSequences().length)) {
            throw new DnaException("Dna table must have a NxN size");
        }
        if(Stream.of(dna.getSequences()).anyMatch(s -> s.matches(INVALID_CHARS_REGEX))) {
            throw new DnaException("Dna has invalid character not in [A T C G]");
        }
    }

    public static AnalyzedDna analyzeDna(Dna dna) {
        if(dna.getSequences().length < MINIMUM_MUTANT_SEQUENCE_SIZE) {
            log.debug("Dna cannot have mutant sequence due to it's size [{}]", dna);
            return AnalyzedDna.builder()
                    .dnaSequence(getDnaAsSingleSequence(dna.getSequences()))
                    .isMutant(false)
                    .build();
        }

        List<String> sequences = DnaUtils.getAllDnaSequencesToAnalyze(dna.getSequences());

        Boolean isMutant = sequences.stream()
                .filter(s -> s.matches(MUTANT_SEQUENCE_REGEX))
                .collect(Collectors.toList()).size() > 1;

        return AnalyzedDna.builder()
                .dnaSequence(getDnaAsSingleSequence(dna.getSequences()))
                .isMutant(isMutant)
                .build();
    }

    private static List<String> getAllDnaSequencesToAnalyze(String[] sequences) {
        //horizontal
        List<String> analyzeSequences = new ArrayList<>(Arrays.asList(sequences));

        //vertical
        for (int i = 0; i < sequences.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(sequences[0].charAt(i));

            for (int j = 1; j < sequences.length; j++) {
                sb.append(sequences[j].charAt(i));
            }

            analyzeSequences.add(sb.toString());
        }

        //diagonal
        for (int i = 0; i <= sequences.length - MINIMUM_MUTANT_SEQUENCE_SIZE; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(sequences[0].charAt(i));

            for (int j = 1; j+i < sequences.length; j++) {
                sb.append(sequences[j].charAt(j+i));
            }

            analyzeSequences.add(sb.toString());
        }

        //antidiagonal
        for (int i = sequences.length-1; i >= MINIMUM_MUTANT_SEQUENCE_SIZE - 1; i--) {
            StringBuilder sb = new StringBuilder();
            sb.append(sequences[0].charAt(i));

            for (int j = 1; i-j >= 0; j++) {
                sb.append(sequences[j].charAt(i-j));
            }

            analyzeSequences.add(sb.toString());
        }

        return analyzeSequences;
    }

    private static String getDnaAsSingleSequence(String[] dna) {
        return String.join("", dna);
    }
}
