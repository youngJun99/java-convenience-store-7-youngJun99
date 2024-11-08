package store.dto;

public record ReceiptDto(
        String productName,
        int price,
        int buyAmount,
        int promotionBonus
) {
}
