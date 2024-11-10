package store.domain.store.promotion;

import store.domain.Purchase;

import java.time.LocalDate;
import java.util.Objects;

import static store.domain.Purchase.*;

public class PromotionImpl implements Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate start;
    private final LocalDate end;

    public PromotionImpl(String name, int buy, int get, LocalDate start, LocalDate end) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean available(LocalDate orderedTime) {
        return orderedTime.isAfter(start) && orderedTime.isBefore(end);
    }

    @Override
    public Purchase makePendingPromotionPurchase(String productName, int requestAmount, int promotionInventory) {

        int divider = buy + get;

        int promotableAmount = divider * (promotionInventory / divider);

        //requestAmount - promotableAmount 만큼 못준다
        if (promotableAmount < requestAmount) {
            return partialPromotablePurchaseFrom(productName, promotableAmount, requestAmount - promotableAmount);
        }
        //프로모션을 문제없이 진행할 수 있다
        if (promotableAmount == requestAmount) {
            return fullPromotablePurchaseFrom(productName, requestAmount);
        }
        return checkBonusGivable(productName, requestAmount, promotableAmount, divider);
    }

    @Override
    public int calculateBonusToGive(int buyAmount) {
        return buyAmount / (buy + get) * get;
    }


    private Purchase checkBonusGivable(String productName, int requestAmount, int promotableAmount, int divider) {

        int leftOvers = requestAmount % divider;

        //프로모션을 문제없이 진행할 수 있다
        if (leftOvers == 0) {
            return fullPromotablePurchaseFrom(productName, requestAmount);
        }
        //보너스를 줄 수 있다
        if (leftOvers == buy && requestAmount + get <= promotableAmount) {
            return bonusReceivablePurchaseFrom(productName, requestAmount, get);
        }
        //leftOver 만큼 줄 수 없다
        return partialPromotablePurchaseFrom(productName, requestAmount - leftOvers, leftOvers);

    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PromotionImpl promotion = (PromotionImpl) object;
        return buy == promotion.buy && get == promotion.get && Objects.equals(name, promotion.name) && Objects.equals(start, promotion.start) && Objects.equals(end, promotion.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buy, get, start, end);
    }
}
