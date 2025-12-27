package com.example.pictures_annotator.application.boundingBox.commands.create;

public record CreateBoundingBoxCommand(
        Integer pictureId,
        int x,
        int y,
        int width,
        int height,
        String label
) {}