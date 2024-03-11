package com.alejua.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class FeignClientException extends CustomException {
    public FeignClientException(String feignClient) {
        super(feignClient);
    }
}
