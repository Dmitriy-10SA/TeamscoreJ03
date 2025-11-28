package second;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Генератор случайных целых чисел от 1 до 6 (броски кубика)
 *
 * @see ArrayGenerator
 */
public class DiceArrayGenerator implements ArrayGenerator<Integer> {
    @Override
    public Integer[] generateArray(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Размер массива должен быть положительным числом");
        }
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = ThreadLocalRandom.current().nextInt(6) + 1;
        }
        return array;
    }
}
