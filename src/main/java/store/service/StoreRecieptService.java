package store.service;

import store.domain.store.membership.MemberShip;
import store.dto.ReceiptDto;
import store.handler.InputHandler;
import store.handler.OutputHandler;

import java.util.List;

public class StoreRecieptService {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final MemberShip memberShip;

    public StoreRecieptService(InputHandler inputHandler, OutputHandler outputHandler, MemberShip memberShip) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.memberShip = memberShip;
    }

    public boolean printReceiptAndProceed(List<ReceiptDto> receipts) {
        boolean memberShipDiscount = inputHandler.requestMemberShipDiscount();

        outputHandler.printReceipt();
    }
}
