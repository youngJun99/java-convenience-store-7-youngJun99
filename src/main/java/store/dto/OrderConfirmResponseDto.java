package store.dto;

public record OrderConfirmResponseDto(
        String productName,
        boolean proceedPurchase
) {
}
