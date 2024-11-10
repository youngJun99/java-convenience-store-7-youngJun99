package store.view;

import java.util.List;

public class OutputView {

    public void printInventory(List<String> inventory) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다." + System.lineSeparator());
        inventory.stream()
                .map(item -> "- " + item)
                .forEach(System.out::println);
        System.out.println();
    }

    public void printBoughtInventory(List<String> buyAmount) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t\t\t수량\t\t금액");
        buyAmount.forEach(System.out::println);
    }

    public void printBonusAmount(List<String> bonusAmount) {
        System.out.println("=============증\t\t정===============");
        bonusAmount.forEach(System.out::println);
        System.out.println("====================================");
    }

    public void printTotalPrice(int totalAmount, String totalPrice) {
        System.out.printf("총구매액\t\t\t\t%d\t\t%s%n", totalAmount, totalPrice);
    }

    public void printPromotionDiscount(String discount) {
        System.out.printf("행사할인\t\t\t\t\t\t-%s%n", discount);
    }

    public void printMembershipDiscount(String discount) {
        System.out.printf("멤버십할인\t\t\t\t\t\t-%s%n", discount);
    }

    public void printFinalPayAmount(String total) {
        System.out.printf("내실돈\t\t\t\t\t\t %s%n", total);
        System.out.println();
    }

}
