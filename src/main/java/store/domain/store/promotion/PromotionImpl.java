package store.domain.store.promotion;

import store.domain.Purchase;

import java.time.LocalDateTime;

import static store.domain.Purchase.*;

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
    public Purchase checkRequest(String productName, int requestAmount, int promotionInventory) {

        int divider = buy + get;

        int promotableAmount = divider * (promotionInventory / divider);

        //requestAmount - promotableAmount 만큼 못준다
        if (promotableAmount < requestAmount) {
            return partialPromotablePurchaseFrom(productName, promotableAmount,requestAmount - promotableAmount);
        }
        //프로모션을 문제없이 진행할 수 있다
        if (promotableAmount == requestAmount) {
            return fullPromotablePurchaseFrom(productName,requestAmount);
        }
        return checkRestCases(productName, requestAmount, promotableAmount, divider);
    }


    private Purchase checkRestCases(String productName, int requestAmount, int promotableAmount, int divider) {

        int leftOvers = requestAmount % divider;

        //보너스를 줄 수 있다
        if (leftOvers == buy && requestAmount + divider <= promotableAmount) {
            return bonusReceivablePurchaseFrom(productName,requestAmount,get);
        }
        //leftOver 만큼 줄 수 없다
        return partialPromotablePurchaseFrom(productName, requestAmount, leftOvers);

    }

}
