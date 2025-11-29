package third.entity;

import java.math.BigDecimal;

/**
 * Товар
 */
public record Item(
        String article,
        String name,
        BigDecimal manufacturerPrice,
        Provider provider
) {
    public Item {
        if (article.length() < 10 || article.length() > 15) {
            throw new IllegalArgumentException("Артикул должен быть длиной от 10 до 15 символов");
        }
        if (!article.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("Артикул должен состоять только из цифр");
        }
        if (manufacturerPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Цена должна быть больше нуля");
        }
    }
}
