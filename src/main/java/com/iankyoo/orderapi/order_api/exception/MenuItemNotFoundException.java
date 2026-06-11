package com.iankyoo.orderapi.order_api.exception;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(Long id){
        super("MenuItem not found with id: " + id);
    }
}
