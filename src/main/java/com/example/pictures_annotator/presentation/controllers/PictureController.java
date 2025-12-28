package com.example.pictures_annotator.presentation.controllers;

import com.example.pictures_annotator.application.picture.commands.create.CreatePictureCommand;
import com.example.pictures_annotator.application.picture.commands.create.CreatePictureHandler;
import com.example.pictures_annotator.application.picture.queries.getAll.GetAllPicturesQuery;
import com.example.pictures_annotator.application.picture.queries.getAll.GetAllPicturesHandler;
import com.example.pictures_annotator.application.picture.queries.getById.GetPictureByIdHandler;
import com.example.pictures_annotator.application.picture.queries.getById.GetPictureByIdQuery;
import com.example.pictures_annotator.application.picture.queries.getById.GetPictureByIdResponse;
import com.example.pictures_annotator.domain.models.Picture;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pictures")
public class PictureController {

    private final CreatePictureHandler createPictureHandler;
    private final GetAllPicturesHandler getAllPicturesHandler;
    private final GetPictureByIdHandler getPictureByIdHandler;


    public PictureController(GetAllPicturesHandler getAllPicturesQuery, CreatePictureHandler createPictureHandler, GetPictureByIdHandler getPictureByIdHandler) {
        this.getAllPicturesHandler = getAllPicturesQuery;
        this.createPictureHandler = createPictureHandler;
        this.getPictureByIdHandler = getPictureByIdHandler;
    }

    @Operation(
            summary = "Dodaje nowy obraz",
            operationId = "createPicture"
    )
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreatePictureCommand command) {
        createPictureHandler.handle(command);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Zwraca wszystkie obrazy",
            operationId = "getAllPictures"
    )
    @GetMapping
    public ResponseEntity<List<Picture>> getAll() {
        GetAllPicturesQuery query = new GetAllPicturesQuery();
        List<Picture> pictures = getAllPicturesHandler.handle(query);

        return new ResponseEntity<>(pictures, HttpStatus.OK);
    }

    @Operation(
            summary = "Zwraca obraz o podanym id",
            operationId = "getPictureById"
    )
    @GetMapping("/{id}")
    public ResponseEntity<GetPictureByIdResponse> getById(@PathVariable Integer id) {
        GetPictureByIdQuery query = new GetPictureByIdQuery(id);
        GetPictureByIdResponse picture = getPictureByIdHandler.handle(query);

        return new ResponseEntity<>(picture, HttpStatus.OK);
    }
}
