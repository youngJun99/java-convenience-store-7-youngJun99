package store.domain.store.promotion;

import store.dto.CheckSummaryDto;

import java.time.LocalDateTime;

public interface Promotion {

    boolean available(LocalDateTime orderedTime);

    CheckSummaryDto checkRequest(String productName,int requestAmount, int promotionInventory);

    public int calculatePromoted(int requestAmount, int promotionInventory);
}
