package store.domain.shoppingcart;

import store.dto.OrderApproveRequestDto;
import store.dto.OrderApproveResponseDto;

import java.util.Objects;

public class Purchase {

    private final String productName;
    private Boolean approved;
    private int promotableAmount;
    private int unPromotableAmount;
    private int extraReceivableBonus;

    public Purchase(String productName, Boolean approved, int promotableAmount, int unPromotableAmount, int extraReceivableBonus) {
        this.productName = productName;
        this.approved = approved;
        this.promotableAmount = promotableAmount;
        this.unPromotableAmount = unPromotableAmount;
        this.extraReceivableBonus = extraReceivableBonus;
    }

    public static Purchase unPromotablePurchaseFrom(String productName, int purchasedAmount) {
        return new Purchase(productName, true, 0, purchasedAmount, 0);
    }

    public static Purchase fullPromotablePurchaseFrom(String productName, int purchasedAmount) {
        return new Purchase(productName, true, purchasedAmount, 0, 0);
    }

    public static Purchase bonusReceivablePurchaseFrom(String productName, int purchaseAmount, int receivableBonus) {
        return new Purchase(productName, false, purchaseAmount, 0, receivableBonus);
    }

    public static Purchase partialPromotablePurchaseFrom(String productName, int promotedAmount, int normalInventory) {
        return new Purchase(productName, false, promotedAmount, normalInventory, 0);
    }

    public String getProductName() {
        return productName;
    }

    public int getPromotableAmount() {
        return promotableAmount;
    }

    public int getUnPromotableAmount() {
        return unPromotableAmount;
    }

    public int getExtraReceivableBonus() {
        return extraReceivableBonus;
    }

    public Boolean getApproved() {
        return approved;
    }

    public OrderApproveRequestDto makeApproveRequest() {
        return new OrderApproveRequestDto(productName, unPromotableAmount, extraReceivableBonus);
    }

    public void processResponse(OrderApproveResponseDto response) {
        approved = true;
        if (response.proceedPurchase()) {
            promotableAmount += extraReceivableBonus;
            extraReceivableBonus = 0;
            return;
        }
        unPromotableAmount = 0;
        extraReceivableBonus = 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Purchase purchase = (Purchase) object;
        return promotableAmount == purchase.promotableAmount && unPromotableAmount == purchase.unPromotableAmount && extraReceivableBonus == purchase.extraReceivableBonus && Objects.equals(productName, purchase.productName) && Objects.equals(approved, purchase.approved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, approved, promotableAmount, unPromotableAmount, extraReceivableBonus);
    }
}
