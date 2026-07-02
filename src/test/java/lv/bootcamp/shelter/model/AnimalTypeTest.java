package lv.bootcamp.shelter.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class AnimalTypeTest {

    @ParameterizedTest
    @CsvSource({
            "DOG, 7, false",  // 15 / 2 = 7. int division matches 7, age 7 is not > 7
            "DOG, 8, true",   // Senior threshold boundary passed
            "CAT, 9, false",  // 18 / 2 = 9. age 9 is not > 9
            "CAT, 10, true",  // Senior threshold boundary passed
            "BIRD, 12, false",// 25 / 2 = 12. age 12 is not > 12
            "BIRD, 13, true"
    })
    @DisplayName("Should correctly identify senior animals based on lifespan boundaries")
    void shouldIdentifySeniorAnimals(AnimalType type, int age, boolean expected) {
        assertThat(type.isSenior(age)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "Dog, DOG",
            "  cat  , CAT",
            "BIRD, BIRD",
            "rabbit, RABBIT",
            "invalid-type, ",
            "'', ",
            "   , "
    })
    @DisplayName("Should look up enum from display name case-insensitively with trimming handling")
    void shouldFromDisplayName(String input, AnimalType expected) {
        assertThat(AnimalType.fromDisplayName(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "DOG, Dog",
            "CAT, Cat"
    })
    @DisplayName("Should verify base field properties for enums")
    void shouldVerifyProperties(AnimalType type, String expectedDisplayName) {
        assertThat(type.getDisplayName()).isEqualTo(expectedDisplayName);
        assertThat(type.getAverageLifespanYears()).isPositive();
    }
}