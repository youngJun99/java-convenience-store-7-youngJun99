package store.domain;

import store.constants.Errors;
import store.domain.promotion.Promotion;
import store.dto.ProductRequestDto;
import store.vo.Name;
import store.vo.Money;
import store.vo.Quantity;

import java.util.Objects;

public class Product {

    private final Name productName;
    private final Promotion promotion;
    private final Money productMoney;
    private final Quantity promotionQuantity;
    private final Quantity productQuantity;

    public Product(Name productName, Promotion promotion, Money productMoney, Quantity promotionQuantity, Quantity productQuantity) {
        validateNull(productName);
        validateNull(promotion);
        validateNull(productMoney);
        validateNull(promotionQuantity);
        validateNull(productQuantity);
        validateZeroPrice(productMoney.getPrice());
        this.productName = productName;
        this.promotion = promotion;
        this.productMoney = productMoney;
        this.promotionQuantity = promotionQuantity;
        this.productQuantity = productQuantity;
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
