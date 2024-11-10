package store.factory;

import store.domain.store.Store;
import store.domain.store.Product;
import store.domain.promotion.NullPromotion;
import store.domain.promotion.Promotion;

import java.util.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class StoreFactoryImpl implements StoreFactory {


    private final PromotionFactory promotionFactory;

    private List<Promotion> promotions;

    public StoreFactoryImpl(PromotionFactory promotionFactory) {
        this.promotionFactory = promotionFactory;
    }

    public Store createStore(String productsPath, String promotionPath) throws IOException {
        promotions = promotionFactory.loadPromotions(promotionPath);
        List<Product> products = new ArrayList<>();
        Path path = Paths.get(productsPath);
        extractProducts(path, products);
        return new Store(products);
    }

    private void extractProducts(Path path, List<Product> products) throws IOException {
        List<String> lines = Files.readAllLines(path);
        for (int i = 1; i < lines.size(); i++) {

            String[] data = lines.get(i).split(",");
            String productName = data[0].trim();
            int price = Integer.parseInt(data[1].trim());

            if (data[3].trim().equals("null")) {
                int normalInventory = Integer.parseInt(data[2].trim());
                Promotion noPromotion = NullPromotion.getInstance();
                products.add(new Product(productName,noPromotion,price,0,normalInventory));
                continue;
            }
            int promotionInventory = Integer.parseInt(data[2].trim());
            Promotion promotion = getPromotionByName(data[3].trim());

            if (i + 1 < lines.size()) {
                String[] nextLine = lines.get(i + 1).split(",");
                if (nextLine[0].trim().equals(productName)) {
                    int normalInventory = Integer.parseInt(nextLine[2].trim());
                    products.add(new Product(productName, promotion, price, promotionInventory, normalInventory));
                    i++;
                    continue;
                }
            }
            products.add(new Product(productName, promotion, price, promotionInventory, 0));
        }
    }

    private Promotion getPromotionByName(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(name))
                .findFirst().get();
    }
}
