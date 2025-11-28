package second;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArrayGeneratorJsonServiceTest {
    private static final Gson gson = new Gson();

    @Test
    @DisplayName("Получение ошибки при попытке создать объект с отрицательной длиной массива для всех типов")
    void shouldThrowExceptionWhenArrayLengthIsNegative() {
        for (ArrayGeneratorType type : ArrayGeneratorType.values()) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new ArrayGeneratorJsonService(type).generateArrayAndGetJson(-1)
            );
        }
    }

    @Test
    @DisplayName("Проверка создания не пустого массива для всех типов")
    void shouldCreateNotEmptyArrayForAllTypes() {
        for (ArrayGeneratorType type : ArrayGeneratorType.values()) {
            String json = new ArrayGeneratorJsonService(type).generateArrayAndGetJson(10_000);
            Object[] arr = gson.fromJson(json, Object[].class);
            assertEquals(10_000, arr.length);
        }
    }
}

