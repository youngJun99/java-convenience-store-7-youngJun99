package store.domain.store;

import store.constants.InputErrors;
import store.domain.store.promotion.Promotion;
import store.domain.Purchase;
import store.dto.ProductInventoryDto;
import store.dto.ReceiptDto;

import java.time.LocalDate;
import java.util.Objects;

import static store.domain.Purchase.normalPurchaseFrom;

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
            return checkPromotionRequest(requestAmount);
        }
        return normalPurchaseFrom(productName, requestAmount);
    }

    private Purchase checkPromotionRequest(int requestAmount) {
        return promotion.checkRequest(productName, requestAmount, promotionInventory);
    }

    public ReceiptDto executePurchase(Purchase purchase, LocalDate orderTime) {
        int promotableAmount = purchase.getPromotableAmount();
        int unPromotableAmount = purchase.getUnPromotableAmount();
        int totalAmount = promotableAmount + unPromotableAmount;

        isNotOverInventory(totalAmount);
        processInventory(totalAmount, orderTime);

        int promotionBonus = promotion.calculateBonusToGive(promotableAmount);

        return new ReceiptDto(productName, price, totalAmount, promotionBonus);
    }

    private void processInventory(int totalAmount, LocalDate orderTime) {
        if (promotion.available(orderTime)) {
            processUnderPromotion(totalAmount);
        }
        processUnderNoPromotion(totalAmount);
    }

    private void processUnderNoPromotion(int totalAmount) {
        if (totalAmount > normalInventory) {
            totalAmount -= normalInventory;
            normalInventory = 0;
            promotionInventory -= totalAmount;
        }
        normalInventory -= totalAmount;
    }

    private void processUnderPromotion(int totalAmount) {
        if (totalAmount > promotionInventory) {
            totalAmount -= promotionInventory;
            promotionInventory = 0;
            normalInventory -= totalAmount;
        }
        promotionInventory -= totalAmount;
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
