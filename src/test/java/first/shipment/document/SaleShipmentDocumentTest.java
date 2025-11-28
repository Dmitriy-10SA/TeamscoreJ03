package first.shipment.document;

import first.Warehouse;
import first.shipment.ShipmentItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaleShipmentDocumentTest {
    private static final Warehouse TEST_STORAGE = new Warehouse("test_main_name", "test_main_owner");
    private static final LocalDate TEST_DATE = LocalDate.of(2025, 5, 10);

    private static SaleShipmentDocument testDocument(List<ShipmentItem> items) {
        return new SaleShipmentDocument("id", TEST_DATE, TEST_STORAGE, items, "customer");
    }

    @Test
    @DisplayName("isWholesale возвращает true, если есть позиция >= минимального объема")
    void isWholesaleReturnTrueWhenItemQuantityMoreThanMinQuantity() {
        SaleShipmentDocument document = testDocument(
                List.of(
                        new ShipmentItem("1", "A", "A", 5, 3.99),
                        new ShipmentItem("3", "C", "C", 5, 10.0),
                        new ShipmentItem("2", "B", "B", 1, 3.0)
                )
        );
        assertTrue(document.isWholesale(4));
    }

    @Test
    @DisplayName("isWholesale возвращает true, если суммарное количество >= минимального объема")
    void isWholesaleReturnTrueWhenTotalQuantityMoreThanMinQuantity() {
        SaleShipmentDocument document = testDocument(
                List.of(
                        new ShipmentItem("1", "A", "A", 2, 10.0),
                        new ShipmentItem("2", "B", "B", 3, 3.0)
                )
        );
        assertTrue(document.isWholesale(5));
    }

    @Test
    @DisplayName("isWholesale возвращает false, если суммарное количество меньше минимального")
    void isWholesaleReturnFalseWhenTotalQuantityLessThanMinQuantity() {
        SaleShipmentDocument document = testDocument(
                List.of(
                        new ShipmentItem("1", "A", "A", 1, 10.0),
                        new ShipmentItem("2", "B", "B", 2, 3.0)
                )
        );
        assertFalse(document.isWholesale(4));
    }

    @Test
    @DisplayName("SaleShipmentDocument возвращает корректный тип")
    void saleDocumentReturnsCorrectType() {
        SaleShipmentDocument document = testDocument(List.of());
        assertEquals(ShipmentDocument.DocumentType.SALE, document.getDocumentType());
    }
}

