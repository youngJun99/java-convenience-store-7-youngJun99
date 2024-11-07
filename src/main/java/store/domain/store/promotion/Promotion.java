package store.domain.store.promotion;

import store.dto.CheckSummaryDto;

import java.time.LocalDateTime;

public interface Promotion {

    boolean available(LocalDateTime orderedTime);

    CheckSummaryDto checkRequest(int requestAmount, int promotionInventory, int normalInventory);
}
