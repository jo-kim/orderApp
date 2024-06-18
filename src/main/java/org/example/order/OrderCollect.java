package org.example.order;

import org.example.model.dto.ItemResponseDto;
import org.example.model.dto.OrderRequestDto;
import org.example.global.Validator;
import org.example.view.InputView;
import org.example.global.Message;
import org.example.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class OrderCollect {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final Validator validator  = new Validator();

    // 주문 담기
    public List<OrderRequestDto> collectOrders(List<ItemResponseDto> itemList) {
        List<OrderRequestDto> orderRequestDtoList = new ArrayList<>();
        while (true) {
            String itemNo = inputView.getItemNo();
            String orderNum = inputView.getOrderNum();

            if (validator.isExitInput(itemNo, orderNum)) {
                break;
            }
            if (!validator.isValidNum(itemNo) || !validator.isValidNum(orderNum)) {
                outputView.numInput();
                continue;
            }
            try {
                OrderRequestDto orderRequest = createOrderRequest(itemNo, orderNum, itemList);
                orderRequestDtoList.add(orderRequest);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
        return orderRequestDtoList;
    }

    // 상품 주문
    private OrderRequestDto createOrderRequest(String itemNoInput, String orderNumInput, List<ItemResponseDto> itemList) {
        int itemNo = Integer.parseInt(itemNoInput);
        int orderQuantity = Integer.parseInt(orderNumInput);

        itemList.stream()
                .filter(i -> i.getItemNo() == itemNo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.valueOf(Message.EMPTY_ORDER_MESSAGE)));

        return new OrderRequestDto(itemNo, orderQuantity);
    }

}
