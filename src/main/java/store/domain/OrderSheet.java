package store.domain;

import java.util.List;

public record OrderSheet(
        List<Order> orders,
        MemberShip memberShip
) {
}
