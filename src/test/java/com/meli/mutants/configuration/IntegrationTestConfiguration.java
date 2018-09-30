package com.meli.mutants.configuration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan(basePackages = {"com.meli.mutants"})
@EnableAutoConfiguration
public class IntegrationTestConfiguration {

}
