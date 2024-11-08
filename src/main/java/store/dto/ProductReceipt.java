package store.dto;

public record ProductReceipt(
        String productName,
        int productPrice,
        int purchasedAmount,
        int promotionalBonus
) {
}
