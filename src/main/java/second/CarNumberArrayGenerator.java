package second;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Генератор массива автомобильных номеров
 *
 * @see ArrayGenerator
 */
public class CarNumberArrayGenerator implements ArrayGenerator<String> {
    private static final char[] LETTERS = {'А', 'В', 'Е', 'К', 'М', 'О', 'Р', 'С', 'Т', 'У', 'Х'};

    private static char getRandomLetter() {
        return LETTERS[ThreadLocalRandom.current().nextInt(LETTERS.length)];
    }

    private static int getRandomNumberInRangeFrom0To9() {
        return ThreadLocalRandom.current().nextInt(10);
    }

    /**
     * Генерация рандомного номера авто, например, А777АА777
     *
     * @return рандомный номер авто
     */
    private static String getRandomCarNumber() {
        return String.valueOf(getRandomLetter()) +
                getRandomNumberInRangeFrom0To9() +
                getRandomNumberInRangeFrom0To9() +
                getRandomNumberInRangeFrom0To9() +
                getRandomLetter() +
                getRandomLetter() +
                getRandomNumberInRangeFrom0To9() +
                getRandomNumberInRangeFrom0To9() +
                getRandomNumberInRangeFrom0To9();
    }

    @Override
    public String[] generateArray(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Размер массива должен быть положительным числом");
        }
        String[] array = new String[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = getRandomCarNumber();
        }
        return array;
    }
}
