package third.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Производитель
 *
 * @see Provider
 */
public class Manufacturer extends Provider {
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public Manufacturer(String inn, String name, String address) {
        super(inn, name, address);
    }

    /**
     * Получение цены (производитель продает без наценки)
     *
     * @param manufacturerPrice цена производителя
     * @return цена без наценки
     */
    @Override
    public BigDecimal getPrice(BigDecimal manufacturerPrice) {
        if (manufacturerPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Цена должна быть больше нуля");
        }
        return manufacturerPrice.setScale(2, ROUNDING_MODE);
    }

    /**
     * Получение производителя
     *
     * @return производитель
     */
    @Override
    public Manufacturer getRealManufacturer() {
        return this;
    }
}
