package store.handler;

import store.dto.OrderApproveRequestDto;
import store.dto.OrderApproveResponseDto;
import store.dto.OrderDto;
import store.view.InputView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {

    private static final Pattern orderPattern = Pattern.compile("\\[([\\p{L}]+)-(\\d+)]");

    private final InputValidator inputValidator;
    private final InputView inputView;

    public InputHandler(InputValidator inputValidator, InputView inputView) {
        this.inputValidator = inputValidator;
        this.inputView = inputView;
    }

    public List<OrderDto> requestInputOrder() {
        String inputOrder = orderRetryHandler(inputView::printOrderRequest);

        //promotion.md의 형태에 따라 우테코 라이브러리의 LocalTimes.now 를 사용하지 않았습니다.
        LocalDate orderTime = LocalDate.now();

        return extractOrders(inputOrder);
    }

    public OrderApproveResponseDto requestApproval(OrderApproveRequestDto orderApproveRequestDto) {
        String productName = orderApproveRequestDto.productName();
        if (orderApproveRequestDto.extraReceivableBonus() != 0) {
            return bonusReceiveResponse(orderApproveRequestDto, productName);
        }
        return unPromotableConfirmResponse(orderApproveRequestDto, productName);
    }

    private OrderApproveResponseDto unPromotableConfirmResponse(OrderApproveRequestDto orderApproveRequestDto, String productName) {
        int unPromotable = orderApproveRequestDto.unPromotableAmount();
        String inputAnswer = responseRetryHandler(() -> inputView.printUnPromotableConditionRequest(productName, unPromotable));
        boolean answer = inputAnswer.equals("Y");
        return new OrderApproveResponseDto(productName, answer);
    }

    private OrderApproveResponseDto bonusReceiveResponse(OrderApproveRequestDto orderApproveRequestDto, String productName) {
        int receivableBonus = orderApproveRequestDto.extraReceivableBonus();
        String inputAnswer = responseRetryHandler(() -> inputView.printExtraBonusReceiveRequest(productName, receivableBonus));
        boolean answer = inputAnswer.equals("Y");
        return new OrderApproveResponseDto(productName, answer);
    }

    public boolean requestMemberShipDiscount() {
        String inputAnswer = responseRetryHandler(inputView::printMemberShipDiscountRequest);
        return inputAnswer.equals("Y");
    }

    public boolean requestContinueShopping() {
        String inputAnswer = responseRetryHandler(inputView::printExtraBuyRequest);
        return inputAnswer.equals("Y");
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


    private String orderRetryHandler(Supplier<String> inputSupplier) {
        while (true) {
            String inputOrder = inputSupplier.get();
            try {
                inputValidator.validateProductOrder(inputOrder);
                return inputOrder;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String responseRetryHandler(Supplier<String> inputSupplier) {
        while (true) {
            String inputResponse = inputSupplier.get();
            try {
                inputValidator.validateCustomerResponse(inputResponse);
                return inputResponse;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
