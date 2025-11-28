package first.shipment.document;

import first.Warehouse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovingShipmentDocumentTest {
    private static final Warehouse TEST_STORAGE = new Warehouse("main", "ACME");
    private static final Warehouse TEST_SAME_OWNER_WAREHOUSE = new Warehouse("branch", "ACME");
    private static final Warehouse TEST_OTHER_OWNER_WAREHOUSE = new Warehouse("partner", "OtherCo");
    private static final LocalDate DOC_DATE = LocalDate.of(2025, 5, 10);

    private static MovingShipmentDocument movingDocument(Warehouse movingWarehouse) {
        return new MovingShipmentDocument("id", DOC_DATE, TEST_STORAGE, List.of(), movingWarehouse);
    }

    @Test
    @DisplayName("isInternalMovement возвращает true при одинаковом владельце складов")
    void isInternalMovementTrueWhenOwnersSame() {
        MovingShipmentDocument document = movingDocument(TEST_SAME_OWNER_WAREHOUSE);
        assertTrue(document.isInternalMovement());
    }

    @Test
    @DisplayName("isInternalMovement возвращает false при разных владельцах")
    void isInternalMovementFalseWhenOwnersNotSame() {
        MovingShipmentDocument document = movingDocument(TEST_OTHER_OWNER_WAREHOUSE);
        assertFalse(document.isInternalMovement());
    }

    @Test
    @DisplayName("MovingShipmentDocument возвращает корректный тип")
    void movingDocumentReturnCorrectType() {
        MovingShipmentDocument document = movingDocument(TEST_SAME_OWNER_WAREHOUSE);
        assertEquals(ShipmentDocument.DocumentType.MOVING, document.getDocumentType());
    }
}

