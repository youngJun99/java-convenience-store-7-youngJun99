package store.handler;

import store.dto.OrderDto;
import store.dto.OrderSheetDto;
import store.view.InputView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {

    private static final Pattern orderPattern = Pattern.compile("\\[(\\w+)-(\\d+)]");

    private InputValidator inputValidator;
    private InputView inputView;

    public InputHandler(InputValidator inputValidator, InputView inputView) {
        this.inputValidator = inputValidator;
        this.inputView = inputView;
    }

    public OrderSheetDto requestInputOrder() {
        String inputOrder = inputView.printOrderRequest();
        inputValidator.validateProductOrder(inputOrder);

        //promotion.md의 형태에 따라 우테코 라이브러리의 LocalTimes.now 를 사용하지 않았습니다.
        LocalDate orderTime = LocalDate.now();

        List<OrderDto> orders = extractOrders(inputOrder);

        return new OrderSheetDto(orders, orderTime);
    }

    public boolean requestWhetherToReceiveBonus(String productName, int receivableBonus) {
        String inputAnswer = inputView.printExtraBonusReceiveRequest(productName, receivableBonus);
        return handleCustomerResponse(inputAnswer);
    }

    public boolean requestToConfirmUnPromotable(String productName, int unPromotable) {
        String inputAnswer = inputView.printUnPromotableConditionRequest(productName, unPromotable);
        return handleCustomerResponse(inputAnswer);
    }

    public boolean requestMemberShipDiscount() {
        String inputAnswer = inputView.printMemberShipDiscountRequest();
        return handleCustomerResponse(inputAnswer);
    }

    public boolean requestContinueShopping() {
        String inputAnswer = inputView.printExtraBuyRequest();
        return handleCustomerResponse(inputAnswer);
    }

    private boolean handleCustomerResponse(String inputResponse) {
        inputValidator.validateCustomerResponse(inputResponse);
        return inputResponse.equals("Y");
    }

    private List<OrderDto> extractOrders(String inputOrder) {
        List<OrderDto> orders = new ArrayList<>();
        Matcher matcher = orderPattern.matcher(inputOrder);
        while (matcher.find()) {
            String productName = matcher.group(1);
            int quantity = Integer.parseInt(matcher.group(2));
            orders.add(new OrderDto(productName, quantity));
        }
        return orders;
    }

}
