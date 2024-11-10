package store.dto;

import java.util.Objects;

public record OrderDto(
        String productName,
        int purchaseQuantity
) {
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OrderDto orderDto = (OrderDto) object;
        return purchaseQuantity == orderDto.purchaseQuantity && Objects.equals(productName, orderDto.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, purchaseQuantity);
    }
}
