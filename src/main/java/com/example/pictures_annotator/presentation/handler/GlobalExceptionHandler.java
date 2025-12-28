package com.example.pictures_annotator.presentation.handler;

import com.example.pictures_annotator.domain.exception.BoundingBoxNotFoundException;
import com.example.pictures_annotator.domain.exception.BoundingBoxOutOfImageException;
import com.example.pictures_annotator.domain.exception.PictureNotFoundException;
import com.example.pictures_annotator.domain.exception.PictureNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PictureNotValidException.class)
    public ResponseEntity<Map<String, String>> handlePictureNotValid(PictureNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("message", ex.getMessage()));
    }


    @ExceptionHandler(PictureNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePictureNotFound(PictureNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Collections.singletonMap("message", ex.getMessage()));
    }

    @ExceptionHandler(BoundingBoxNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBoundingBoxNotFound(BoundingBoxNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("message", ex.getMessage()));
    }

    @ExceptionHandler(BoundingBoxOutOfImageException.class)
    public ResponseEntity<Map<String, String>> handleBoundingBoxOutOfImage(BoundingBoxOutOfImageException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("message", ex.getMessage()));
    }
}
