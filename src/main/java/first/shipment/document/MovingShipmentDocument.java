package first.shipment.document;

import first.Warehouse;
import first.shipment.ShipmentItem;

import java.time.LocalDate;
import java.util.List;

/**
 * Документ отгрузки со склада типа перемещение
 *
 * @see ShipmentDocument
 * @see Warehouse
 */
public class MovingShipmentDocument extends ShipmentDocument {
    private final Warehouse movingWarehouse; //склад получения

    public MovingShipmentDocument(
            String documentId,
            LocalDate documentDate,
            Warehouse storageWarehouse,
            List<ShipmentItem> shipmentItems,
            Warehouse movingWarehouse
    ) {
        super(documentId, documentDate, storageWarehouse, shipmentItems);
        this.movingWarehouse = movingWarehouse;
    }

    /**
     * Является ли перемещение внутренним (между складами одного владельца).
     */
    public boolean isInternalMovement() {
        return getStorageWarehouse().owner().equals(movingWarehouse.owner());
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.MOVING;
    }

    public Warehouse getMovingWarehouse() {
        return movingWarehouse;
    }
}
