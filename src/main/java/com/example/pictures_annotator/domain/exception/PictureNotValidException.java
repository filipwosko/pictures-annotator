package com.example.pictures_annotator.domain.exception;

public class PictureNotValidException extends RuntimeException {

    public PictureNotValidException(String message){
        super(message);
    };
}
