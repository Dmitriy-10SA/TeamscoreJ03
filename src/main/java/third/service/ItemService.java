package third.service;

import third.entity.Item;
import third.entity.ResultItem;
import third.mapper.ItemMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ItemService {
    private final Map<String, Item> items;

    public ItemService(List<Item> items) {
        this.items = new HashMap<>();
        for (Item item : items) {
            //артикул должен быть уникален для каждого товара
            if (this.items.containsKey(item.article())) {
                throw new IllegalArgumentException("Найден дублирующийся артикул: " + item.article());
            }
            this.items.put(item.article(), item);
        }
    }

    /**
     * Поиск товара по артикулу (полное совпадение)
     *
     * @param article артикул
     * @return товар (может отсутствовать)
     */
    public Optional<ResultItem> findItemByArticle(String article) {
        return Optional.ofNullable(items.get(article)).map(ItemMapper::map);
    }

    /**
     * Поиск товара по имени (частичное совпадение)
     *
     * @param name имя
     * @return список товаров, которые подошли под данное имя
     */
    public List<ResultItem> findItemsByName(String name) {
        String lowerCaseName = name.toLowerCase();
        return items.values().stream()
                .filter(item -> item.name().toLowerCase().contains(lowerCaseName))
                .map(ItemMapper::map)
                .toList();
    }
}