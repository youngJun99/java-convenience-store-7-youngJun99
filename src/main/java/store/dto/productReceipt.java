package store.dto;

public record productReceipt(
        String productName,
        int productPrice,
        int purchasedAmount,
        int promoted
) {
}
