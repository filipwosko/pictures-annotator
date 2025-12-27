package com.example.pictures_annotator.presentation.controller;

import com.example.pictures_annotator.application.boundingBox.commands.create.CreateBoundingBoxCommand;
import com.example.pictures_annotator.application.boundingBox.commands.create.CreateBoundingBoxHandler;
import com.example.pictures_annotator.application.boundingBox.commands.delete.DeleteBoundingBoxCommand;
import com.example.pictures_annotator.application.boundingBox.commands.delete.DeleteBoundingBoxHandler;
import com.example.pictures_annotator.application.boundingBox.commands.modify.ModifyBoundingBoxCommand;
import com.example.pictures_annotator.application.boundingBox.commands.modify.ModifyBoundingBoxHandler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bounding-boxes")
public class BoundingBoxController {

    private final CreateBoundingBoxHandler createBoundingBoxHandler;
    private final ModifyBoundingBoxHandler modifyBoundingBoxHandler;
    private final DeleteBoundingBoxHandler deleteBoundingBoxHandler;

    public BoundingBoxController(CreateBoundingBoxHandler createBoundingBoxHandler, ModifyBoundingBoxHandler modifyBoundingBoxHandler, DeleteBoundingBoxHandler deleteBoundingBoxHandler) {
        this.createBoundingBoxHandler = createBoundingBoxHandler;
        this.modifyBoundingBoxHandler = modifyBoundingBoxHandler;
        this.deleteBoundingBoxHandler = deleteBoundingBoxHandler;
    }

    @Operation(
            summary = "Dodaje nowy boks",
            operationId = "createBoundingBox"
    )
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateBoundingBoxCommand command) {
        createBoundingBoxHandler.handle(command);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Modyfikuje istniejący boks",
            operationId = "modifyBoundingBox"
    )
    @PutMapping
    public ResponseEntity<Void> modify(@RequestBody ModifyBoundingBoxCommand command) {
        modifyBoundingBoxHandler.handle(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Usuwa boks o podanym id",
            operationId = "deleteBoundingBoxById"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        DeleteBoundingBoxCommand command = new DeleteBoundingBoxCommand(id);
        deleteBoundingBoxHandler.handle(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}