package store.dto;

public record OrderConfirmRequestDto(
        String productName,
        int unPromotableAmount,
        int extraReceivableBonus
){
}
