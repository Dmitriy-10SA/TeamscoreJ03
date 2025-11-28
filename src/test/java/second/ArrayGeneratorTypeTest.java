package second;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ArrayGeneratorTypeTest {
    @Test
    @DisplayName("проверка верной выдачи генератора для DICE")
    void getDiceGenerator() {
        assertInstanceOf(DiceArrayGenerator.class, ArrayGeneratorType.DICE.createNewArrayGenerator());
    }

    @Test
    @DisplayName("проверка верной выдачи генератора для CAR_NUMBER")
    void getCarNumberGenerator() {
        assertInstanceOf(CarNumberArrayGenerator.class, ArrayGeneratorType.CAR_NUMBER.createNewArrayGenerator());
    }

    @Test
    @DisplayName("проверка верной выдачи генератора для BOOLEAN")
    void getBooleanGenerator() {
        assertInstanceOf(BooleanArrayGenerator.class, ArrayGeneratorType.BOOLEAN.createNewArrayGenerator());
    }
}