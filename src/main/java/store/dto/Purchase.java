package store.dto;

public record Purchase(
        String productName,
        Boolean approved,
        int unPromotableAmount,
        int receivableBonus
) {

    public static Purchase normalSummaryFrom(String productName) {
        return new Purchase(productName, true, 0, 0);
    }

    public static Purchase bonusReceivableFrom(String productName, int receivableBonus) {
        return new Purchase(productName,false,0,receivableBonus);
    }

    public static Purchase unPromotableFrom(String productName, int unPromotableAmount) {
        return new Purchase(productName,false,unPromotableAmount,0);
    }

}
