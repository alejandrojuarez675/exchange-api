package com.alejua.app.exceptions;

public class NotAllowedMoneyCode extends Exception {
    public NotAllowedMoneyCode(String moneyCode) {
        super(moneyCode);
    }
}
