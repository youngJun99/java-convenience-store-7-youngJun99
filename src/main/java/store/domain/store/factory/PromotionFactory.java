package store.domain.store.factory;

import store.domain.store.promotion.NullPromotion;
import store.domain.store.promotion.Promotion;
import store.domain.store.promotion.PromotionImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionFactory {

    private static PromotionFactory instance;

    private PromotionFactory() {
    }

    public static PromotionFactory getInstance() {
        if(instance == null) {
            instance = new PromotionFactory();
        }
        return instance;
    }

    public List<PromotionImpl> loadPromotions(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<PromotionImpl> promotions = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            // 첫 줄을 건너뜁니다.
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                extractPromotions(line, promotions);
            }
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

