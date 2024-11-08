package store.domain.store.factory;

import store.domain.store.promotion.PromotionImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionFactory {

    private static final String promotionPath = "src/main/resources/promotions.md";

    private static PromotionFactory instance;

    private PromotionFactory() {
    }

    public static PromotionFactory getInstance() {
        if (instance == null) {
            instance = new PromotionFactory();
        }
        return instance;
    }

    public List<PromotionImpl> loadPromotions() throws IOException {
        List<PromotionImpl> promotions = new ArrayList<>();

        Path path = Paths.get(promotionPath);
        List<String> lines = Files.readAllLines(path);
        for (int i = 1; i < lines.size(); i++) {
            extractPromotions(lines.get(i), promotions);
        }
        return promotions;
    }

    private void extractPromotions(String line, List<PromotionImpl> promotions) {
        String[] data = line.split(",");
        String name = data[0].trim();
        int buy = Integer.parseInt(data[1].trim());
        int get = Integer.parseInt(data[2].trim());
        LocalDate startDate = LocalDate.parse(data[3].trim());
        LocalDate endDate = LocalDate.parse(data[4].trim());

        promotions.add(new PromotionImpl(name, buy, get, startDate, endDate));
    }
}

