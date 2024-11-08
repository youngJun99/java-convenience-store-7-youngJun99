package store.domain;

import store.vo.ItemName;
import store.vo.PurchaseQuantity;

import java.util.Objects;

//여기 값에 대한 validation을 만드는게 좋은 것 같음. Qunatitiy는 점검할 수 있을듯 이름은 나중에.
public record Order(
        ItemName itemName,
        PurchaseQuantity purchaseQuantity
) {
}
