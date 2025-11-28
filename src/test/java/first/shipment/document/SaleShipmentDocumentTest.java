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
    private static final double DELTA = 0.001;

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

    @Test
    @DisplayName("promoSum с заданной скидкой возвращает корректную сумму")
    void promoSumReturnCorrectSumWithDiscount() {
        SaleShipmentDocument document = testDocument(
                List.of(
                        new ShipmentItem("1", "A", "A", 2, 10.0),
                        new ShipmentItem("3", "A2", "A1", 5, 10.0),
                        new ShipmentItem("2", "B", "B", 3, 3.0)
                )
        );
        String[] promoArticles = {"A", "B"};
        assertEquals(26.1, document.promoSum(promoArticles, 10), DELTA);
    }

    @Test
    @DisplayName("promoSum корректно округляет вверх до копеек")
    void promoSumRoundUpCorrectly() {
        SaleShipmentDocument document = testDocument(
                List.of(new ShipmentItem("1", "A", "A", 1, 9.999))
        );
        String[] promo = {"A"};
        assertEquals(9.00, document.promoSum(promo, 10), DELTA);
    }

    @Test
    @DisplayName("promoSum возвращает 0 если нет промо-товаров")
    void promoSumReturnsZeroIfNoPromo() {
        SaleShipmentDocument document = testDocument(
                List.of(new ShipmentItem("1", "A", "A", 2, 10.0))
        );
        String[] promo = {"X"};
        assertEquals(0.0, document.promoSum(promo, 20), DELTA);
    }

    @Test
    @DisplayName("promoSum со скидкой 0% возвращает обычную сумму promoSum")
    void promoSumZeroPercentDiscount() {
        SaleShipmentDocument document = testDocument(
                List.of(
                        new ShipmentItem("1", "A", "A1", 1, 141.0),
                        new ShipmentItem("2", "B", "A2", 12, 141.0),
                        new ShipmentItem("3", "C", "A3", 100, 10.421),
                        new ShipmentItem("4", "D", "A4", 4, 1314.41),
                        new ShipmentItem("5", "E", "A5", 50, 4.4)
                )
        );
        String[] promo = {"A"};
        assertEquals(document.promoSum(promo), document.promoSum(promo, 0.0), DELTA);
    }

    @Test
    @DisplayName("promoSum со скидкой 100% возвращает 0")
    void promoSumHundredPercentDiscount() {
        SaleShipmentDocument document = testDocument(
                List.of(
                        new ShipmentItem("1", "A", "A1", 1, 141.0),
                        new ShipmentItem("2", "B", "A2", 12, 141.0)
                )
        );
        String[] promo = {"A"};
        assertEquals(0.0, document.promoSum(promo, 100), DELTA);
    }

    @Test
    @DisplayName("promoSum выбрасывает исключение при отрицательной скидке")
    void promoSumThrowsForNegativeDiscount() {
        SaleShipmentDocument document = testDocument(List.of());
        String[] promo = {"A"};
        assertThrows(IllegalArgumentException.class, () -> document.promoSum(promo, -5));
    }

    @Test
    @DisplayName("promoSum выбрасывает исключение при скидке больше 100%")
    void promoSumThrowsForDiscountAbove100() {
        SaleShipmentDocument document = testDocument(List.of());
        String[] promo = {"A"};
        assertThrows(IllegalArgumentException.class, () -> document.promoSum(promo, 101));
    }

    @Test
    @DisplayName("promoSum возвращает 0 для пустого документа")
    void promoSumReturnsZeroForEmptyDocument() {
        SaleShipmentDocument document = testDocument(List.of());
        String[] promo = {"A"};
        assertEquals(0.0, document.promoSum(promo, 10), DELTA);
    }
}

