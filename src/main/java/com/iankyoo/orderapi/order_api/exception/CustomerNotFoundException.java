package com.iankyoo.orderapi.order_api.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id){
        super("Customer not found with id:" + id);
    }
}
