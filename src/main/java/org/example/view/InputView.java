package org.example.view;

import org.example.global.Message;

import java.util.Scanner;

public class InputView {
    private static final Scanner sc = new Scanner(System.in);

    public String getInput() {
        return sc.nextLine();
    }

    public String getItemNo() {
        System.out.print(Message.ITEM_NO_MESSAGE);
        return getInput();
    }

    public String getOrderNum() {
        System.out.print(Message.STOCK_MESSAGE);
        return getInput();
    }

}
