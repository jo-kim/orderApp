package org.example.view;

import org.example.model.dto.ItemResponseDto;
import org.example.model.dto.OrderRequestDto;
import org.example.order.OrderCollect;
import org.example.order.OrderProcess;
import org.example.service.ItemService;
import org.example.service.OrderService;
import org.example.global.Validator;

import java.util.List;

public class Display {


    private final ItemService itemService = new ItemService();
    private final OrderService orderService = new OrderService();
    private final OrderCollect orderCollect = new OrderCollect();
    private final OrderProcess orderProcess = new OrderProcess();
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final Validator validator = new Validator();


    public void start() {
        while (true) {
            outputView.showStart();

            String input = inputView.getInput();

            if (!validator.isValid(input)) {
                outputView.wrongInput();
                continue;
            }
            // 프로그램 종료
            if (validator.isQuit(input)) {
                outputView.quit();
                break;
            }
            // 주문 시작
            List<ItemResponseDto> itemList = itemService.getItemList();
            // 메뉴 출력
            outputView.printMenuList(itemList);
            // 메뉴 담기
            List<OrderRequestDto> orderRequestList = orderCollect.collectOrders(itemList);

            if(!orderRequestList.isEmpty()){
                // 계산
                orderProcess.processOrder(orderRequestList);
            }
        }
    }









}