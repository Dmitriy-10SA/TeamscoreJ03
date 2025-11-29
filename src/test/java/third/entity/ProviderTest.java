package third.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProviderTest {
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

    @ParameterizedTest
    @DisplayName("Provider — корректные ИНН 10 и 12 символов принимаются")
    @CsvSource({
            "1234567890",
            "123456789012"
    })
    void providerAcceptsValidInn(String inn) {
        Provider provider = new TestProvider(inn, "Имя", "Адрес");
        assertEquals(inn, provider.getInn());
    }

    @ParameterizedTest
    @DisplayName("Provider — неверная длина ИНН вызывает ошибку")
    @CsvSource({
            "123",
            "123456789",
            "12345678901",
            "1234567890123"
    })
    void providerThrowsOnWrongInnLength(String inn) {
        var ex = assertThrows(
                IllegalArgumentException.class,
                () -> new TestProvider(inn, "Имя", "Адрес")
        );
        assertEquals("ИНН должен быть длиной 10 или 12 символов", ex.getMessage());
    }

    @Test
    @DisplayName("Provider — ИНН с недопустимыми символами вызывает ошибку")
    void providerThrowsOnNonDigitInn() {
        var ex = assertThrows(
                IllegalArgumentException.class,
                () -> new TestProvider("12345abcde", "Имя", "Адрес")
        );
        assertEquals("ИНН должен состоять только из цифр", ex.getMessage());
    }
}