# Shelter Testing Practice

## What this project is

A pre-built Java project for practising unit testing with JUnit 5 and AssertJ.

The source code is already implemented. Your job is to **write the tests**.

## How to start

1. Open this folder in IntelliJ IDEA as a Maven project.
2. Wait for dependencies to download.
3. Navigate to `src/test/java` — you will find test class skeletons with TODO comments.
4. Write the tests described in each file.
5. Run tests with the green arrow or `mvn test`.

## Project structure

```
src/main/java/lv/bootcamp/shelter/
├── model/      → Animal, AnimalType (shared domain classes)
├── task1/      → AgeCalculator (pure logic)
├── task23/      → AnimalValidator (parameterized + exceptions)
├── task4/      → AnimalSorter (collection sorting)
├── task5/      → AnimalCsvParser (file I/O and nested tests)
├── task6/      → IntakeService, AnimalRepository (mocking a dependency)
└── stretch/    → AnimalReportWriter (stretch goal)

src/main/resources/
└── intake.csv  → Sample data for CSV parsing tasks

src/test/java/lv/bootcamp/shelter/
├── task1/      → AgeCalculatorTest
├── task23/      → AnimalValidatorTest
├── task4/      → AnimalSorterTest
├── task5/      → AnimalCsvParserTest
├── task6/      → IntakeServiceTest (mocking with Mockito)
└── stretch/    → AnimalReportWriterTest (stretch)
```

## Tasks overview

| # | Package   | Focus | Testing concepts |
|---|-----------|-------|-----------------|
| 1 | `task1`   | Pure logic | `assertEquals`, AAA pattern, naming | [x] Done |
| 2 | `task23`  | Multiple rules | `@ParameterizedTest`, `@CsvSource` | [x] Done |
| 3 | `task23`  | Error cases | `assertThrows`, exception messages | [x] Done |
| 4 | `task4`   | Collection order | AssertJ list assertions | [x] Done |
| 5 | `task5`   | CSV scenarios | `@Nested` classes, `@DisplayName` | [x] Done |
| 6 | `stretch` | File output formatting | AssertJ string/list assertions | [x] Done |
| 7 | `task6`   | Mocking a dependency | `@Mock`, `@InjectMocks`, `when/thenReturn`, `verify` | [x] Done |
| 8 | All       | Coverage | IntelliJ coverage runner | [x] Done (coverage-notes.txt`) |

`task5` introduces Mockito basics: mock a repository dependency so you can test the `IntakeService` in isolation. JPA and controller tests come later in M14.

## Task 8: Coverage check

After completing tasks 1–7:
1. Right-click the `test` folder → **Run All Tests with Coverage** [x]
2. Review which lines are green (covered) vs red (not covered) [x]
3. Identify at least two untested branches [x]
4. Decide: would tests for those branches catch real bugs? [x]

## Dependencies

- Java 21
- JUnit Jupiter 5.11
- AssertJ 3.26
- Mockito 5.14 (used in `task5` for mocking a dependency)
