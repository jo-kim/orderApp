package org.example.order;

import org.example.exception.SoldOutException;
import org.example.model.dto.OrderRequestDto;
import org.example.model.dto.OrderResponseDto;
import org.example.model.dto.ResultDto;
import org.example.service.ItemService;
import org.example.service.OrderService;
import org.example.view.OutputView;

import java.util.List;
import java.util.stream.Collectors;

public class OrderProcess {

    private final ItemService itemService = new ItemService();
    private final OrderService orderService = new OrderService();
    private final OutputView outputView = new OutputView();

    // 상품 계산
    public void processOrder(List<OrderRequestDto> orderRequestList) {
        List<OrderResponseDto> orderResponses = orderRequestList.stream()
                .map(order-> new OrderResponseDto(
                        order.getItemNo(),
                        findItemName(order.getItemNo()),
                        order.getQuantity()))
                .collect(Collectors.toList());;
        try {
            ResultDto result = orderService.getResult(orderResponses);
            if (result != null) {
                outputView.printResult(orderResponses, result);
            }
        } catch (SoldOutException e) {
            System.err.println(e.getMessage());
        }
    }

    // 상품 이름 확인
    private String findItemName(long itemNo) {
        return itemService.getItemList().stream()
                .filter(i->i.getItemNo() == itemNo)
                .findFirst().get().getItemName();
    }

}
