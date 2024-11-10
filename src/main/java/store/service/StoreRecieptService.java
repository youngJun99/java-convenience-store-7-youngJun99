package store.service;

import store.domain.membership.Membership;
import store.dto.ReceiptDto;
import store.handler.InputHandler;
import store.handler.OutputHandler;

import java.util.List;

public class StoreRecieptService {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final Membership memberShip;

    public StoreRecieptService(InputHandler inputHandler, OutputHandler outputHandler, Membership memberShip) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.memberShip = memberShip;
    }

    public void printReceipt(List<ReceiptDto> receipts) {
        boolean memberShipDiscountResponse = inputHandler.requestMemberShipDiscount();
        outputHandler.printInventoryReceipt(receipts);

        int totalBuy = receipts.stream()
                .mapToInt(ReceiptDto::buyAmount)
                .sum();

        int totalPrice = receipts.stream()
                .mapToInt(receipt -> receipt.price() * receipt.buyAmount())
                .sum();

        int promotionDiscount = receipts.stream()
                .mapToInt(receipt -> receipt.price() * receipt.promotionBonus())
                .sum();

        int membershipDiscount =0;
        if(memberShipDiscountResponse){
            membershipDiscount = memberShip.processDiscount(totalPrice - promotionDiscount);
        }

        outputHandler.printMoneyReceipt(totalBuy, totalPrice, promotionDiscount, membershipDiscount);
    }
}
