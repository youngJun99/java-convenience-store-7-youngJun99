package store.dto;

import store.vo.ItemName;
import store.vo.PurchaseQuantity;

public record OrderDto(
        ItemName itemName,
        PurchaseQuantity purchaseQuantity
) {
}
