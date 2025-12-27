package com.example.pictures_annotator.aplication.boundingBox.commands.create;

public record CreateBoundingBoxCommand(
        Integer pictureId,
        int x,
        int y,
        int width,
        int height,
        String label
) {}