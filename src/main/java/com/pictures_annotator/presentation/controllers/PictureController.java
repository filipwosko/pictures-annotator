package com.pictures_annotator.presentation.controllers;

import com.pictures_annotator.application.picture.commands.addBoundingBox.AddBoundingBoxCommand;
import com.pictures_annotator.application.picture.commands.addBoundingBox.AddBoundingBoxCommandHandler;
import com.pictures_annotator.application.picture.commands.create.CreateCommand;
import com.pictures_annotator.application.picture.commands.create.CreateCommandHandler;
import com.pictures_annotator.application.picture.commands.deleteBoundingBox.DeleteBoundingBoxCommand;
import com.pictures_annotator.application.picture.commands.deleteBoundingBox.DeleteBoundingBoxCommandHandler;
import com.pictures_annotator.application.picture.commands.modifyBoundingBox.ModifyBoundingBoxCommand;
import com.pictures_annotator.application.picture.commands.modifyBoundingBox.ModifyBoundingBoxCommandHandler;
import com.pictures_annotator.application.picture.queries.getAll.GetAllQuery;
import com.pictures_annotator.application.picture.queries.getAll.GetAllQueryHandler;
import com.pictures_annotator.application.picture.queries.getById.GetByIdQueryHandler;
import com.pictures_annotator.application.picture.queries.getById.GetByIdQuery;
import com.pictures_annotator.application.picture.queries.dto.PictureDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pictures")
public class PictureController {

    private final CreateCommandHandler createPictureHandler;
    private final GetAllQueryHandler getAllPicturesHandler;
    private final GetByIdQueryHandler getPictureByIdHandler;

    private final AddBoundingBoxCommandHandler addBoundingBoxCommandHandler;
    private final ModifyBoundingBoxCommandHandler modifyBoundingBoxCommandHandler;
    private final DeleteBoundingBoxCommandHandler deleteBoundingBoxCommandHandler;

    public PictureController(
            CreateCommandHandler createPictureHandler,
            GetAllQueryHandler getAllPicturesHandler,
            GetByIdQueryHandler getPictureByIdHandler,
            AddBoundingBoxCommandHandler addBoundingBoxCommandHandler,
            ModifyBoundingBoxCommandHandler modifyBoundingBoxCommandHandler,
            DeleteBoundingBoxCommandHandler deleteBoundingBoxCommandHandler
    ) {
        this.createPictureHandler = createPictureHandler;
        this.getAllPicturesHandler = getAllPicturesHandler;
        this.getPictureByIdHandler = getPictureByIdHandler;
        this.addBoundingBoxCommandHandler = addBoundingBoxCommandHandler;
        this.modifyBoundingBoxCommandHandler = modifyBoundingBoxCommandHandler;
        this.deleteBoundingBoxCommandHandler = deleteBoundingBoxCommandHandler;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateCommand command) {
        createPictureHandler.handle(command);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PictureDto>> getAll() {
        List<PictureDto> pictures = getAllPicturesHandler.handle(new GetAllQuery());
        return new ResponseEntity<>(pictures, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PictureDto> getById(@PathVariable Integer id) {
        PictureDto picture = getPictureByIdHandler.handle(new GetByIdQuery(id));
        return new ResponseEntity<>(picture, HttpStatus.OK);
    }

    @PostMapping("/bounding-boxes")
    public ResponseEntity<Void> addBoundingBox(@RequestBody AddBoundingBoxCommand command) {
        addBoundingBoxCommandHandler.handle(command);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/bounding-boxes")
    public ResponseEntity<Void> modifyBoundingBox(@RequestBody ModifyBoundingBoxCommand command) {
        modifyBoundingBoxCommandHandler.handle(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{pictureId}/bounding-boxes/{boundingBoxId}")
    public ResponseEntity<Void> deleteBoundingBox(
            @PathVariable Integer pictureId,
            @PathVariable Integer boundingBoxId) {
        DeleteBoundingBoxCommand command = new DeleteBoundingBoxCommand(pictureId, boundingBoxId);
        deleteBoundingBoxCommandHandler.handle(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

