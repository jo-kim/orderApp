package org.example.service;

import org.example.exception.SoldOutException;
import org.example.model.item.Item;
import org.example.model.dto.OrderResponseDto;
import org.example.model.dto.ResultDto;

import java.util.List;

public class OrderService {
    private final ItemService itemService = new ItemService();


    // 주문 결과
    public synchronized ResultDto getResult(final List<OrderResponseDto> orderResponseDtoList) {
        int orderPrice = 0;
        int shipFee = 0;

        for (OrderResponseDto orderResponseDto : orderResponseDtoList) {
            Item item = itemService.getItem(orderResponseDto.getItemNo());
            // 재고 있을시
            if (item.getQuantity() - orderResponseDto.getQuantity() >= 0) {
                item.setQuantity(item.getQuantity() - orderResponseDto.getQuantity());
                orderPrice += item.getPrice() * orderResponseDto.getQuantity();
                // 수량 업데이트
                itemService.updateItem(item);
            }else{
                throw new SoldOutException("SoldOutException 발생. 주문한 상품이 재고량보다 큽니다.");
            }
        }

        if(orderPrice < 50000) {
            shipFee = 2500;
        }
        ResultDto resultDto = new ResultDto(
                orderPrice, shipFee, (orderPrice + shipFee)
        );

        return resultDto;
    }
}
