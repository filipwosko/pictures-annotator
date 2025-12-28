package com.pictures_annotator.domain.exceptions;

public class PictureNotFoundException extends RuntimeException {
    public PictureNotFoundException(Integer id) {
        super("Nie odnaleziono obrazu o id: " + id);
    }
}
