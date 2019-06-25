# opto
[![Build Status](https://travis-ci.org/llicursi/opto.svg?branch=master)](https://travis-ci.org/llicursi/opto)  
Opto, decisions made easy - A java/angular voting system

## Initialize Angular 7
Before starting the execution, make sure all angular dependencies were downloaded
```
cd src/main/frontend/angular
npm install -g npm@latest
npm build
``` 

## Setup database
Create a new schema on MySQL called `opto` and **flyway** will populate it with 
the initial database structure on first execution. 

Initial data can be found on:
```
src/test/resources/db/migrations/
```

## Build
At the root folder of this project, execute : 
```
./mvnw clean package -P web
``` 
It's important to run with `-P web` profile so it can embed the **angular** build   
A `war` file will be generated on *"/target"*
 
## Development execution 
Development execution can be achieved with Angular 7 embedded or on a distinct service

### Embedded
```
./mvnw spring-boot:run -DskipTests -P web
```

### Distinct server
1. Start java backend
```
./mvnw spring-boot:run 
```
2. Start Angular frontend
```
cd src/main/frontend/angular/
ng serve -c local
```

## Libraries

 - Lombok
 - Spring (web, mvc, data, security)
 - Flyway
 - Hibernate
 - Angular 7
 - Angular Material
 - Moment.js
 