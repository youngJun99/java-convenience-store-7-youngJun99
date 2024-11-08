package store.dto;

public record OrderApproveResponseDto(
        String productName,
        boolean proceedPurchase
) {
}
