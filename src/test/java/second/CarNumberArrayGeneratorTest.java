package second;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class CarNumberArrayGeneratorTest {
    private static final Pattern PATTERN = Pattern.compile("^[АВЕКМОРСТУХ][0-9]{3}[АВЕКМОРСТУХ]{2}[0-9]{3}$");

    @Test
    @DisplayName("generateArray возвращает номера в формате А777АА777")
    void generateArrayReturnsNumbersInExpectedFormat() {
        CarNumberArrayGenerator generator = new CarNumberArrayGenerator();
        String[] numbers = generator.generateArray(10_000);
        assertEquals(10_000, numbers.length);
        for (String number : numbers) {
            assertTrue(PATTERN.matcher(number).matches());
        }
    }

    @Test
    @DisplayName("generateArray с size = 0 возвращает пустой массив")
    void generateArrayWithZeroReturnsEmptyArray() {
        CarNumberArrayGenerator generator = new CarNumberArrayGenerator();
        assertEquals(0, generator.generateArray(0).length);
    }

    @Test
    @DisplayName("generateArray с отрицательным size выбрасывает IllegalArgumentException")
    void generateArrayWithNegativeSizeThrows() {
        CarNumberArrayGenerator generator = new CarNumberArrayGenerator();
        assertThrows(IllegalArgumentException.class, () -> generator.generateArray(-3));
    }
}

