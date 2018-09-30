package com.meli.mutants.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE)
public class Stats {
    @NotNull
    @JsonProperty(value = "count_mutant_dna")
    private Integer countMutantDna;
    @NotNull
    @JsonProperty(value = "count_human_dna")
    private Integer countHumanDna;
    @NotNull
    private Double ratio;
}
