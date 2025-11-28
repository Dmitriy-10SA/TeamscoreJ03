package first.shipment.document;

import first.Warehouse;
import first.shipment.ShipmentItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipmentDocumentTest {
    private static final Warehouse TEST_STORAGE = new Warehouse("test_main_name", "test_main_owner");
    private static final LocalDate TEST_DATE = LocalDate.of(2025, 5, 10);
    private static final double DELTA = 0.001;

    private static ShipmentDocument testDocument(List<ShipmentItem> items) {
        return new ShipmentDocument("test_document", TEST_DATE, TEST_STORAGE, items) {
            @Override
            public DocumentType getDocumentType() {
                return null;
            }
        };
    }

    @Test
    @DisplayName("totalAmount возвращает верно округленную сумму вверх")
    void totalAmountReturnCorrectlyRoundedUpSum() {
        ShipmentDocument document = testDocument(
                List.of(
                        new ShipmentItem("1", "1", "1", 2, 10.0),
                        new ShipmentItem("2", "2", "2", 3.5, 5.55)
                )
        );
        assertEquals(39.43, document.totalAmount(), DELTA);
    }

    @Test
    @DisplayName("totalAmount возвращает верно округленную сумму вниз")
    void totalAmountReturnCorrectlyRoundedDownSum() {
        ShipmentDocument document = testDocument(
                List.of(
                        new ShipmentItem("1", "1", "1", 2, 10.0),
                        new ShipmentItem("2", "2", "2", 3.4, 5.33)
                )
        );
        assertEquals(38.12, document.totalAmount(), DELTA);
    }

    @Test
    @DisplayName("totalAmount возвращает 0 для документа без товаров")
    void totalAmountReturnZeroForEmptyDocument() {
        ShipmentDocument emptyDocument = testDocument(List.of());
        assertEquals(0.00, emptyDocument.totalAmount(), DELTA);
    }

    @Test
    @DisplayName("itemAmount возвращает верную сумму для существующего товара")
    void itemAmountReturnCorrectAmountForExistingItem() {
        ShipmentDocument document = testDocument(
                List.of(
                        new ShipmentItem("1", "1", "1", 2, 10.0),
                        new ShipmentItem("2", "2", "2", 1, 7.25)
                )
        );
        assertEquals(7.25, document.itemAmount("2"));
    }

    @Test
    @DisplayName("itemAmount возвращает 0 в случае отсутствия товара")
    void itemAmountReturnZeroForMissingItem() {
        ShipmentDocument document = testDocument(
                List.of(new ShipmentItem("1", "1", "1", 2, 10.0))
        );
        assertEquals(0.00, document.itemAmount("2"));
    }

    @Test
    @DisplayName("itemAmount возвращает 0 в случае, когда товаров вообще нет")
    void itemAmountReturnZeroForEmptyDocument() {
        ShipmentDocument document = testDocument(List.of());
        assertEquals(0.00, document.itemAmount("2"));
    }

    @Test
    @DisplayName("promoSum суммирует только промо-позиции")
    void promoSumReturnCorrectCountOfPromoArticles() {
        ShipmentDocument document = testDocument(
                List.of(
                        new ShipmentItem("1", "promo_1", "1", 2, 5),
                        new ShipmentItem("2", "2", "2", 3, 4),
                        new ShipmentItem("3", "promo_3", "3", 1, 10)
                )
        );
        String[] promoArticles = {"promo_1", "promo_3"};
        assertEquals(20.00, document.promoSum(promoArticles));
    }

    @Test
    @DisplayName("promoSum возвращает 0 при отсутствии промо-позиции")
    void promoSumReturnZeroWhenEmptyPromoArticles() {
        ShipmentDocument document = testDocument(
                List.of(
                        new ShipmentItem("1", "promo_1", "1", 2, 5),
                        new ShipmentItem("2", "2", "2", 3, 4),
                        new ShipmentItem("3", "promo_3", "3", 1, 10)
                )
        );
        String[] promoArticles = {"promo_2"};
        assertEquals(0.00, document.promoSum(promoArticles));
    }

    @Test
    @DisplayName("promoSum возвращает 0 при отсутствии товаров в документе")
    void promoSumReturnZeroWhenDocumentEmpty() {
        ShipmentDocument document = testDocument(List.of());
        String[] promoArticles = {"promo_1", "promo_3"};
        assertEquals(0.00, document.promoSum(promoArticles));
    }
}

