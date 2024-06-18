package org.example.global;

public enum Message {
    WRONG_INPUT("잘못된 입력입니다."),
    ITEM_NO_MESSAGE("상품번호 : "),
    STOCK_MESSAGE("수량 : "),
    START_MESSAGE("입력(o[order]: 주문, q[quit]: 종료) : "),
    HEADER("상품번호\t상품명\t\t\t\t\t\t\t\t판매가격\t재고수량"),
    INPUT_NUMBER("숫자를 입력해 주세요."),
    HYPHEN("----------------------------------------------"),
    ORDER_LIST_MESSAGE("주문 내역 : "),
    FINAL_MESSAGE("고객님의 주문 감사합니다."),
    EMPTY_ORDER_MESSAGE("주문하신 상품이 없습니다.")
    ;


    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
