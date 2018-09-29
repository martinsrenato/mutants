SET SCHEMA 'mutants';

CREATE TABLE dna(
	dna_sequence text PRIMARY KEY NOT NULL,
	is_mutant boolean NOT NULL,
	mutant_sequences text[]
);