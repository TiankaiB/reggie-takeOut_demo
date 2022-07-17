package com.pro.reggie.common;

/**
 * self-define exception
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
