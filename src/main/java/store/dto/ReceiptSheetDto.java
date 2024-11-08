package store.dto;

import store.domain.MemberShip;

import java.util.List;

public record ReceiptSheetDto(
        MemberShip memberShip,
        List<ReceiptDto> receiptDtos
) {
}
