package com.example.pictures_annotator.aplication.boundingBox.commands.modify;

public record ModifyBoundingBoxCommand(
    Integer id,
    Integer pictureId,
    int x,
    int y,
    int width,
    int height,
    String label
) {}
