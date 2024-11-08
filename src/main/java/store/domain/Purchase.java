package store.domain;

import store.dto.OrderApproveRequestDto;
import store.dto.OrderApproveResponseDto;

public class Purchase {

    private String productName;
    private int price;
    private Boolean approved;
    private int promotableAmount;
    private int unPromotableAmount;
    private int extraReceivableBonus;

    public Purchase(String productName, int price, Boolean approved, int promotableAmount, int unPromotableAmount, int extraReceivableBonus) {
        this.productName = productName;
        this.price = price;
        this.approved = approved;
        this.promotableAmount = promotableAmount;
        this.unPromotableAmount = unPromotableAmount;
        this.extraReceivableBonus = extraReceivableBonus;
    }

    public static Purchase normalPurchaseFrom(String productName, int price, int purchasedAmount) {
        return new Purchase(productName, price,true, 0, purchasedAmount,0);
    }

    public static Purchase fullPromotablePurchaseFrom(String productName, int price, int purchasedAmount) {
        return new Purchase(productName, price,true, purchasedAmount,0 ,0);
    }

    public static Purchase bonusReceivablePurchaseFrom(String productName, int price, int purchaseAmount,int receivableBonus) {
        return new Purchase(productName,price,false,purchaseAmount,0,receivableBonus);
    }

    public static Purchase partialPromotablePurchaseFrom(String productName,int price, int promotedAmount, int normalInventory) {
        return new Purchase(productName,price,false,promotedAmount,normalInventory,0);
    }

    public String getProductName() {
        return productName;
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

    public OrderApproveRequestDto makeRequest() {
        return new OrderApproveRequestDto(productName, unPromotableAmount, extraReceivableBonus);
    }

    public void processResponse(OrderApproveResponseDto response) {
        if(response.proceedPurchase()) {
            if(unPromotableAmount == 0) confirmBonus();
            if(extraReceivableBonus == 0) confirmUnPromotableAmount();
        }
        if(unPromotableAmount == 0) discardBonus();
        if(extraReceivableBonus == 0) discardUnPromotableAmount();
    }

    private void confirmBonus(){
        approved = true;
        promotableAmount =+extraReceivableBonus;
        extraReceivableBonus = 0;
    }

    private void discardBonus() {
        approved = true;
        extraReceivableBonus = 0;
    }

    private void confirmUnPromotableAmount() {
        approved = true;
    }

    private void discardUnPromotableAmount() {
        approved = true;
        unPromotableAmount = 0;
    }
}
