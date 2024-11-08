package store.dto;

public record OrderApproveRequestDto(
        String productName,
        int unPromotableAmount,
        int extraReceivableBonus
){
}
