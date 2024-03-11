package com.alejua.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotAllowedExchangeRateException extends CustomException {
    public NotAllowedExchangeRateException(String message) {
        super(message);
    }
}
