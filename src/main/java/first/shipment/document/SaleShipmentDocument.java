package first.shipment.document;

import first.Warehouse;
import first.shipment.ShipmentItem;

import java.time.LocalDate;
import java.util.List;

/**
 * Документ отгрузки со склада типа продажа
 *
 * @see ShipmentDocument
 * @see Warehouse
 */
public class SaleShipmentDocument extends ShipmentDocument {
    private final String customer; //получатель

    public SaleShipmentDocument(
            String documentId,
            LocalDate documentDate,
            Warehouse storageWarehouse,
            List<ShipmentItem> shipmentItems,
            String customer
    ) {
        super(documentId, documentDate, storageWarehouse, shipmentItems);
        this.customer = customer;
    }

    /**
     * Является ли продажа оптовой для переданного минимального объема.
     */
    public boolean isWholesale(double minQuantity) {
        List<ShipmentItem> shipmentItems = getShipmentItems();
        double sumQuantity = 0;
        for (ShipmentItem shipmentItem : shipmentItems) {
            if (shipmentItem.quantity() >= minQuantity) {
                return true;
            }
            sumQuantity += shipmentItem.quantity();
        }
        return sumQuantity >= minQuantity;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.SALE;
    }

    public String getCustomer() {
        return customer;
    }
}
