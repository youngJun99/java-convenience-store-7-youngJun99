package store.domain;

import store.vo.ItemName;
import store.vo.PurchaseQuantity;

public record Order(
        ItemName itemName,
        PurchaseQuantity purchaseQuantity
) {
}
