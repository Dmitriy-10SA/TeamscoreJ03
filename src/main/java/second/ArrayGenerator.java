package second;

/**
 * Интерфейс ArrayGenerator для генерации массива заданной длины
 *
 * @param <T>
 */
@FunctionalInterface
public interface ArrayGenerator<T> {
    T[] generateArray(int size);
}
