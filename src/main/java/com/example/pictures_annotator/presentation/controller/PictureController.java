package com.example.pictures_annotator.presentation.controller;

import com.example.pictures_annotator.domain.model.Picture;
import com.example.pictures_annotator.aplication.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pictures")
public class PictureController {

    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Operation(
            summary = "Dodaje nowy obraz",
            operationId = "createPicture"
    )
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Picture picture) {
        pictureService.createPicture(picture);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Zwraca wszystkie obrazy",
            operationId = "getAllPictures"
    )
    @GetMapping
    public ResponseEntity<List<Picture>> getAll() {
        List<Picture> body = pictureService.listPictures();

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Operation(
            summary = "Zwraca obraz o podanym id",
            operationId = "getPictureById"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Picture> getById(@PathVariable Integer id) {
        Picture picture = pictureService.getPictureById(id);

        return new ResponseEntity<>(picture, HttpStatus.OK);
    }
}
