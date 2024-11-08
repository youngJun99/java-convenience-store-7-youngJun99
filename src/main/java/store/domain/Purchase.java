package store.domain;

public class Purchase {

    private String productName;
    private Boolean approved;
    private int PromotedAmount;
    private int unPromotableAmount;
    private int receivableBonus;

    private Purchase(String productName, Boolean approved, int promotedAmount, int unPromotableAmount, int receivableBonus) {
        this.productName = productName;
        this.approved = approved;
        PromotedAmount = promotedAmount;
        this.unPromotableAmount = unPromotableAmount;
        this.receivableBonus = receivableBonus;
    }

    public static Purchase normalPurchaseFrom(String productName, int purchasedAmount) {
        return new Purchase(productName, true, 0, purchasedAmount,0);
    }

    public static Purchase promotablePurchaseFrom(String productName, int purchasedAmount) {
        return new Purchase(productName, true, purchasedAmount,0 ,0);
    }

    public static Purchase bonusReceivablePurchaseFrom(String productName, int purchaseAmount,int receivableBonus) {
        return new Purchase(productName,false,purchaseAmount,0,receivableBonus);
    }

    public static Purchase partialPromotablePurchaseFrom(String productName, int promotedAmount, int unPromotableAmount) {
        return new Purchase(productName,false,promotedAmount,unPromotableAmount,0);
    }



}
