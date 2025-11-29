package third.entity;

import java.math.BigDecimal;

public record ResultItem(
        String article,
        String name,
        BigDecimal price,
        String providerName,
        String providerAddress,
        String manufacturerName
) {
}
