package third.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemTest {
    private static class TestProvider extends Provider {
        public TestProvider(String inn, String name, String address) {
            super(inn, name, address);
        }

        @Override
        public BigDecimal getPrice(BigDecimal manufacturerPrice) {
            return manufacturerPrice;
        }

        @Override
        public Manufacturer getRealManufacturer() {
            return null;
        }
    }

    @Test
    @DisplayName("Item создаётся корректно при валидных данных")
    void itemCreatedSuccessfully() {
        Provider provider = new TestProvider("1234567890", "Имя", "Адрес");
        Item item = new Item("1234567890", "Товар", new BigDecimal("100.00"), provider);
        assertEquals("1234567890", item.article());
        assertEquals("Товар", item.name());
        assertEquals(new BigDecimal("100.00"), item.manufacturerPrice());
        assertEquals(provider, item.provider());
    }

    @ParameterizedTest
    @DisplayName("Item — артикул неверной длины вызывает ошибку")
    @CsvSource({
            "12345",
            "1234567890123456"
    })
    void itemThrowsOnWrongArticleLength(String article) {
        Provider provider = new TestProvider("1234567890", "Имя", "Адрес");
        var ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Item(article, "Товар", new BigDecimal("10"), provider)
        );
        assertEquals("Артикул должен быть длиной от 10 до 15 символов", ex.getMessage());
    }

    @Test
    @DisplayName("Item — артикул с недопустимыми символами вызывает ошибку")
    void itemThrowsOnNonDigitArticle() {
        Provider provider = new TestProvider("1234567890", "Имя", "Адрес");
        var ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Item("12345abcde", "Товар", new BigDecimal("10"), provider)
        );
        assertEquals("Артикул должен состоять только из цифр", ex.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Item — отрицательная и нулевая цена вызывает ошибку")
    @CsvSource({
            "-1",
            "-10.55",
            "0"
    })
    void itemThrowsOnNegativePrice(String price) {
        Provider provider = new TestProvider("1234567890", "Имя", "Адрес");
        assertThrows(
                IllegalArgumentException.class,
                () -> new Item("1234567890", "Товар", new BigDecimal(price), provider)
        );
    }
}