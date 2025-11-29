package third.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import third.entity.Dealer;
import third.entity.Item;
import third.entity.Manufacturer;
import third.entity.ResultItem;
import third.mapper.ItemMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {
    private static Manufacturer manufacturer() {
        return new Manufacturer("1234567890", "Prod", "Addr");
    }

    private static Dealer dealer(BigDecimal markupPercent) {
        return new Dealer(manufacturer(), markupPercent, "0987654321", "Dealer", "DealerAddr");
    }

    private static Item item(String article, String name, String manufacturerPrice, Manufacturer provider) {
        return new Item(article, name, new BigDecimal(manufacturerPrice), provider);
    }

    private static Item item(String article, String name, String manufacturerPrice, Dealer provider) {
        return new Item(article, name, new BigDecimal(manufacturerPrice), provider);
    }

    @Test
    @DisplayName("constructor — дублирующиеся артикулы приводят к исключению с артикулом в сообщении")
    void constructorThrowsOnDuplicateArticle() {
        Item a = item("0000000001", "One", "10.00", manufacturer());
        Item b = item("0000000001", "Two", "20.00", manufacturer());
        var ex = assertThrows(IllegalArgumentException.class, () -> new ItemService(List.of(a, b)));
        assertTrue(ex.getMessage().contains("Найден дублирующийся артикул"));
        assertTrue(ex.getMessage().contains("0000000001"));
    }

    @Test
    @DisplayName("findItemByArticle — находит товар по точному артикулу и возвращает замапленный ResultItem")
    void findItemByArticleFound() {
        Item it = item("0000000002", "Widget", "100.00", dealer(new BigDecimal("33.33")));
        ItemService svc = new ItemService(List.of(it));
        var opt = svc.findItemByArticle("0000000002");
        assertTrue(opt.isPresent());
        ResultItem expected = ItemMapper.map(it);
        ResultItem actual = opt.get();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("findItemByArticle — отсутствующий артикул возвращает пустой Optional")
    void findItemByArticleNotFound() {
        Item it = item("0000000003", "Gadget", "10.00", manufacturer());
        ItemService svc = new ItemService(List.of(it));
        assertFalse(svc.findItemByArticle("9999999999").isPresent());
    }

    @Test
    @DisplayName("findItemsByName — частичный поиск по имени (регистронезависимый)")
    void findItemsByNameCaseInsensitive() {
        Item it1 = item("0000000010", "Red Apple", "5.00", manufacturer());
        Item it2 = item("0000000011", "Green apple", "6.00", manufacturer());
        Item it3 = item("0000000012", "Banana", "3.00", manufacturer());
        ItemService svc = new ItemService(List.of(it1, it2, it3));
        List<ResultItem> results = svc.findItemsByName("apple");
        assertEquals(2, results.size());
        Set<ResultItem> expected = Set.of(ItemMapper.map(it1), ItemMapper.map(it2));
        assertEquals(expected, Set.copyOf(results));
    }

    @ParameterizedTest
    @DisplayName("findItemsByName — разные запросы возвращают ожидаемое количество совпадений")
    @CsvSource({
            "app, 2",
            "Red, 1",
            "green, 1",
            "z, 0",
            "BANANA, 1"
    })
    void findItemsByNameCounts(String query, int expectedCount) {
        Item it1 = item("0000000020", "Red Apple", "5.00", manufacturer());
        Item it2 = item("0000000021", "Green apple", "6.00", manufacturer());
        Item it3 = item("0000000022", "Banana", "3.00", manufacturer());
        ItemService svc = new ItemService(List.of(it1, it2, it3));
        List<ResultItem> results = svc.findItemsByName(query);
        assertEquals(expectedCount, results.size());
    }

    @Test
    @DisplayName("findItemsByName — если нет совпадений, возвращается пустой список")
    void findItemsByNameNoMatches() {
        Item it1 = item("0000000030", "Chair", "10.00", manufacturer());
        Item it2 = item("0000000031", "Table", "20.00", manufacturer());
        ItemService svc = new ItemService(List.of(it1, it2));
        List<ResultItem> results = svc.findItemsByName("sofa");
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("findItemsByName — частичные совпадения внутри строки (слово в середине)")
    void findItemsByNameSubstringInMiddle() {
        Item it1 = item("0000000040", "SuperWidgetPro", "15.00", manufacturer());
        Item it2 = item("0000000041", "WidgetMini", "8.00", manufacturer());
        ItemService svc = new ItemService(List.of(it1, it2));
        List<ResultItem> results = svc.findItemsByName("widget");
        assertEquals(2, results.size());
        Set<ResultItem> expected = Set.of(ItemMapper.map(it1), ItemMapper.map(it2));
        assertEquals(expected, Set.copyOf(results));
    }

    @Test
    @DisplayName("constructor — разные товары с уникальными артикулами создаются успешно")
    void constructorAcceptsUniqueArticles() {
        Item a = item("0000000100", "A", "1.00", manufacturer());
        Item b = item("0000000101", "B", "2.00", manufacturer());
        Item c = item("0000000102", "C", "3.00", dealer(new BigDecimal("10.00")));
        ItemService svc = new ItemService(List.of(a, b, c));
        assertEquals(ItemMapper.map(a), svc.findItemByArticle(a.article()).orElseThrow());
        assertEquals(ItemMapper.map(b), svc.findItemByArticle(b.article()).orElseThrow());
        assertEquals(ItemMapper.map(c), svc.findItemByArticle(c.article()).orElseThrow());
    }
}