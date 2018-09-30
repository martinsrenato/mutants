package com.meli.mutants.utils;

import com.meli.mutants.exception.DnaException;
import com.meli.mutants.model.AnalyzedDna;
import com.meli.mutants.model.Dna;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Log4j2
public class DnaUtils {

    private static final String INVALID_CHARS_REGEX = ".*[^ATCG]+.*";

    private static final String MUTANT_SEQUENCE_REGEX = ".*(AAAA|CCCC|TTTT|GGGG)+.*";
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
                    .dnaSequence(getDnaAsSingleSequence(dna))
                    .isMutant(false)
                    .build();
        }

        Boolean isMutant = isMutant(dna.getSequences());

        return AnalyzedDna.builder()
                .dnaSequence(getDnaAsSingleSequence(dna))
                .isMutant(isMutant)
                .build();
    }

    private static boolean isMutant(String[] dna) {
        //horizontal
        long matchedSequences = Stream.of(dna).filter(s -> s.matches(MUTANT_SEQUENCE_REGEX)).count();
        if (matchedSequences > 1) {
            return true;
        }

        //vertical
        matchedSequences += getVerticalSequences(dna).stream().filter(s -> s.matches(MUTANT_SEQUENCE_REGEX)).count();
        if (matchedSequences > 1) {
            return true;
        }

        //diagonal
        matchedSequences += getDiagonalSequences(dna).stream().filter(s -> s.matches(MUTANT_SEQUENCE_REGEX)).count();
        if (matchedSequences > 1) {
            return true;
        }

        //antidiagonal
        matchedSequences += getAntiDiagonalSequences(dna).stream().filter(s -> s.matches(MUTANT_SEQUENCE_REGEX)).count();

        return matchedSequences > 1;
    }

    private static List<String> getVerticalSequences(String[] dna) {
        List<String> sequences = new ArrayList<>();
        for (int i = 0; i < dna.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(dna[0].charAt(i));

            for (int j = 1; j < dna.length; j++) {
                sb.append(dna[j].charAt(i));
            }

            sequences.add(sb.toString());
        }

        return sequences;
    }

    private static List<String> getDiagonalSequences(String[] dna) {
        List<String> sequences = new ArrayList<>();
        for (int i = 0; i <= dna.length - MINIMUM_MUTANT_SEQUENCE_SIZE; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(dna[0].charAt(i));

            for (int j = 1; j+i < dna.length; j++) {
                sb.append(dna[j].charAt(j+i));
            }

            sequences.add(sb.toString());
        }

        return sequences;
    }

    private static List<String> getAntiDiagonalSequences(String[] dna) {
        List<String> sequences = new ArrayList<>();
        for (int i = dna.length-1; i >= MINIMUM_MUTANT_SEQUENCE_SIZE - 1; i--) {
            StringBuilder sb = new StringBuilder();
            sb.append(dna[0].charAt(i));

            for (int j = 1; i-j >= 0; j++) {
                sb.append(dna[j].charAt(i-j));
            }

            sequences.add(sb.toString());
        }

        return sequences;
    }

    public static String getDnaAsSingleSequence(Dna dna) {
        return String.join("", dna.getSequences());
    }
}
