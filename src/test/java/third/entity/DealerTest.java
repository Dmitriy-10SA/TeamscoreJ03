package third.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {
    private static Manufacturer testManufacturer() {
        return new Manufacturer("1234567890", "Man", "Addr");
    }

    @ParameterizedTest
    @DisplayName("Dealer — корректно считает цену с наценкой и округлением")
    @CsvSource({
            "100,   10,     110.00",
            "100,   33.33,  133.33",
            "50,    5.5,    52.75",
            "10.123, 10,    11.13"
    })
    void dealerPriceWithMarkup(String manufacturerPrice, String markup, String expected) {
        Dealer dealer = new Dealer(
                testManufacturer(),
                new BigDecimal(markup),
                "0987654321",
                "Dealer",
                "Addr"
        );
        BigDecimal result = dealer.getPrice(new BigDecimal(manufacturerPrice));
        assertEquals(new BigDecimal(expected), result);
    }

    @Test
    @DisplayName("Dealer — отрицательная цена вызывает ошибку")
    void dealerThrowsOnNegativePrice() {
        Dealer dealer = new Dealer(
                testManufacturer(),
                new BigDecimal("10"),
                "0987654321",
                "Dealer",
                "Addr"
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> dealer.getPrice(new BigDecimal("-1"))
        );
    }

    @Test
    @DisplayName("Dealer — отрицательная наценка вызывает ошибку")
    void dealerThrowsOnNegativeMarkup() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Dealer(
                        testManufacturer(),
                        new BigDecimal("-5"),
                        "0987654321",
                        "Dealer",
                        "Addr"
                )
        );
    }

    @Test
    @DisplayName("Dealer — getRealManufacturer возвращает переданного производителя")
    void dealerReturnsCorrectManufacturer() {
        Manufacturer manufacturer = testManufacturer();
        Dealer dealer = new Dealer(
                manufacturer,
                new BigDecimal("10"),
                "0987654321",
                "Dealer",
                "Addr"
        );
        assertSame(manufacturer, dealer.getRealManufacturer());
    }

    @ParameterizedTest
    @DisplayName("Dealer — наценка сохраняется с округлением HALF_UP до 2 знаков")
    @CsvSource({
            "10.1,      10.10",
            "10.125,    10.13",
            "33.3333,   33.33"
    })
    void dealerMarkupStoredCorrectly(String input, String expected) {
        Dealer dealer = new Dealer(
                testManufacturer(),
                new BigDecimal(input),
                "0987654321",
                "Dealer",
                "Addr"
        );
        assertEquals(new BigDecimal(expected), dealer.getMarkupPercent());
    }
}