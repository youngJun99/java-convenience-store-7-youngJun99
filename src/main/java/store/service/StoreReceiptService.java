package store.service;

import store.domain.membership.Membership;
import store.dto.ProductReceiptDto;
import store.handler.InputHandler;
import store.handler.OutputHandler;

import java.util.List;

public class StoreReceiptService {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final Membership memberShip;

    public StoreReceiptService(InputHandler inputHandler, OutputHandler outputHandler, Membership memberShip) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.memberShip = memberShip;
    }

    public void printReceipt(List<ProductReceiptDto> receipts) {
        boolean memberShipDiscountResponse = inputHandler.requestMemberShipDiscount();
        outputHandler.printInventoryReceipt(receipts);

        int totalBuy = calculateTotalBuy(receipts);

        int totalPrice = calculateTotalPrice(receipts);

        int promotionDiscount = calculatePromotionDiscount(receipts);

        int membershipDiscount = calculateMemberDiscount(memberShipDiscountResponse, totalPrice, promotionDiscount);

        outputHandler.printMoneyReceipt(totalBuy, totalPrice, promotionDiscount, membershipDiscount);
    }

    private static int calculateTotalBuy(List<ProductReceiptDto> receipts) {
        return receipts.stream()
                .mapToInt(ProductReceiptDto::buyAmount)
                .sum();
    }

    private static int calculatePromotionDiscount(List<ProductReceiptDto> receipts) {
        return receipts.stream()
                .mapToInt(receipt -> receipt.price() * receipt.promotionBonus())
                .sum();
    }

    private static int calculateTotalPrice(List<ProductReceiptDto> receipts) {
        return receipts.stream()
                .mapToInt(receipt -> receipt.price() * receipt.buyAmount())
                .sum();
    }

    private int calculateMemberDiscount(boolean memberShipDiscountResponse, int totalPrice, int promotionDiscount) {
        int membershipDiscount = 0;
        if (memberShipDiscountResponse) {
            membershipDiscount = memberShip.calculateDiscount(totalPrice - promotionDiscount);
        }
        return membershipDiscount;
    }

}
