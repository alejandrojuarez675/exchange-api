package com.alejua.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotAllowedMoneyCode extends CustomException {
    public NotAllowedMoneyCode(String moneyCode) {
        super(moneyCode);
    }
}
