package store.handler;

import store.dto.ProductInventoryDto;
import store.dto.ProductReceiptDto;
import store.view.OutputView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OutputHandler {

    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###");
    private static final DecimalFormat INVENTORY_FORMAT = new DecimalFormat("#개");

    private final OutputView outputView;

    public OutputHandler(OutputView outputView) {
        this.outputView = outputView;
    }

    public void printInventoryStatus(List<ProductInventoryDto> inventoryStatus) {

        List<String> inventoryList = new ArrayList<>();

        for (ProductInventoryDto inventory : inventoryStatus) {
            if (!inventory.promotionName().isBlank()) {
                handlePromotableInventory(inventory, inventoryList);
                continue;
            }
            handleUnPromotableInventory(inventory, inventoryList);
        }
        outputView.printInventory(inventoryList);
    }

    private void handleUnPromotableInventory(ProductInventoryDto inventory, List<String> inventoryList) {
        String productName = inventory.productName();
        String price = MONEY_FORMAT.format(inventory.price());
        String normalInventory = formatInventory(inventory.normalInventory());
        inventoryList.add(formatInventoryInterface(productName, price, normalInventory, ""));
    }

    private void handlePromotableInventory(ProductInventoryDto inventory, List<String> inventoryList) {
        String productName = inventory.productName();
        String price = MONEY_FORMAT.format(inventory.price());
        String promotionInventory = formatInventory(inventory.promotionInventory());
        String normalInventory = formatInventory(inventory.normalInventory());
        inventoryList.add(formatInventoryInterface(productName, price, promotionInventory, inventory.promotionName()));
        inventoryList.add(formatInventoryInterface(productName, price, normalInventory, ""));
    }

    private String formatInventory(int inventory) {
        if (inventory == 0) {
            return "재고 없음";
        }
        return INVENTORY_FORMAT.format(inventory);
    }

    private String formatInventoryInterface(String productName, String price, String inventory, String promotionName) {
        return String.format("%s %s원 %s %s", productName, price, inventory, promotionName);
    }

    public void printInventoryReceipt(List<ProductReceiptDto> receipts) {

        List<String> purchasedInventory = new ArrayList<>();
        List<String> bonusReceived = new ArrayList<>();

        for (ProductReceiptDto receipt : receipts) {
            String productName = receipt.productName();
            addPurchasedInventory(receipt, purchasedInventory, productName);
            addBonuseReceid(receipt, bonusReceived, productName);
        }

        outputView.printBoughtInventory(purchasedInventory);
        outputView.printBonusAmount(bonusReceived);
    }

    private void addBonuseReceid(ProductReceiptDto receipt, List<String> bonusReceived, String productName) {
        if (receipt.promotionBonus() != 0) {
            int bonusAmount = receipt.promotionBonus();
            bonusReceived.add(formatBonusReceived(productName, bonusAmount));
        }
    }

    private void addPurchasedInventory(ProductReceiptDto receipt, List<String> purchasedInventory, String productName) {
        int price = receipt.price();
        int boughtAmount = receipt.buyAmount();
        int boughtPrice = boughtAmount * price;
        String boughtTotal = MONEY_FORMAT.format(boughtPrice);
        purchasedInventory.add(formatBoughtInventory(productName, boughtAmount, boughtTotal));
    }

    public void printMoneyReceipt(int totalBuy, int totalPrice, int promotionDiscount, int memberShipDiscount) {

        int finalPayment = totalPrice - promotionDiscount - memberShipDiscount;

        outputView.printTotalPrice(totalBuy, MONEY_FORMAT.format(totalPrice));
        outputView.printPromotionDiscount(MONEY_FORMAT.format(promotionDiscount));
        outputView.printMemberShipDiscount(MONEY_FORMAT.format(memberShipDiscount));
        outputView.printFinalPayAmount(MONEY_FORMAT.format(finalPayment));
    }

    private String formatBoughtInventory(String productName, int boughtInventory, String boughtPrice) {
        return String.format("%-19s%d\t\t%s", productName, boughtInventory, boughtPrice);
    }

    private String formatBonusReceived(String productName, int bonusReceived) {
        return String.format("%s                 %d", productName, bonusReceived);
    }


}
