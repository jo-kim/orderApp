package org.example.global;

import java.util.regex.Pattern;

public class Validator {
    // 숫자 확인
    public boolean isValidNum(String input) {
        Pattern pattern = Pattern.compile("^[0-9 ]+$");
        return pattern.matcher(input).matches();
    }
    // SPACE + ENTER
    public boolean isExitInput(String itemNo, String orderNum) {
        return itemNo.trim().isEmpty() && orderNum.trim().isEmpty();
    }

    // 종료 확인
    public boolean isQuit(String input) {
        return "q".equals(input) || "quit".equals(input);
    }

    // 입력확인
    public boolean isValid(String input) {
        Pattern pattern = Pattern.compile("(o|q|order|quit)");
        return pattern.matcher(input).matches();
    }
}
