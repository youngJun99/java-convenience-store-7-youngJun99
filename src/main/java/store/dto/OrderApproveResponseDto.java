package store.dto;

import java.util.Objects;

public record OrderApproveResponseDto(
        String productName,
        boolean proceedPurchase
) {
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OrderApproveResponseDto that = (OrderApproveResponseDto) object;
        return proceedPurchase == that.proceedPurchase && Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, proceedPurchase);
    }
}
