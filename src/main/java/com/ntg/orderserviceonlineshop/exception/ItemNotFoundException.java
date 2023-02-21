package com.ntg.orderserviceonlineshop.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String formatted) {
        super(formatted);
    }
}
