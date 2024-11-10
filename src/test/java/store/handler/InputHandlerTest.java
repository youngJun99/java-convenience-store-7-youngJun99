package store.handler;

import camp.nextstep.edu.missionutils.Console;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.OrderApproveRequestDto;
import store.dto.OrderApproveResponseDto;
import store.dto.OrderDto;
import store.view.InputView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class InputHandlerTest {

    private static InputHandler inputHandler;

    @AfterEach
    void set() {
        Console.close();
    }

    @BeforeAll
    static void setUp() {
        inputHandler = new InputHandler(new InputValidator(),new InputView());
    }

    @Test
    @DisplayName("InputHandler는 주문을 바탕으로 List<OrderDto>를 만들어 준다")
    void testRequestInputOrder() {
        //given
        String readLine = "[감자칩-2],[사이다-1]";
        System.setIn(new ByteArrayInputStream(readLine.getBytes()));

        //when
        List<OrderDto> orders =  inputHandler.requestInputOrder();

        //then
        OrderDto potatoChip = new OrderDto("감자칩",2);
        OrderDto cider = new OrderDto("사이다",1);
        List<OrderDto> answer = List.of(potatoChip,cider);

        assertThat(orders).isEqualTo(answer);
    }

    @Test
    @DisplayName("InputHandler는 Y응답에는 true, N응답에는 false boolean 값을 반환한다.")
    void testCustomerRespone() {
        // given
        String input = "Y\nY\nN\nN\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertAll(
                ()->assertThat(inputHandler.requestContinueShopping()).isTrue(),
                ()->assertThat(inputHandler.requestMemberShipDiscount()).isTrue(),
                ()->assertThat(inputHandler.requestContinueShopping()).isFalse(),
                ()->assertThat(inputHandler.requestMemberShipDiscount()).isFalse()
        );
    }

    @Test
    @DisplayName("InputHandler는 구매 승인 요청에 Y를 입력하면 구매를 승인한다.")
    void requestApprovalTest1() {
        //given
        String readLine = "Y";
        System.setIn(new ByteArrayInputStream(readLine.getBytes()));
        OrderApproveRequestDto request = new OrderApproveRequestDto("콜라",2,0);

        //when
        OrderApproveResponseDto response = inputHandler.requestApproval(request);

        //then
        OrderApproveResponseDto answer = new OrderApproveResponseDto("콜라",true);
        assertThat(response).isEqualTo(answer);
    }

    @Test
    @DisplayName("InputHandler는 구매 승인 요청에 N를 입력하면 구매를 거절한다.")
    void requestApprovalTest2() {
        //given
        String readLine = "N";
        System.setIn(new ByteArrayInputStream(readLine.getBytes()));
        OrderApproveRequestDto request = new OrderApproveRequestDto("콜라",2,0);

        //when
        OrderApproveResponseDto response = inputHandler.requestApproval(request);

        //then
        OrderApproveResponseDto answer = new OrderApproveResponseDto("콜라",false);
        assertThat(response).isEqualTo(answer);
    }
}
