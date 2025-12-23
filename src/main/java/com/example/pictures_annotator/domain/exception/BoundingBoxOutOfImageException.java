package com.example.pictures_annotator.domain.exception;

public class BoundingBoxOutOfImageException extends RuntimeException {
    public BoundingBoxOutOfImageException(String message){
        super(message);
    }
}
