package com.meli.mutants.utils;


import com.meli.mutants.model.Dna;

public class DnaMockUtils {
    public static Dna mockDna(String[] sequences) {
        Dna dna = new Dna();
        dna. setSequences(sequences);
        return dna;
    }
}
