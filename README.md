# goeuro-test

## Overview

This application is a command line utility intended to test the GoEuro Location JSON API.  The application runs on Java 8 and produces a CSV file using the data returned from the API.

## Configuring and Running the Project

- The GoEuro base REST URL is defined in the `src/main/resources/app.properties` file; this value will work by default, however, can be changed to use test values
- The project can be built by executing the command `./gradlew clean build`
- Once built, the JAR file can be found in the build/libs folder

## Running Tests

- Tests can be run You can run tests using gradle by executing `./gradlew cleanTest test`
- To generate a code coverage report, run `./gradlew jacocoTestReport`

