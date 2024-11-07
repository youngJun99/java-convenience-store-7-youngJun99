package store.domain.store;

import store.constants.Errors;
import store.domain.store.promotion.Promotion;
import store.dto.ProductRequestDto;
import store.vo.ItemName;
import store.vo.purchaseQuantity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product {

    private final ItemName productItemName;
    private final Promotion promotion;
    private final Money productMoney;
    private final purchaseQuantity promotionPurchaseQuantity;
    private final purchaseQuantity productPurchaseQuantity;

    public Product(ItemName productItemName, Promotion promotion, Money productMoney, purchaseQuantity promotionPurchaseQuantity, purchaseQuantity productPurchaseQuantity) {
        validateInputs(productItemName, promotion, productMoney, promotionPurchaseQuantity, productPurchaseQuantity);
        this.productItemName = productItemName;
        this.promotion = promotion;
        this.productMoney = productMoney;
        this.promotionPurchaseQuantity = promotionPurchaseQuantity;
        this.productPurchaseQuantity = productPurchaseQuantity;
    }


    public ProductRequestDto checkRequestOf(int requestAmount, LocalDateTime timeofOrder) {
        if(promotion.available(timeofOrder)) {
            return checkPromotionRequest(requestAmount);
        }
       return checkNormalRequest(requestAmount);
    }

    private ProductRequestDto checkPromotionRequest(int requestAmount) {
        return promotion.checkRequest(requestAmount, promotionPurchaseQuantity, promotionPurchaseQuantity);
    }

    private ProductRequestDto checkNormalRequest(int requestAmount) {
        if(requestAmount <= productPurchaseQuantity.getQuantity()){
            return
        }

    }

    public void executeRequestOf(int executeAmount) {

    }

    private void validateRequest(int requestAmount) {
        int TotalAmount = promotionPurchaseQuantity.getQuantity()+ productPurchaseQuantity.getQuantity();
        if(TotalAmount < requestAmount){
            throw new IllegalArgumentException(Errors.OVER_TOTAL_QUANTITY_REQUEST.getMessage());
        }
    }

    private void validateInputs(ItemName productItemName, Promotion promotion, Money productMoney, purchaseQuantity promotionPurchaseQuantity, purchaseQuantity productPurchaseQuantity) {
        validateNull(productItemName);
        validateNull(promotion);
        validateNull(productMoney);
        validateNull(promotionPurchaseQuantity);
        validateNull(productPurchaseQuantity);
        validateZeroPrice(productMoney.getPrice());
    }

    private void validateZeroPrice(int price) {
        if(price == 0) {
            throw new IllegalArgumentException(Errors.ZERO_PRICE.getMessage());
        }
    }

    private void validateNull(Object object) {
        if(Objects.isNull(object)){
            throw new IllegalArgumentException(Errors.NULL_IN_PRODUCT_CREATION.getMessage());
        }
    }

}
