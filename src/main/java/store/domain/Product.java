package store.domain;

import store.constants.Errors;
import store.domain.promotion.Promotion;
import store.dto.ProductRequestDto;
import store.vo.Name;
import store.vo.Money;
import store.vo.Quantity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product {

    private final Name productName;
    private final Promotion promotion;
    private final Money productMoney;
    private final Quantity promotionQuantity;
    private final Quantity productQuantity;

    public Product(Name productName, Promotion promotion, Money productMoney, Quantity promotionQuantity, Quantity productQuantity) {
        validateInputs(productName, promotion, productMoney, promotionQuantity, productQuantity);
        this.productName = productName;
        this.promotion = promotion;
        this.productMoney = productMoney;
        this.promotionQuantity = promotionQuantity;
        this.productQuantity = productQuantity;
    }


    public ProductRequestDto checkRequestOf(int requestAmount, LocalDateTime timeofOrder) {
        if(promotion.available(timeofOrder)) {
            return checkPromotionRequest(requestAmount);
        }
       return checkNormalRequest(requestAmount);
    }

    private ProductRequestDto checkPromotionRequest(int requestAmount) {
        return promotion.checkRequest(requestAmount,promotionQuantity,promotionQuantity);
    }

    private ProductRequestDto checkNormalRequest(int requestAmount) {

    }

    public void executeRequestOf(int executeAmount) {

    }

    private void validateRequest(int requestAmount) {
        int TotalAmount = promotionQuantity.getQuantity()+productQuantity.getQuantity();
        if(TotalAmount < requestAmount){
            throw new IllegalArgumentException(Errors.OVER_TOTAL_QUANTITY_REQUEST.getMessage());
        }
    }

    private void validateInputs(Name productName, Promotion promotion, Money productMoney, Quantity promotionQuantity, Quantity productQuantity) {
        validateNull(productName);
        validateNull(promotion);
        validateNull(productMoney);
        validateNull(promotionQuantity);
        validateNull(productQuantity);
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
