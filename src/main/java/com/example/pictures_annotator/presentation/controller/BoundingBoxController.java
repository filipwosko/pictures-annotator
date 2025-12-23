package com.example.pictures_annotator.presentation.controller;

import com.example.pictures_annotator.domain.model.BoundingBox;
import com.example.pictures_annotator.aplication.service.BoundingBoxService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bounding-boxes")
public class BoundingBoxController {

    private final BoundingBoxService boundingBoxService;

    public BoundingBoxController(BoundingBoxService BoundingBoxService) {
        this.boundingBoxService = BoundingBoxService;
    }

    @Operation(
            summary = "Dodaje nowy boks",
            operationId = "createBoundingBox"
    )
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody BoundingBox BoundingBox) {
        boundingBoxService.createBoundingBox(BoundingBox);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Modyfikuje istniejący boks",
            operationId = "modifyBoundingBox"
    )
    @PutMapping
    public ResponseEntity<Void> modify(@RequestBody BoundingBox BoundingBox) {
        boundingBoxService.modifyBoundingBox(BoundingBox);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Usuwa boks o podanym id",
            operationId = "deleteBoundingBoxById"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boundingBoxService.deleteBoundingBox(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
