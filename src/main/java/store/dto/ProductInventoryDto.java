package store.dto;

import java.util.Objects;

public record ProductInventoryDto(
        String productName,
        int price,
        int promotionInventory,
        int normalInventory,
        String promotionName
) {
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProductInventoryDto that = (ProductInventoryDto) object;
        return price == that.price && normalInventory == that.normalInventory && promotionInventory == that.promotionInventory && Objects.equals(productName, that.productName) && Objects.equals(promotionName, that.promotionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, price, promotionInventory, normalInventory, promotionName);
    }
}
