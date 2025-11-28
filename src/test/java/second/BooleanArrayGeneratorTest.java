package second;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BooleanArrayGeneratorTest {
    @Test
    @DisplayName("generateArray возвращает массив требуемой длины из Boolean-значений")
    void generateArrayReturnsBooleanValues() {
        BooleanArrayGenerator generator = new BooleanArrayGenerator();
        Boolean[] result = generator.generateArray(10_000);
        assertEquals(10_000, result.length);
        for (Boolean value : result) {
            assertInstanceOf(Boolean.class, value);
        }
    }

    @Test
    @DisplayName("generateArray с size = 0 возвращает пустой массив")
    void generateArrayWithZeroReturnsEmptyArray() {
        BooleanArrayGenerator generator = new BooleanArrayGenerator();
        Boolean[] result = generator.generateArray(0);
        assertEquals(0, result.length);
    }

    @Test
    @DisplayName("generateArray с отрицательной длиной выбрасывает IllegalArgumentException")
    void generateArrayWithNegativeSizeThrows() {
        BooleanArrayGenerator generator = new BooleanArrayGenerator();
        assertThrows(IllegalArgumentException.class, () -> generator.generateArray(-5));
    }
}

