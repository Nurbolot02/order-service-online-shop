package com.ntg.orderserviceonlineshop.exception;

public class NotEnoughCountException extends RuntimeException {
    public NotEnoughCountException(String formatted) {
        super(formatted);
    }
}
