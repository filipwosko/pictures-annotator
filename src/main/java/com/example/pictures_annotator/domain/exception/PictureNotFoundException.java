package com.example.pictures_annotator.domain.exception;

public class PictureNotFoundException extends RuntimeException {

    public PictureNotFoundException(Integer id) {
        super("Nie odnaleziono obrazu o id: " + id);
    }
}
