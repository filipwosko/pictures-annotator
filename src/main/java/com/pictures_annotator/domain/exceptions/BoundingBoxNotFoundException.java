package com.pictures_annotator.domain.exceptions;

public class BoundingBoxNotFoundException extends RuntimeException{
    public BoundingBoxNotFoundException(Integer id) {
        super("Nie odnaleziono boksa o id: " + id);
    }
}
