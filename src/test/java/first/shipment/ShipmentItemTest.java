package first.shipment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipmentItemTest {
    private static final double DELTA = 0.001;

    private static ShipmentItem testItem(double quantity, double price) {
        return new ShipmentItem("id", "article", "title", quantity, price);
    }

    @ParameterizedTest
    @DisplayName("getAmount — округление вниз")
    @CsvSource({
            "1.999, 7.777, 15.55",
            "2.999, 8.888, 26.66",
            "3.141, 9.001, 28.27"
    })
    void getAmount_roundDown(double quantity, double price, double expected) {
        ShipmentItem item = testItem(quantity, price);
        assertEquals(expected, item.getAmount(), DELTA);
    }

    @ParameterizedTest
    @DisplayName("getAmount — нулевые значения")
    @CsvSource({
            "0.0, 99.99, 0.0",
            "1.0, 0.0, 0.0",
            "0.0, 0.0, 0.0"
    })
    void getAmount_zeroAmounts(double quantity, double price, double expected) {
        ShipmentItem item = testItem(quantity, price);
        assertEquals(expected, item.getAmount(), DELTA);
    }

    @ParameterizedTest
    @DisplayName("getAmount — округление вверх")
    @CsvSource({
            "3.5, 5.55, 19.43",
            "3.999, 9.999, 39.99",
            "4.999, 10.10, 50.49"
    })
    void getAmount_roundUp(double quantity, double price, double expected) {
        ShipmentItem item = testItem(quantity, price);
        assertEquals(expected, item.getAmount(), DELTA);
    }
}

