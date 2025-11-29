package third.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerTest {
    @ParameterizedTest
    @DisplayName("getPrice — корректное округление до 2 знаков")
    @CsvSource({
            "10,       10.00",
            "10.1,     10.10",
            "10.123,   10.12",
            "10.125,   10.13",
            "10.129,   10.13"
    })
    void manufacturerPriceRounding(String input, String expected) {
        Manufacturer manufacturer = new Manufacturer("1234567890", "Man", "Addr");
        BigDecimal price = manufacturer.getPrice(new BigDecimal(input));
        assertEquals(new BigDecimal(expected), price);
    }

    @Test
    @DisplayName("getPrice — отрицательная цена вызывает ошибку")
    void manufacturerThrowsOnNegativePrice() {
        Manufacturer manufacturer = new Manufacturer("1234567890", "Man", "Addr");
        assertThrows(
                IllegalArgumentException.class,
                () -> manufacturer.getPrice(new BigDecimal("-1"))
        );
    }

    @Test
    @DisplayName("getRealManufacturer возвращает самого себя")
    void getRealManufacturerReturnsSelf() {
        Manufacturer manufacturer = new Manufacturer("1234567890", "Man", "Addr");
        assertSame(manufacturer, manufacturer.getRealManufacturer());
    }
}