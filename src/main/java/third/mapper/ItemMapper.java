package third.mapper;

import third.entity.Item;
import third.entity.ResultItem;

public class ItemMapper {
    public static ResultItem map(Item item) {
        return new ResultItem(
                item.article(),
                item.name(),
                item.provider().getPrice(item.manufacturerPrice()),
                item.provider().getName(),
                item.provider().getAddress(),
                item.provider().getRealManufacturer().getName()
        );
    }
}
