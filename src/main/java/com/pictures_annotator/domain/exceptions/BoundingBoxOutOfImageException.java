package com.pictures_annotator.domain.exceptions;

public class BoundingBoxOutOfImageException extends RuntimeException {
    public BoundingBoxOutOfImageException(String message){
        super(message);
    }
}
