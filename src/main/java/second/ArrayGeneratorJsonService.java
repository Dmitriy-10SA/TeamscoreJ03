package second;

import com.google.gson.Gson;

/**
 * Класс, создающий выбранный генератор и возвращающий созданный массив в виде JSON-строки
 *
 * @see ArrayGeneratorType
 */
public class ArrayGeneratorJsonService {
    private static final Gson gson = new Gson();

    private final ArrayGenerator<?> arrayGenerator;

    public ArrayGeneratorJsonService(ArrayGeneratorType arrayGeneratorType) {
        this.arrayGenerator = arrayGeneratorType.createNewArrayGenerator();
    }

    public String generateArrayAndGetJson(int size) {
        return gson.toJson(arrayGenerator.generateArray(size));
    }
}
