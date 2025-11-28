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

    /**
     * Суммарная стоимость товаров с учетом скидки, попадающих в список промо-акции.
     * Для каждого товара из промо-акции применяем скидку (с округлением до копеек в большую сторону)
     *
     * @param discountPercent скидка в процентах
     */
    public double promoSum(String[] promoArticles, double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Скидка должна быть в диапазоне от 0 до 100!");
        }
        List<ShipmentItem> shipmentItems = getShipmentItems();
        double sum = 0;
        for (ShipmentItem shipmentItem : shipmentItems) {
            for (String promoArticle : promoArticles) {
                if (shipmentItem.article().equals(promoArticle)) {
                    double amount = shipmentItem.getAmount();
                    double amountWithDiscount = amount * (1 - discountPercent / 100);
                    sum += Math.ceil(amountWithDiscount * 100) / 100;
                    break;
                }
            }
        }
        return sum;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.SALE;
    }

    public String getCustomer() {
        return customer;
    }
}
