package lv.bootcamp.shelter.task23;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tasks 2 & 3: Parameterized tests and exception tests
 *
 * Practice:
 * - @ParameterizedTest with @CsvSource
 * - @ValueSource and @NullAndEmptySource
 * - assertThrows with message checks
 * - AssertJ assertThatThrownBy
 *
 */
@DisplayName("AnimalValidator")
class AnimalValidatorTest {

    private AnimalValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AnimalValidator();
    }

    private Animal animal(String name, String species, int age) {
        return new Animal(name, species, age, true, LocalDate.of(2026, Month.JANUARY, 15));
    }

    // ==================== Task 2: Parameterized tests ====================

    @Nested
    @DisplayName("validateName")
    class ValidateName {

        @ParameterizedTest
        @ValueSource(strings = {"Buddy", "Luna", "Mr. Whiskers", "X"})
        @DisplayName("accepts valid names")
        void shouldAcceptValidNames(String name) {
            assertDoesNotThrow(() -> validator.validateName(name));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        @DisplayName("rejects blank or null names")
        void shouldRejectBlankNames(String name) {
            assertThatThrownBy(() -> validator.validateName(name))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("must not be blank");
        }

        //additional test for full coverage
        @Test
        @DisplayName("accepts a name of exactly 100 characters (boundary)")
        void shouldAcceptNameOfExactlyHundredChars() {
            String name = "a".repeat(100);
            assertDoesNotThrow(() -> validator.validateName(name));
        }

        @Test
        @DisplayName("rejects name longer than 100 characters")
        void shouldRejectOverlyLongName() {
            String tooLong = "a".repeat(101);

            assertThatThrownBy(() -> validator.validateName(tooLong))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("100 characters");
        }
    }

    //additional test for full coverage
    @Nested
    @DisplayName("validateSpecies")
    class ValidateSpecies {

        @ParameterizedTest(name = "accepts \"{0}\"")
        @ValueSource(strings = {"Dog", "Cat", "Bird", "Rabbit"})
        @DisplayName("accepts valid species")
        void shouldAcceptValidSpecies(String species) {
            assertDoesNotThrow(() -> validator.validateSpecies(species));
        }

        @ParameterizedTest(name = "rejects [{0}]")
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t"})
        @DisplayName("rejects blank or null species")
        void shouldRejectBlankSpecies(String species) {
            assertThatThrownBy(() -> validator.validateSpecies(species))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("must not be blank");
        }
    }

    @Nested
    @DisplayName("validateAge")
    class ValidateAge {

        @ParameterizedTest
        @CsvSource({
                "0",
                "1",
                "10",
                "50"
        })
        @DisplayName("accepts valid ages")
        void shouldAcceptValidAges(int age) {
            assertDoesNotThrow(() -> validator.validateAge(age));
        }

        @ParameterizedTest
        @CsvSource({
                "-1, must not be negative",
                "-100, must not be negative",
                "51, seems unrealistic",
                "999, seems unrealistic"
        })
        @DisplayName("rejects invalid ages with correct message")
        void shouldRejectInvalidAges(int age, String expectedMessagePart) {
            assertThatThrownBy(() -> validator.validateAge(age))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(expectedMessagePart);
        }
    }

    // ==================== Task 3: Exception tests ====================

    @Nested
    @DisplayName("validate (full animal)")
    class ValidateFullAnimal {

        @Test
        @DisplayName("throws NullPointerException for null animal")
        void shouldThrowForNullAnimal() {
            NullPointerException exception = assertThrows(NullPointerException.class,
                    () -> validator.validate(null));
            assertThat(exception.getMessage()).contains("must not be null");
        }

        @Test
        @DisplayName("throws for animal with blank name")
        void shouldThrowForBlankName() {
            Animal invalid = animal(" ", "Dog", 3);

            assertThatThrownBy(() -> validator.validate(invalid))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Name must not be blank");
        }

        @Test
        @DisplayName("throws for animal with blank species")
        void shouldThrowForBlankSpecies() {
            Animal invalid = animal("Buddy", " ", 3);

            assertThatThrownBy(() -> validator.validate(invalid))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Species must not be blank");
        }

        @Test
        @DisplayName("throws for animal with negative age")
        void shouldThrowForNegativeAge() {
            Animal invalid = animal("Buddy", "Dog", -1);

            assertThatThrownBy(() -> validator.validate(invalid))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("must not be negative");
        }

        @Test
        @DisplayName("does not throw for fully valid animal")
        void shouldPassForValidAnimal() {
            Animal valid = new Animal("Buddy", "Dog", 3, true, LocalDate.of(2026, Month.JANUARY, 15));

            assertDoesNotThrow(() -> validator.validate(valid));
        }
    }
}
