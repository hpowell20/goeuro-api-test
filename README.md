# goeuro-test

## Purpose



## What You Need

To get started, you'll need 3 things:

1. The Java JDK (development kit) version 1.8.
2. The text editor or IDE of your choice.

## Configuring and Running the Project

- The GoEuro base REST URL is defined in the `src/main/resources/app.properties` file; this value will work by default, however, can be changed to use test values
- The project can be built by executing the command `./gradlew clean build`
- Once built, the JAR file can be found in the build/libs folder

## Running Tests

Tests can be run You can run tests using gradle by executing `./gradlew cleanTest test`. To generate a code coverage report, run `./gradlew jacocoTestReport`.

