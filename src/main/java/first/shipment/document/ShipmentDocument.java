package first.shipment.document;

import first.Warehouse;
import first.shipment.ShipmentItem;

import java.time.LocalDate;
import java.util.List;

/**
 * Документ отгрузки со склада
 *
 * @see ShipmentItem
 * @see Warehouse
 */
public abstract class ShipmentDocument {
    private final String documentId; // GUID документа
    private final LocalDate documentDate; // дата документа
    private final Warehouse storageWarehouse; // склад отгрузки
    private final List<ShipmentItem> shipmentItems; //информация о товарах в документе

    public ShipmentDocument(
            String documentId,
            LocalDate documentDate,
            Warehouse storageWarehouse,
            List<ShipmentItem> shipmentItems
    ) {
        this.documentId = documentId;
        this.documentDate = documentDate;
        this.storageWarehouse = storageWarehouse;
        this.shipmentItems = List.copyOf(shipmentItems);
    }

    /**
     * Суммарная стоимость товаров в документе.
     */
    public final double totalAmount() {
        double sum = 0;
        for (ShipmentItem shipmentItem : shipmentItems) {
            sum += shipmentItem.getAmount();
        }
        return sum;
    }

    /**
     * Стоимость товара с переданным id.
     */
    public final double itemAmount(String id) {
        for (ShipmentItem shipmentItem : shipmentItems) {
            if (shipmentItem.id().equals(id)) {
                return shipmentItem.getAmount();
            }
        }
        return 0;
    }

    /**
     * Суммарная стоимость товаров, попадающих в список промо-акции.
     */
    public final double promoSum(String[] promoArticles) {
        double sum = 0;
        for (ShipmentItem shipmentItem : shipmentItems) {
            for (String promoArticle : promoArticles) {
                if (shipmentItem.article().equals(promoArticle)) {
                    sum += shipmentItem.getAmount();
                    break;
                }
            }
        }
        return sum;
    }

    /**
     * тип отгрузки: SALE - продажа, MOVING - перемещение
     */
    public enum DocumentType {
        SALE,
        MOVING
    }

    public final String getDocumentId() {
        return documentId;
    }

    public final LocalDate getDocumentDate() {
        return documentDate;
    }

    public abstract DocumentType getDocumentType();

    public final Warehouse getStorageWarehouse() {
        return storageWarehouse;
    }

    public final List<ShipmentItem> getShipmentItems() {
        return shipmentItems;
    }
}
