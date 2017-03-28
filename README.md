# goeuro-api-test

## Overview

This application is a command line utility used to test the GoEuro Location JSON API.  The application runs on Java 8 and produces a CSV file using the data returned from the API.

## Configuring and Running the Project

- The GoEuro base REST URL is defined in the `src/main/resources/app.properties` file; this value will work by default, however, can be changed to use test values
- The project can be built by executing the command `./gradlew clean build`
- Once built, the application can be run using the command java -jar GoEuroTest.jar "CITY_NAME" (where CITY_NAME is the city used as the input to the API)

NOTE: The generated CSV file will be of the format "CITY_NAME".csv.

## Running Tests

- Tests can be executed by running the command `./gradlew cleanTest test`
- To generate a code coverage report, run `./gradlew jacocoTestReport`
