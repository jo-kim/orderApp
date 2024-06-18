package org.example.service;

import org.example.exception.SoldOutException;
import org.example.model.item.Item;
import org.example.model.dto.OrderResponseDto;
import org.example.model.dto.ResultDto;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class OrderService {
    private final ItemService itemService = new ItemService();
    private final ReentrantLock lock = new ReentrantLock();

    // 주문 결과
    public ResultDto getResult(final List<OrderResponseDto> orderResponseDtoList) {
        // 락
        lock.lock();
        try {
            // 재고확인
            validateOrderQuantities(orderResponseDtoList);
            int orderPrice = calculateOrderPrice(orderResponseDtoList);
            int shipFee = calculateShippingFee(orderPrice);
            // 재고 업데이트
            updateItemQuantities(orderResponseDtoList);

            return new ResultDto(orderPrice, shipFee, orderPrice + shipFee);

        } catch (SoldOutException e) {
            throw e;
        } finally {
            // 락 해제
            lock.unlock();
        }
    }

    private void validateOrderQuantities(List<OrderResponseDto> orderResponseDtoList) {
        for (OrderResponseDto orderResponseDto : orderResponseDtoList) {
            Item item = itemService.getItem(orderResponseDto.getItemNo());
            if (item.getQuantity() - orderResponseDto.getQuantity() < 0) {
                throw new SoldOutException("SoldOutException 발생. 주문한 상품이 재고량보다 큽니다.");
            }
        }
    }

    private int calculateOrderPrice(List<OrderResponseDto> orderResponseDtoList) {
        int orderPrice = 0;
        for (OrderResponseDto orderResponseDto : orderResponseDtoList) {
            Item item = itemService.getItem(orderResponseDto.getItemNo());
            orderPrice += item.getPrice() * orderResponseDto.getQuantity();
        }
        return orderPrice;
    }

    private int calculateShippingFee(int orderPrice) {
        if (orderPrice < 50000) {
            return 2500;
        }
        return 0;
    }

    private void updateItemQuantities(List<OrderResponseDto> orderResponseDtoList) {
        for (OrderResponseDto orderResponseDto : orderResponseDtoList) {
            Item item = itemService.getItem(orderResponseDto.getItemNo());
            item.setQuantity(item.getQuantity() - orderResponseDto.getQuantity());
            // 재고 업데이트
            itemService.updateItem(item);
        }
    }
}
