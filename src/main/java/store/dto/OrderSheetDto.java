package store.dto;

import store.domain.MemberShip;

import java.time.LocalDate;
import java.util.List;

public record OrderSheetDto(
        List<OrderDto> orderDtos,
        LocalDate orderedTime
) {
}
