package com.example.pictures_annotator.domain.exception;

public class BoundingBoxNotFoundException extends RuntimeException{
    public BoundingBoxNotFoundException(Integer id) {
        super("Nie odnaleziono boksa o id: " + id);
    }
}
