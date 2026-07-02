package lv.bootcamp.shelter.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Animal")
class AnimalTest {

    private static final LocalDate INTAKE_DATE = LocalDate.of(2026, Month.JANUARY, 15);

    private Animal buddy;

    @BeforeEach
    void setUp() {
        buddy = new Animal("Buddy", "Dog", 3, true, INTAKE_DATE);
    }

    // --- Constructor and getters ---

    @Test
    @DisplayName("constructor sets all fields correctly")
    void shouldSetAllFields() {
        assertThat(buddy)
                .extracting(Animal::getName, Animal::getSpecies, Animal::getAge,
                        Animal::isVaccinated, Animal::getIntakeDate)
                .containsExactly("Buddy", "Dog", 3, true, INTAKE_DATE);
    }

    // --- equals ---

    @Nested
    @DisplayName("equals")
    class Equals {

        @Test
        @DisplayName("returns true for same reference")
        void shouldReturnTrueForSameReference() {
            Animal sameReference = buddy;

            assertThat(buddy).isEqualTo(sameReference);
        }

        @Test
        @DisplayName("returns true for equal object with same field values")
        void shouldReturnTrueForEqualObject() {
            Animal same = new Animal("Buddy", "Dog", 3, true, INTAKE_DATE);

            assertThat(buddy).isEqualTo(same);
        }

        @Test
        @DisplayName("returns false for null")
        void shouldReturnFalseForNull() {
            assertThat(buddy.equals(null)).isFalse();
        }

        @Test
        @DisplayName("returns false for different class")
        void shouldReturnFalseForDifferentClass() {
            Object animalObj = buddy;
            Object stringObj = "not an animal";

            assertThat(animalObj).isNotEqualTo(stringObj);
        }

        @Test
        @DisplayName("returns false when age differs")
        void shouldReturnFalseForDifferentAge() {
            Animal differentAge = new Animal("Buddy", "Dog", 5, true, INTAKE_DATE);

            assertThat(buddy).isNotEqualTo(differentAge);
        }

        @Test
        @DisplayName("returns false when vaccinated differs")
        void shouldReturnFalseForDifferentVaccinated() {
            Animal differentVacc = new Animal("Buddy", "Dog", 3, false, INTAKE_DATE);

            assertThat(buddy).isNotEqualTo(differentVacc);
        }

        @Test
        @DisplayName("returns false when name differs")
        void shouldReturnFalseForDifferentName() {
            Animal differentName = new Animal("Max", "Dog", 3, true, INTAKE_DATE);

            assertThat(buddy).isNotEqualTo(differentName);
        }

        @Test
        @DisplayName("returns false when species differs")
        void shouldReturnFalseForDifferentSpecies() {
            Animal differentSpecies = new Animal("Buddy", "Cat", 3, true, INTAKE_DATE);

            assertThat(buddy).isNotEqualTo(differentSpecies);
        }

        @Test
        @DisplayName("returns false when intakeDate differs")
        void shouldReturnFalseForDifferentIntakeDate() {
            Animal differentDate = new Animal("Buddy", "Dog", 3, true,
                    LocalDate.of(2026, Month.FEBRUARY, 1));

            assertThat(buddy).isNotEqualTo(differentDate);
        }
    }

    // --- hashCode ---

    @Nested
    @DisplayName("hashCode")
    class HashCode {

        @Test
        @DisplayName("equal objects produce the same hash code")
        void shouldReturnSameHashForEqualObjects() {
            Animal same = new Animal("Buddy", "Dog", 3, true, INTAKE_DATE);

            assertThat(buddy).hasSameHashCodeAs(same);
        }

        @Test
        @DisplayName("different objects typically produce different hash codes")
        void shouldReturnDifferentHashForDifferentObjects() {
            Animal other = new Animal("Luna", "Cat", 2, false,
                    LocalDate.of(2026, Month.JANUARY, 10));

            assertThat(buddy.hashCode()).isNotEqualTo(other.hashCode());
        }
    }

    // --- toString ---

    @Nested
    @DisplayName("toString")
    class ToString {

        @Test
        @DisplayName("includes all fields and shows 'vaccinated' when true")
        void shouldFormatVaccinatedAnimal() {
            assertThat(buddy.toString())
                    .contains("Buddy")
                    .contains("Dog")
                    .contains("age 3")
                    .contains("vaccinated")
                    .contains("2026-01-15")
                    .doesNotContain("not vaccinated");
        }

        @Test
        @DisplayName("shows 'not vaccinated' when false")
        void shouldFormatUnvaccinatedAnimal() {
            Animal unvacc = new Animal("Max", "Dog", 5, false,
                    LocalDate.of(2026, Month.JANUARY, 18));

            assertThat(unvacc.toString()).contains("not vaccinated");
        }
    }
}