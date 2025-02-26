package store.dto;

import java.util.Objects;

public record ProductReceiptDto(
        String productName,
        int price,
        int buyAmount,
        int promotionBonus
) {
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProductReceiptDto that = (ProductReceiptDto) object;
        return price == that.price && buyAmount == that.buyAmount && promotionBonus == that.promotionBonus && Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, price, buyAmount, promotionBonus);
    }
}
