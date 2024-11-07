package store.domain;

import store.domain.promotion.Promotion;
import store.dto.ProductRequestDto;
import store.vo.Name;
import store.vo.Money;
import store.vo.Quantity;

public class Product {

    private final Name productName;
    private final Promotion promotion;
    private final Money productMoney;
    private final Quantity promotionQuantity;
    private final Quantity productQuantity;

    public Product(Name productName, Promotion promotion, Money productMoney, Quantity promotionQuantity, Quantity productQuantity) {
        this.productName = productName;
        this.promotion = promotion;
        this.productMoney = productMoney;
        this.promotionQuantity = promotionQuantity;
        this.productQuantity = productQuantity;
    }

    public ProductRequestDto checkRequestOf(int requestAmount) {
        return promotion.reviewRequest(productQuantity,requestAmount);
    }

    public void executeRequestOf(int executeAmount) {

    }

    private validateZeroPrice(int price) {
        if()
    }
}
