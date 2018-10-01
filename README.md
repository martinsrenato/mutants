# mutants
Magneto's mutant detector (MeLi test)

## Description
Magneto wants to recruit new mutants to fight against the X-Men. For this, he asked for a tool that can look at a human 
DNA and identify if that person is a mutant.<br/>
This web application detects mutant DNA sequences on a table of size NxN. Each table contains letters [A, T, C, G] that 
represent each nitrogenous base on a particular DNA.<br/>
Mutant sequences have more than one sequence with at least four equal letters, on a vertical, horizontal or diagonal
axis.

## How to Run
#### Execute tests:
 `mvn clean install`

##### Tests coverage:
![Tests Coverage](coverage.png?raw=true "Coverage")
The `domain` package contains auto-generated jOOQ resources, not all used by the application code.


#### Starting application:
 `mvn spring-boot:run -Dspring.profiles.active=local`
 
 The project runs locally using a H2 In-Memory Database. If no profile is used, the project will connect to a
 cloud database on AWS RDS.
 
## Using the API

#### Analyze DNA

After starting the project, call the API to analyze a DNA, sending a POST request to `localhost:8080/mutant` with a 
JSON containing the DNA sequence in the request body. 

Example:<br/>
```curl -X POST http://localhost:8080/mutant -H 'Content-Type: application/json' -d '{"sequences":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}'```
 
 The API will return a Http Status `200 - OK` response if the DNA is from a mutant, or `403 - Forbidden` if the DNA is 
 human.
 
#### Stats
 
To check how many mutants were recruited (and how many humans were tested), on can call the `/stats` API to get check 
the stats from every unique DNA already tested.

Example:<br/>
```curl -X GET http://localhost:8080/stats```

This will return a JSON with the statistics of all the analysis already made.
```
{
    "ratio":0.5,
    "count_mutant_dna":2,
    "count_human_dna":4
}
```

### Test in the cloud

The API is also hosted in the cloud, and can be requested using the following URIs:

Analyze DNA <br/>
`http://ec2-54-207-8-209.sa-east-1.compute.amazonaws.com:8080/mutant`

Stats <br/>
`http://ec2-54-207-8-209.sa-east-1.compute.amazonaws.com:8080/stats`



## Stack
 * Java
 * SpringBoot
 * jOOQ
 * H2
 * PostgresSQL
 * Lombok
 * JUnit
 * Mockito
 * AWS (EC2, RDS)