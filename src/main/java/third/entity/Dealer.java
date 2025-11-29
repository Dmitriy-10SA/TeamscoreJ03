package third.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Дилер
 *
 * @see Provider
 */
public class Dealer extends Provider {
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private final Manufacturer manufacturer;
    private final BigDecimal markupPercent; // Наценка в процентах

    // Наценка, на которую мы умножаем цену производителя, чтоб не считать каждый раз в методе
    //сразу считаем в конструкторе и используем
    private final BigDecimal readyForMultiplyMarkup;

    public Dealer(Manufacturer manufacturer, BigDecimal markupPercent, String inn, String name, String address) {
        super(inn, name, address);
        if (markupPercent.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Наценка должна быть больше нуля");
        }
        this.manufacturer = manufacturer;
        this.markupPercent = markupPercent.setScale(2, ROUNDING_MODE);
        this.readyForMultiplyMarkup = BigDecimal.ONE
                .add(this.markupPercent.divide(BigDecimal.valueOf(100), 10, ROUNDING_MODE));
    }

    /**
     * Получение цены с учетом наценки
     *
     * @param manufacturerPrice цена производителя
     * @return цена с учетом наценки
     */
    @Override
    public BigDecimal getPrice(BigDecimal manufacturerPrice) {
        if (manufacturerPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Цена должна быть больше нуля");
        }
        return manufacturerPrice
                .setScale(2, ROUNDING_MODE)
                .multiply(readyForMultiplyMarkup)
                .setScale(2, ROUNDING_MODE);
    }

    /**
     * Получение производителя
     *
     * @return производитель
     */
    @Override
    public Manufacturer getRealManufacturer() {
        return manufacturer;
    }

    public BigDecimal getMarkupPercent() {
        return markupPercent;
    }
}
