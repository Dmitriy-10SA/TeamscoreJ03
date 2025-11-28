package second;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceArrayGeneratorTest {
    @Test
    @DisplayName("generateArray возвращает массив нужной длины со значениями 1..6")
    void generateArrayReturnsValuesBetweenOneAndSix() {
        DiceArrayGenerator generator = new DiceArrayGenerator();
        Integer[] values = generator.generateArray(10_000);
        assertEquals(10_000, values.length);
        for (int value : values) {
            assertTrue(value >= 1 && value <= 6);
        }
    }

    @Test
    @DisplayName("generateArray с size = 0 возвращает пустой массив")
    void generateArrayWithZeroReturnsEmptyArray() {
        DiceArrayGenerator generator = new DiceArrayGenerator();
        Integer[] values = generator.generateArray(0);
        assertEquals(0, values.length);
    }

    @Test
    @DisplayName("generateArray с отрицательным size выбрасывает IllegalArgumentException")
    void generateArrayWithNegativeSizeThrows() {
        DiceArrayGenerator generator = new DiceArrayGenerator();
        assertThrows(IllegalArgumentException.class, () -> generator.generateArray(-1));
    }
}

