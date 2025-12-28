package com.pictures_annotator.domain.exceptions;

public class PictureNotValidException extends RuntimeException {
    public PictureNotValidException(String message){
        super(message);
    };
}
