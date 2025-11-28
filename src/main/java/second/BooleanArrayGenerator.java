package second;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Генератор булевого массива
 *
 * @see ArrayGenerator
 */
public class BooleanArrayGenerator implements ArrayGenerator<Boolean> {
    @Override
    public Boolean[] generateArray(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Размер массива должен быть положительным числом");
        }
        Boolean[] array = new Boolean[size];
        for (int i = 0; i < size; i++) {
            array[i] = ThreadLocalRandom.current().nextBoolean();
        }
        return array;
    }
}
