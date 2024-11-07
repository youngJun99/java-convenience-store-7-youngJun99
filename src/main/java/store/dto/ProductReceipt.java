package store.dto;

public record ProductReceipt(
        String productName,
        int productPrice,
        int purchasedAmount,
        int promoted
) {
    public static ProductReceipt normalReceiptFrom(String productName,int productPrice, int purchasedAmount) {
        return new ProductReceipt(productName,productPrice,purchasedAmount,0);
    }
}
