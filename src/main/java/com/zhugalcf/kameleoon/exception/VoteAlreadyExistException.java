package com.zhugalcf.kameleoon.exception;

public class VoteAlreadyExistException extends RuntimeException{
    public VoteAlreadyExistException(String message) {
        super(message);
    }
}
