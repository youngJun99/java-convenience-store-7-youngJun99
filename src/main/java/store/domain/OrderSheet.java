package store.domain;

import java.time.LocalDateTime;
import java.util.List;

public record OrderSheet(
        List<Order> orders,
        MemberShip memberShip,
        LocalDateTime orderedTime
) {
}
