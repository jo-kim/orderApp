package org.example.view;

import org.example.exception.SoldOutException;
import org.example.model.dto.ItemResponseDto;
import org.example.model.dto.OrderRequestDto;
import org.example.model.dto.OrderResponseDto;
import org.example.model.dto.ResultDto;
import org.example.service.ItemService;
import org.example.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Display {
    private static final String WRONG_INPUT = "잘못된 입력입니다.";
    private static final String START_MESSAGE = "입력(o[order]: 주문, q[quit]: 종료) : ";
    private static final String HEADER = "상품번호\t상품명\t\t\t\t\t\t\t\t판매가격\t재고수량";
    private static final String ITEM_NO_MESSAGE = "상품번호 : ";
    private static final String STOCK_MESSAGE = "수량 : ";
    private static final String INPUT_NUMBER = "숫자를 입력해 주세요.";
    private static final String HYPHEN = "----------------------------------------------";
    private static final String ORDER_LIST_MESSAGE = "주문 내역 : ";
    private static final String FINAL_MESSAGE = "고객님의 주문 감사합니다.";
    private static final String EMPTY_ORDER_MESSAGE = "주문하신 상품이 없습니다.";


    private static final Scanner sc = new Scanner(System.in);
    private final ItemService itemService = new ItemService();
    private final OrderService orderService = new OrderService();


    public void start() {

        while (true) {

            String operation = "";

            System.out.print(START_MESSAGE);
            operation = sc.nextLine();
            if (!operation.matches("(o|q|order|quit)")) {
                System.out.println(WRONG_INPUT);
                continue;
            }
            // 프로그램 종료
            if (operation.equals("q")) {
                System.out.println(FINAL_MESSAGE);
                break;
            }

            // 주문 시작
            System.out.println(HEADER);
            List<ItemResponseDto> itemList = itemService.getItemList();
            itemList.forEach(item -> System.out.println(
                              item.getItemNo() + "\t"
                            + item.getItemName() + "\t"
                            + item.getPrice() + "\t"
                            + item.getQuantity())
            );

            // 주문받기
            List<OrderRequestDto> orderRequestDtoList = new ArrayList<>();
            List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
            while (true) {
                // 상품번호 입력 메시지
                System.out.print(ITEM_NO_MESSAGE);
                String itemNo = sc.nextLine();

                // 수량 입력 메시지
                System.out.print(STOCK_MESSAGE);
                String orderCnt = sc.nextLine();
                if (!itemNo.matches("^[0-9 ]+$") || !orderCnt.matches("^[0-9 ]+$")) {
                    System.out.println(INPUT_NUMBER);
                    continue;
                }
                // SPACE + ENTER 조건
                if (itemNo.equals(" ") && orderCnt.equals(" ")) {
                    break;
                }
                // 주문 요청
                OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                        .itemNo(Integer.parseInt(itemNo))
                        .quantity(Integer.parseInt(orderCnt))
                        .build();

                try {
                    // 상품 확인
                    ItemResponseDto itemResponse = itemList.stream()
                            .filter(i -> i.getItemNo() == orderRequestDto.getItemNo())
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException(EMPTY_ORDER_MESSAGE));
                    // 주문 담기
                    orderRequestDtoList.add(orderRequestDto);
                    // 응답
                    OrderResponseDto orderResponseDto = new OrderResponseDto(
                            itemResponse.getItemNo(),
                            itemResponse.getItemName(),
                            orderRequestDto.getQuantity()
                    );

                    orderResponseDtoList.add(orderResponseDto);

                } catch (IllegalArgumentException e) {
                    System.err.print(e.getMessage());
                }

            }
            // 주문 있으면
            if (!orderResponseDtoList.isEmpty()) {
                try {
                    // 응답 결과
                    ResultDto result = orderService.getResult(orderResponseDtoList);
                    if (result == null) {
                        break;
                    }
                    getResultPrint(orderResponseDtoList);
                    System.out.println(HYPHEN);
                    System.out.println("주문금액 : " + String.format("%,d", result.getPrice()) + "원");
                    if (result.getShipFee() != 0) {
                        System.out.println("배송비 : " + String.format("%,d", result.getShipFee()) + "원");
                    }
                    System.out.println(HYPHEN);
                    System.out.println("지불금액 : " + String.format("%,d", result.getPayPrice()) + "원");
                    System.out.println(HYPHEN);

                } catch (SoldOutException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    private void getResultPrint(List<OrderResponseDto> orderResponseDtoList) {
        System.out.println(ORDER_LIST_MESSAGE);
        System.out.println(HYPHEN);
        for (OrderResponseDto responseDto : orderResponseDtoList) {
            System.out.println(String.format("%s", responseDto.getName()) + " - " + String.format("%d", responseDto.getQuantity()) + "개");
        }
    }


}