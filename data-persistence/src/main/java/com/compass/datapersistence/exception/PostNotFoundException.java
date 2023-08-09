package com.compass.datapersistence.exception;

public class PostNotFoundException extends EntityNotFoundException{
    public PostNotFoundException(String message){
        super(message);
    }
}
