package store.dto;

import store.domain.MemberShip;

import java.time.LocalDateTime;
import java.util.List;

public record OrderSheetDto(
        List<OrderDto> orderDtos,
        MemberShip memberShip,
        LocalDateTime orderedTime
) {
}
