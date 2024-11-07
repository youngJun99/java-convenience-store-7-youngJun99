package store.domain.store;

import store.constants.InputErrors;
import store.domain.store.promotion.Promotion;
import store.dto.CheckSummaryDto;
import store.dto.ProductReceipt;

import java.time.LocalDateTime;

import static store.dto.CheckSummaryDto.normalSummaryFrom;
import static store.dto.ProductReceipt.*;


public class Product {

    private final String productName;
    private final Promotion promotion;
    private final int price;
    private int promotionInventory;
    private int normalInventory;

    public Product(String productName, Promotion promotion, int price, int promotionInventory, int normalInventory) {
        this.productName = productName;
        this.promotion = promotion;
        this.price = price;
        this.promotionInventory = promotionInventory;
        this.normalInventory = normalInventory;
    }

    public CheckSummaryDto checkRequestOf(int requestAmount, LocalDateTime orderedTime) {
        validateRequest(requestAmount);
        if (promotion.available(orderedTime)) {
            return checkPromotionRequest(requestAmount);
        }
        return normalSummaryFrom(productName);
    }

    private CheckSummaryDto checkPromotionRequest(int requestAmount) {
        return promotion.checkRequest(productName,requestAmount,promotionInventory);
    }

    public ProductReceipt order(int requestAmount, LocalDateTime orderedTime) {
        // 항상 check가 된 주문이 들어오지만, public으로 열려 있어 검증 추가
        validateRequest(requestAmount);
        if (promotion.available(orderedTime)) {
            //구현 해야함
            return
        }
        processNormalOrder(requestAmount);
        return normalReceiptFrom(productName,price,requestAmount);
    }

    private void processNormalOrder(int requestAmount) {
        if(normalInventory < requestAmount) {
            requestAmount -= normalInventory;
            normalInventory = 0;
            promotionInventory -= requestAmount;
        }
        normalInventory -= requestAmount;
    }

    private void validateRequest(int requestAmount) {
        int TotalAmount = promotionInventory + normalInventory;
        if (TotalAmount < requestAmount) {
            throw new IllegalArgumentException(InputErrors.INVENTORY_SHORTAGE.getMessage());
        }
    }

}
