package store.domain;

public class Purchase {

    private String productName;
    private Boolean approved;
    private int promotableAmount;
    private int unPromotableAmount;
    private int extraReceivableBonus;

    private Purchase(String productName, Boolean approved, int promotableAmount, int unPromotableAmount, int extraReceivableBonus) {
        this.productName = productName;
        this.approved = approved;
        this.promotableAmount = promotableAmount;
        this.unPromotableAmount = unPromotableAmount;
        this.extraReceivableBonus = extraReceivableBonus;
    }

    public static Purchase normalPurchaseFrom(String productName, int purchasedAmount) {
        return new Purchase(productName, true, 0, purchasedAmount,0);
    }

    public static Purchase fullPromotablePurchaseFrom(String productName, int purchasedAmount) {
        return new Purchase(productName, true, purchasedAmount,0 ,0);
    }

    public static Purchase bonusReceivablePurchaseFrom(String productName, int purchaseAmount,int receivableBonus) {
        return new Purchase(productName,false,purchaseAmount,0,receivableBonus);
    }

    public static Purchase partialPromotablePurchaseFrom(String productName, int promotedAmount, int normalInventory) {
        return new Purchase(productName,false,promotedAmount,normalInventory,0);
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

    public Boolean isNotApproved() {
        return !approved;
    }

    public void confirmBonus(){
        approved = true;
        promotableAmount =+extraReceivableBonus;
        extraReceivableBonus = 0;
    }

    public void discardBonus() {
        approved = true;
        extraReceivableBonus = 0;
    }

    public void confirmUnPromotableAmount() {
        approved = true;
    }

    public void discardUnPromotableAmount() {
        approved = true;
        unPromotableAmount = 0;
    }
}
