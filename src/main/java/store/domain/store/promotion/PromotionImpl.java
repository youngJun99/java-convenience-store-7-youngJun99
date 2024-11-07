package store.domain.store.promotion;

import store.dto.CheckSummaryDto;

import java.time.LocalDateTime;

import static store.dto.CheckSummaryDto.*;

public class PromotionImpl implements Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public PromotionImpl(String name, int buy, int get, LocalDateTime start, LocalDateTime end) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean available(LocalDateTime orderedTime) {
        return orderedTime.isAfter(start) && orderedTime.isBefore(end);
    }

    @Override
    public CheckSummaryDto checkRequest(String productName, int requestAmount, int promotionInventory) {

        int divider = buy + get;

        int promotableAmount = divider * (promotionInventory / divider);

        //requestAmount - promotableAmount 만큼 못준다
        if (promotableAmount < requestAmount) {
            return unPromotableFrom(productName, requestAmount - promotableAmount);
        }
        //프로모션을 문제없이 진행할 수 있다
        if (promotableAmount == requestAmount) {
            return normalSummaryFrom(productName);
        }
        return checkRestCases(productName, requestAmount, divider, promotableAmount);
    }

    private CheckSummaryDto checkRestCases(String productName, int requestAmount, int divider, int promotableAmount) {
        int leftOvers = requestAmount % divider;
        //보너스를 줄 수 있다
        if (leftOvers == buy && requestAmount + divider <= promotableAmount) {
            return bonusReceivableFrom(productName, get);
        }
        //leftOver 만큼 줄 수 없다
        return unPromotableFrom(productName, leftOvers);

    }
}
