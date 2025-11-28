package second;

import java.util.function.Supplier;

/**
 * Тип ArrayGenerator, который мы будем использовать
 *
 * @see ArrayGenerator
 * @see DiceArrayGenerator
 * @see BooleanArrayGenerator
 * @see CarNumberArrayGenerator
 */
public enum ArrayGeneratorType {
    DICE(DiceArrayGenerator::new),
    BOOLEAN(BooleanArrayGenerator::new),
    CAR_NUMBER(CarNumberArrayGenerator::new);

    private final Supplier<ArrayGenerator<?>> supplier;

    ArrayGeneratorType(Supplier<ArrayGenerator<?>> supplier) {
        this.supplier = supplier;
    }

    public ArrayGenerator<?> createNewArrayGenerator() {
        return supplier.get();
    }
}
