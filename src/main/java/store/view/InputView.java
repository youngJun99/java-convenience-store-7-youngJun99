package store.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class InputView {

    public String requestInputOrder() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예 : [사이다 -2],[감자칩-1]");
        return readLine();
    }

    public String requestWhetherToReceiveBonus(String productName, int receivableBonus) {
        System.out.println(String.format("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)",productName,receivableBonus));
        return readLine();
    }

    public String requestToConfirmUnPromotable(String productname, int unPromotable) {
        System.out.println(String.format("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"));
        return readLine();
    }

    public String requestMemberShipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return readLine();
    }

    public String requestMorePurchase() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return readLine();
    }
}
