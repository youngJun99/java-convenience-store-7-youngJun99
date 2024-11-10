package store.handler;

import store.dto.ProductInventoryDto;
import store.view.OutputView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OutputHandler {

    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###원");
    private static final DecimalFormat INVENTORY_FORMAT = new DecimalFormat("#개");

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
                String promotionInventory = INVENTORY_FORMAT.format(inventory.promotionInventory());
                String normalInventory = INVENTORY_FORMAT.format(inventory.normalInventory());
                inventoryList.add(formatInventoryInterface(productName,price,promotionInventory,inventory.promotionName()));
                inventoryList.add(formatInventoryInterface(productName,price,normalInventory,""));
                continue;
            }
            String productName = inventory.productName();
            String price = MONEY_FORMAT.format(inventory.price());
            String normalInventory = INVENTORY_FORMAT.format(inventory.normalInventory());
            inventoryList.add(formatInventoryInterface(productName,price,normalInventory,""));
        }
        outputView.printInventory(inventoryList);
    }

    private String formatInventoryInterface(String productName, String price, String inventory, String promotionName){
        return String.format("%s %s %s %s",productName,price,inventory,promotionName);
    }


}
