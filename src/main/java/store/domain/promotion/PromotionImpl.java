package store.domain.promotion;

import store.domain.shoppingcart.Purchase;

import java.time.LocalDate;
import java.util.Objects;

import static store.domain.shoppingcart.Purchase.*;

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
    public Purchase makePendingPromotablePurchase(String productName, int requestAmount, int promotionInventory) {

        int divider = buy + get;

        int promotableAmount = divider * (promotionInventory / divider);

        //requestAmount - promotableAmount 만큼 프로모션 혜택을 받지 못합니다.
        if (promotableAmount < requestAmount) {
            return partialPromotablePurchaseFrom(productName, promotableAmount, requestAmount - promotableAmount);
        }
        //Full Promotion
        if (promotableAmount == requestAmount) {
            return fullPromotablePurchaseFrom(productName, requestAmount);
        }
        // 프로모션 가능한 재고보다 적은 만큼의 요청이 들어왔다면 보너스를 지급할 가능성이 있습니다.
        return checkBonusGivable(productName, requestAmount, promotableAmount, divider);
    }

    @Override
    public int calculateBonusToGive(int buyAmount) {
        return buyAmount / (buy + get) * get;
    }

    private Purchase checkBonusGivable(String productName, int requestAmount, int promotableAmount, int divider) {

        int leftOvers = requestAmount % divider;

        if (leftOvers == 0) {
            return fullPromotablePurchaseFrom(productName, requestAmount);
        }
        if (leftOvers == buy && requestAmount + get <= promotableAmount) {
            return bonusReceivablePurchaseFrom(productName, requestAmount, get);
        }
        //leftOver 만큼 프로모션 불가
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
