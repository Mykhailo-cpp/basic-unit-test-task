package lv.bootcamp.shelter.stretch;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * Stretch goal: Testing file output
 *
 * Practice:
 * - Writing to temp files and reading them back
 * - String content assertions
 * - Cleanup with Files.deleteIfExists
 *
 * Instructions:
 * These tests verify that AnimalReportWriter produces correct output.
 * This task is optional — attempt it after completing tasks 1–6.
 */
@DisplayName("AnimalReportWriter (stretch)")
class AnimalReportWriterTest {

    private final AnimalReportWriter writer = new AnimalReportWriter();

    private Animal animal(String name, String species, int age, boolean vaccinated) {
        return new Animal(name, species, age, vaccinated, LocalDate.of(2026, Month.JANUARY, 15));
    }

    @Test
    @DisplayName("writes report file that contains total count")
    void shouldWriteTotalCount() throws IOException {
        List<Animal> animals = List.of(
                animal("Buddy", "Dog", 3, true),
                animal("Luna", "Cat", 2, true),
                animal("Max", "Dog", 5, false)
        );

        Path output = Files.createTempFile("report-test", ".txt");
        try {
            writer.writeReport(animals, output);

            String content = Files.readString(output, StandardCharsets.UTF_8);

            assertThat(content).contains("Total animals: 3");
        }finally {
            Files.deleteIfExists(output);
        }
    }

    @Test
    @DisplayName("writes per-species breakdown in alphabetical order")
    void shouldWriteSpeciesBreakdown() throws IOException {
        List<Animal> animals = List.of(
                animal("Buddy", "Dog", 3, true),
                animal("Luna", "Cat", 2, true),
                animal("Max", "Dog", 5, false)
        );

        Path output = Files.createTempFile("report-species", ".txt");
        try{
            writer.writeReport(animals, output);

            String content = Files.readString(output, StandardCharsets.UTF_8);

            assertThat(content.indexOf("Cat:")).isLessThan(content.indexOf("Dog:"));

            assertThat(content).contains("Cat: 1 total, 1 vaccinated");
            assertThat(content).contains("Dog: 2 total, 1 vaccinated");
        }finally {
            Files.deleteIfExists(output);
        }
    }

    @Test
    @DisplayName("writes oldest animal per species")
    void shouldWriteOldestPerSpecies() throws IOException {
        List<Animal> animals = List.of(
                animal("Buddy", "Dog", 3, true),
                animal("Max", "Dog", 5, false),
                animal("Luna", "Cat", 2, true)
        );

        Path output = Files.createTempFile("report-oldest", ".txt");
        try {
            writer.writeReport(animals, output);

            String content = Files.readString(output, StandardCharsets.UTF_8);

            assertThat(content).contains("Dog: Max (age 5)");
        } finally {
            Files.deleteIfExists(output);
        }
    }

    //Stretch goal 2
    @Test
    @DisplayName("stream assertion: every per-species line is well formed and grouped")
    void shouldVerifyGroupedContentWithStreamAssertion() throws IOException {
        List<Animal> animals = List.of(
                animal("Buddy", "Dog", 3, true),
                animal("Max", "Dog", 5, false),
                animal("Luna", "Cat", 2, true),
                animal("Bella", "Cat", 1, true)
        );
        Path output = Files.createTempFile("report-stream", ".txt");
        try {
            writer.writeReport(animals, output);

            List<String> lines = Files.readAllLines(output, StandardCharsets.UTF_8);

            assertThat(lines)
                    .filteredOn(line -> line.matches("^(Cat|Dog): \\d+ total, \\d+ vaccinated$"))
                    .hasSize(2)
                    .allSatisfy(line -> assertThat(line).contains("total").contains("vaccinated"));

            assertThat(lines).anySatisfy(line -> assertThat(line).isEqualTo("Cat: 2 total, 2 vaccinated"));
            assertThat(lines).anySatisfy(line -> assertThat(line).isEqualTo("Dog: 2 total, 1 vaccinated"));
        } finally {
            Files.deleteIfExists(output);
        }
    }

    //Stretch goal 3
    @Test
    @DisplayName("spy: verifies writeReport is invoked while keeping real writing behavior")
    void shouldVerifyWriteReportCalledViaSpy() throws IOException {
        AnimalReportWriter spyWriter = spy(new AnimalReportWriter());
        List<Animal> animals = List.of(
                animal("Buddy", "Dog", 3, true),
                animal("Luna", "Cat", 2, true)
        );
        Path output = Files.createTempFile("report-spy", ".txt");
        try {

            doCallRealMethod().when(spyWriter).writeReport(animals, output);

            spyWriter.writeReport(animals, output);

            verify(spyWriter, times(1)).writeReport(animals, output);

            String content = Files.readString(output, StandardCharsets.UTF_8);
            assertThat(content).contains("Total animals: 2");
            Mockito.verifyNoMoreInteractions(spyWriter);
        } finally {
            Files.deleteIfExists(output);
        }
    }
}
