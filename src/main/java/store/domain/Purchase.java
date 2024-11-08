package store.domain;

public class Purchase {

    private String productName;
    private Boolean approved;
    private int promotionInventory;
    private int normalInventory;
    private int receivableBonus;

    private Purchase(String productName, Boolean approved, int promotionInventory, int normalInventory, int receivableBonus) {
        this.productName = productName;
        this.approved = approved;
        this.promotionInventory = promotionInventory;
        this.normalInventory = normalInventory;
        this.receivableBonus = receivableBonus;
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



}
