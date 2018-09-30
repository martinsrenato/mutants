package com.meli.mutants.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class AnalyzedDna {
    @NotNull
    private String dnaSequence;
    @NotNull
    private Boolean isMutant;
}
