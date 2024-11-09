package store.dto;

public record ProductInventoryDto(
        String productName,
        int price,
        int promotionInventory,
        int normalInventory,
        String promotionName
) {
}
