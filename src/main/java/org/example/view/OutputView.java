package org.example.view;

import org.example.global.Message;
import org.example.model.dto.ItemResponseDto;
import org.example.model.dto.OrderResponseDto;
import org.example.model.dto.ResultDto;

import java.util.List;

public class OutputView {

    public void showStart() {
        System.out.print(Message.START_MESSAGE);
    }

    public void wrongInput() {
        System.out.println(Message.WRONG_INPUT);
    }

    public void numInput() {
        System.out.println(Message.INPUT_NUMBER);
    }

    public void quit() {
        System.out.println(Message.FINAL_MESSAGE);
    }

    private void orderList() {
        System.out.println(Message.ORDER_LIST_MESSAGE);
    }

    private void hyphen() {
        System.out.println(Message.HYPHEN);
    }

    // 메뉴 출력
    public void printMenuList(List<ItemResponseDto> itemList) {
        itemList.forEach(item -> System.out.println(
                item.getItemNo() + "\t"
                        + item.getItemName() + "\t"
                        + item.getPrice() + "\t"
                        + item.getQuantity())
        );
    }

    public void printResult(List<OrderResponseDto> orderResponseDtoList, ResultDto result) {
        getResultPrint(orderResponseDtoList);
        hyphen();
        System.out.println("주문금액 : " + String.format("%,d", result.getPrice()) + "원");
        if (result.getShipFee() != 0) {
            System.out.println("배송비 : " + String.format("%,d", result.getShipFee()) + "원");
        }
        hyphen();
        System.out.println("지불금액 : " + String.format("%,d", result.getPayPrice()) + "원");
        hyphen();
    }

    private void getResultPrint(List<OrderResponseDto> orderResponseDtoList) {
        orderList();
        hyphen();
        for (OrderResponseDto responseDto : orderResponseDtoList) {
            System.out.println(String.format("%s", responseDto.getName()) + " - " + String.format("%d", responseDto.getQuantity()) + "개");
        }

    }
}
