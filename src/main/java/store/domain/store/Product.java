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

    //return 쏴주는거랑 재고 관리하는거랑 분리해야 겠어 그리고 calculatePromotableAmount 저거 이름도 좀 만지고 만들긴해야해.
    public ProductReceipt order(int requestAmount, LocalDateTime orderedTime) {
        if (promotion.available(orderedTime)) {
            int promotedAmount = promotion.calculatePromoted(requestAmount,promotionInventory);
            int promotableAmount = promotion.calculatePromotableAmount(requestAmount,promotionInventory);
            if(requestAmount > promotableAmount+normalInventory) {
                requestAmount -= normalInventory;
                normalInventory =0;
                promotionInventory -= requestAmount;
            }
            if(requestAmount > promotableAmount) {
                requestAmount -= promotableAmount;
                promotionInventory-= promotableAmount;
                normalInventory -= requestAmount;
            }
            promotionInventory-= requestAmount;

            return new ProductReceipt(productName,price,requestAmount,)
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
