package com.example.pictures_annotator.presentation.handler;

import com.example.pictures_annotator.domain.exception.BoundingBoxNotFoundException;
import com.example.pictures_annotator.domain.exception.BoundingBoxOutOfImageException;
import com.example.pictures_annotator.domain.exception.PictureNotFoundException;
import com.example.pictures_annotator.domain.exception.PictureNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PictureNotFoundException.class)
    public ResponseEntity<String> handlePictureNotFound(PictureNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PictureNotValidException.class)
    public ResponseEntity<String> handleBoundingBoxNotFound(PictureNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BoundingBoxNotFoundException.class)
    public ResponseEntity<String> handleBoundingBoxNotFound(BoundingBoxNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BoundingBoxOutOfImageException.class)
    public ResponseEntity<String> handleBoundingBoxOutOfImage(BoundingBoxOutOfImageException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
