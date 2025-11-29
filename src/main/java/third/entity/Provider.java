package third.entity;

import java.math.BigDecimal;

/**
 * Поставщик (либо производитель, либо дилер)
 *
 * @see Manufacturer
 * @see Dealer
 */
public abstract class Provider {
    private final String inn;
    private final String name;
    private final String address;

    public Provider(String inn, String name, String address) {
        if (inn.length() != 10 && inn.length() != 12) {
            throw new IllegalArgumentException("ИНН должен быть длиной 10 или 12 символов");
        }
        if (!inn.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("ИНН должен состоять только из цифр");
        }
        this.inn = inn;
        this.name = name;
        this.address = address;
    }

    /**
     * Получение итоговой цены с учетом наценок и т.п.
     *
     * @param manufacturerPrice цена производителя
     * @return итоговая цена с учетом наценок и т.п.
     */
    public abstract BigDecimal getPrice(BigDecimal manufacturerPrice);

    /**
     * Получение производителя
     *
     * @return производитель
     */
    public abstract Manufacturer getRealManufacturer();

    public String getInn() {
        return inn;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
