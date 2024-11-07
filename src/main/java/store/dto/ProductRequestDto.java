package store.dto;

public record ProductRequestDto(
        String productName,
        int approvedAmount,
        int unapprovedAmount,
        int promotedAmount,
        int promotionBonus
) {

}
