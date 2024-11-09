package store.dto;

import java.util.Objects;

public record OrderApproveRequestDto(
        String productName,
        int unPromotableAmount,
        int extraReceivableBonus
){
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OrderApproveRequestDto that = (OrderApproveRequestDto) object;
        return unPromotableAmount == that.unPromotableAmount && extraReceivableBonus == that.extraReceivableBonus && Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, unPromotableAmount, extraReceivableBonus);
    }
}
