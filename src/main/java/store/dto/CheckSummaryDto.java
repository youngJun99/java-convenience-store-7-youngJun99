package store.dto;

public record CheckSummaryDto(
        String productName,
        Boolean approved,
        int unPromotableAmount,
        int receivableBonus
) {

    public static CheckSummaryDto normalSummaryFrom(String productName) {
        return new CheckSummaryDto(productName, true, 0, 0);
    }

    public static CheckSummaryDto bonusReceivableFrom(String productName, int receivableBonus) {
        return new CheckSummaryDto(productName,false,0,receivableBonus);
    }

    public static CheckSummaryDto unPromotableFrom(String productName, int unPromotableAmount) {
        return new CheckSummaryDto(productName,false,unPromotableAmount,0);
    }

}
