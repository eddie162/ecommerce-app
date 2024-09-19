package com.eddie.ecommerce.exceptions;

public class GenericAPIException extends RuntimeException {
    public GenericAPIException(String message){
        super(message);
    }
}
