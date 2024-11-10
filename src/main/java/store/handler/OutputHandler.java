package store.handler;

import store.dto.ProductInventoryDto;
import store.dto.ReceiptDto;
import store.view.OutputView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OutputHandler {

    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###");

    private final OutputView outputView;

    public OutputHandler(OutputView outputView) {
        this.outputView = outputView;
    }

    public void printInventoryStatus(List<ProductInventoryDto> inventoryStatus) {

        List<String> inventoryList = new ArrayList<>();

        for(ProductInventoryDto inventory:inventoryStatus){
            if(!inventory.promotionName().isBlank()) {
                String productName = inventory.productName();
                String price = MONEY_FORMAT.format(inventory.price());
                int promotionInventory = inventory.promotionInventory();
                int normalInventory =inventory.normalInventory();
                inventoryList.add(formatInventoryInterface(productName,price,promotionInventory,inventory.promotionName()));
                inventoryList.add(formatInventoryInterface(productName,price,normalInventory,""));
                continue;
            }
            String productName = inventory.productName();
            String price = MONEY_FORMAT.format(inventory.price());
            int normalInventory = inventory.normalInventory();
            inventoryList.add(formatInventoryInterface(productName,price,normalInventory,""));
        }
        outputView.printInventory(inventoryList);
    }

    private String formatInventoryInterface(String productName, String price, int inventory, String promotionName){
        return String.format("%s %s원 %d개 %s",productName,price,inventory,promotionName);
    }

    public void printReceipt(List<ReceiptDto> receipts, int memberShipDiscount) {

        List<String> purchasedInventory = new ArrayList<>();
        List<String> bonusReceived = new ArrayList<>();
        int totalBoughtInventory = 0;
        int totalPrice = 0;
        int discountPrice = 0;


        for(ReceiptDto receipt:receipts) {
            String productName = receipt.productName();
            int price = receipt.price();
            if(receipt.promotionBonus() != 0){
                int bonusAmount = receipt.promotionBonus();
                discountPrice += bonusAmount*price;
                bonusReceived.add(formatBonusReceived(productName,bonusAmount));
            }
            int boughtAmount = receipt.buyAmount();
            int boughtPrice = boughtAmount*price;
            totalBoughtInventory+=boughtAmount;
            totalPrice += boughtPrice;
            String boughtTotal = MONEY_FORMAT.format(boughtPrice);
            purchasedInventory.add(formatBoughtInventory(productName,boughtAmount,boughtTotal));
        }

        int finalPayment = totalPrice-discountPrice-memberShipDiscount;

        outputView.printBoughtInventory(purchasedInventory);
        outputView.printBonusAmount(bonusReceived);
        outputView.printTotalPrice(totalBoughtInventory,totalPrice);
        outputView.printPromotionDiscount(discountPrice);
        outputView.printMemberShipDiscount(memberShipDiscount);
        outputView.printFinalPayAmount(finalPayment);
    }

    private String formatBoughtInventory(String productName, int boughtInventory, String boughtPrice) {
        return String.format("%s        %d  %s",productName,boughtInventory,boughtPrice);
    }

    private String formatBonusReceived(String productName, int bonusReceived) {
        return String.format("%s            %d",productName,bonusReceived);
    }


}
