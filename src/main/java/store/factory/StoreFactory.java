package store.factory;

import store.domain.store.Store;
import store.domain.store.Product;
import store.domain.store.promotion.NullPromotion;
import store.domain.store.promotion.Promotion;
import store.domain.store.promotion.PromotionImpl;

import java.util.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class StoreFactory {

    private static final String productsPath = "src/main/resources/products.md";

    private final List<PromotionImpl> promotions;

    public StoreFactory(List<PromotionImpl> promotions) {
        this.promotions = promotions;
    }

    public Store createStore() throws IOException {
        List<Product> products = new ArrayList<>();
        Path path = Paths.get(productsPath);
        extractProducts(path, products);
        return new Store(products);
    }

    private void extractProducts(Path path, List<Product> products) throws IOException {
        List<String> lines = Files.readAllLines(path);
        for (int i = 1; i < lines.size(); i++) {
            extractOneLine(lines.get(i), products);
        }
    }

    private void extractOneLine(String line, List<Product> products) {
        String[] data = line.split(",");
        String productName = data[0].trim();
        int price = Integer.parseInt(data[1].trim());
        int promotionInventory = Integer.parseInt(data[2].trim());
        int normalInventory = Integer.parseInt(data[3].trim());
        Promotion promotion = NullPromotion.getInstance();
        if (!data[4].trim().equals("null")) {
            promotion = getPromotionByName(data[4].trim());
        }
        products.add(new Product(productName, promotion, price, promotionInventory, normalInventory));
    }

    private PromotionImpl getPromotionByName(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(name))
                .findFirst().get();
    }
}
