package third.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import third.entity.Dealer;
import third.entity.Item;
import third.entity.Manufacturer;
import third.entity.ResultItem;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemMapperTest {
    @Test
    @DisplayName("map корректно преобразует Item с Manufacturer в ResultItem")
    void mapConvertsItemWithManufacturerToResultItem() {
        Manufacturer manufacturer = new Manufacturer("1234567890", "Производитель ООО", "Москва, ул. Тестовая, 1");
        Item item = new Item("1234567890", "Товар А", new BigDecimal("100.50"), manufacturer);
        ResultItem result = ItemMapper.map(item);
        assertEquals("1234567890", result.article());
        assertEquals("Товар А", result.name());
        assertEquals(new BigDecimal("100.50"), result.price());
        assertEquals("Производитель ООО", result.providerName());
        assertEquals("Москва, ул. Тестовая, 1", result.providerAddress());
        assertEquals("Производитель ООО", result.manufacturerName());
    }

    @Test
    @DisplayName("map корректно преобразует Item с Dealer в ResultItem")
    void mapConvertsItemWithDealerToResultItem() {
        Manufacturer manufacturer = new Manufacturer("1234567890", "Производитель ООО", "Москва, ул. Тестовая, 1");
        Dealer dealer = new Dealer(manufacturer, new BigDecimal("15.5"), "0987654321", "Дилер ООО", "СПб, ул. Торговая, 5");
        Item item = new Item("9876543210", "Товар Б", new BigDecimal("200.00"), dealer);
        ResultItem result = ItemMapper.map(item);
        assertEquals("9876543210", result.article());
        assertEquals("Товар Б", result.name());
        assertEquals(new BigDecimal("231.00"), result.price());
        assertEquals("Дилер ООО", result.providerName());
        assertEquals("СПб, ул. Торговая, 5", result.providerAddress());
        assertEquals("Производитель ООО", result.manufacturerName());
    }

    @Test
    @DisplayName("map корректно обрабатывает нулевую цену производителя")
    void mapHandlesZeroManufacturerPrice() {
        Manufacturer manufacturer = new Manufacturer(
                "1234567890",
                "Производитель ООО",
                "Москва, ул. Тестовая, 1"
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Item("1234567890", "Бесплатный товар", BigDecimal.ZERO, manufacturer)
        );
    }

    @Test
    @DisplayName("map использует правильный расчет цены через provider.getPrice()")
    void mapUsesProviderPriceCalculation() {
        Manufacturer manufacturer = new Manufacturer("1234567890", "Производитель", "Адрес");
        Dealer dealer = new Dealer(
                manufacturer,
                new BigDecimal("10"),
                "0987654321",
                "Дилер",
                "Адрес"
        );
        Item item = new Item("1234567890", "Товар", new BigDecimal("100"), dealer);
        ResultItem result = ItemMapper.map(item);
        assertEquals(new BigDecimal("110.00"), result.price());
    }

    @Test
    @DisplayName("map возвращает имя производителя через getRealManufacturer().getName()")
    void mapReturnsManufacturerNameThroughGetRealManufacturer() {
        Manufacturer manufacturer = new Manufacturer("1234567890", "Реальный производитель", "Адрес");
        Dealer dealer = new Dealer(manufacturer, new BigDecimal("20"), "0987654321", "Дилер", "Адрес");
        Item item = new Item("1234567890", "Товар", new BigDecimal("50"), dealer);
        ResultItem result = ItemMapper.map(item);
        assertEquals("Реальный производитель", result.manufacturerName());
        assertEquals("Дилер", result.providerName());
    }

    @Test
    @DisplayName("map корректно обрабатывает округление цены")
    void mapHandlesPriceRounding() {
        Manufacturer manufacturer = new Manufacturer("1234567890", "Производитель", "Адрес");
        Dealer dealer = new Dealer(manufacturer, new BigDecimal("33.33"), "0987654321", "Дилер", "Адрес");
        Item item = new Item("1234567890", "Товар", new BigDecimal("100"), dealer);
        ResultItem result = ItemMapper.map(item);
        assertEquals(new BigDecimal("133.33"), result.price());
    }
}
