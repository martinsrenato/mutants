CREATE SCHEMA IF NOT EXISTS "mutants";

CREATE TABLE IF NOT EXISTS "mutants"."dna"(
	"dna_sequence" VARCHAR(255) NOT NULL,
	"is_mutant" boolean NOT NULL,
	primary key ("dna_sequence")
);