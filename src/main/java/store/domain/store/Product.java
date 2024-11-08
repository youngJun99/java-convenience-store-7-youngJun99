package store.domain.store;

import store.constants.InputErrors;
import store.domain.store.promotion.Promotion;
import store.dto.CheckSummaryDto;
import store.dto.ProductReceipt;

import java.time.LocalDateTime;

import static store.dto.CheckSummaryDto.normalSummaryFrom;

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


    public ProductReceipt order(int normalInventoryRequest, int promotionInventoryRequest) {
        this.normalInventory -= normalInventoryRequest;
        this.promotionInventory -= promotionInventoryRequest;
        int purchaseAmount = normalInventoryRequest+promotionInventoryRequest;
        return new ProductReceipt(productName,price,purchaseAmount,promotionInventoryRequest);
    }

    private void validateRequest(int requestAmount) {
        int TotalAmount = promotionInventory + normalInventory;
        if (TotalAmount < requestAmount) {
            throw new IllegalArgumentException(InputErrors.INVENTORY_SHORTAGE.getMessage());
        }
    }

}
