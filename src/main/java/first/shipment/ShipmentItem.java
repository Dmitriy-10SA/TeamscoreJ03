package first.shipment;

/**
 * Информация о товаре в документе
 *
 * @param id       GUID товара
 * @param article  артикул товара
 * @param title    наименование товара
 * @param quantity количество шт. товара (хочется поставить int, но указан был double, поэтому оставим так)
 * @param price    цена товара
 */
public record ShipmentItem(String id, String article, String title, double quantity, double price) {
    public double getAmount() {
        return Math.round(quantity * price * 100) / 100.0;
    }
}