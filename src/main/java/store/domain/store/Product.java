package store.domain.store;

import store.constants.InputErrors;
import store.domain.promotion.Promotion;
import store.domain.shoppingcart.Purchase;
import store.dto.ProductInventoryDto;
import store.dto.ReceiptDto;

import java.time.LocalDate;
import java.util.Objects;

import static store.domain.shoppingcart.Purchase.unPromotablePurchaseFrom;

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

    public String getProductName() {
        return productName;
    }

    public ProductInventoryDto showInventory() {
        return new ProductInventoryDto(productName, price, promotionInventory, normalInventory, promotion.getName());
    }

    public Purchase makePendingPurchase(int requestAmount, LocalDate orderedTime) {
        isNotOverInventory(requestAmount);
        if (promotion.available(orderedTime)) {
            return promotion.makePendingPromotablePurchase(productName, requestAmount, promotionInventory);
        }
        return unPromotablePurchaseFrom(productName, requestAmount);
    }

    public ReceiptDto executePurchase(Purchase purchase, LocalDate orderTime) {
        int promotableAmount = purchase.getPromotableAmount();
        int unPromotableAmount = purchase.getUnPromotableAmount();
        int totalAmount = promotableAmount + unPromotableAmount;

        processInventory(totalAmount, orderTime);

        int promotionBonus = promotion.calculateBonusToGive(promotableAmount);

        return new ReceiptDto(productName, price, totalAmount, promotionBonus);
    }

    //내부 필드에 접근하는 로직이기 때문에 early return을 만드는 것보다 한번의 if-else가 더 가독성이 좋다고 판단했습니다.
    private void processInventory(int totalAmount, LocalDate orderTime) {
        if (promotion.available(orderTime)) {
            processUnderPromotion(totalAmount);
        } else {
            processUnderNoPromotion(totalAmount);
        }
    }

    private void processUnderNoPromotion(int totalAmount) {
        int remainingAmount = Math.max(totalAmount - normalInventory, 0);
        normalInventory = Math.max(normalInventory - totalAmount, 0);
        promotionInventory -= remainingAmount;
    }

    private void processUnderPromotion(int totalAmount) {
        int remainingAmount = Math.max(totalAmount - promotionInventory, 0);
        promotionInventory = Math.max(promotionInventory - totalAmount, 0);
        normalInventory -= remainingAmount;
    }

    private void isNotOverInventory(int requestAmount) {
        int TotalAmount = promotionInventory + normalInventory;
        if (TotalAmount < requestAmount) {
            throw new IllegalArgumentException(InputErrors.INVENTORY_SHORTAGE.getMessage());
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Product product = (Product) object;
        return price == product.price && promotionInventory == product.promotionInventory && normalInventory == product.normalInventory && Objects.equals(productName, product.productName) && Objects.equals(promotion, product.promotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, promotion, price, promotionInventory, normalInventory);
    }
}
