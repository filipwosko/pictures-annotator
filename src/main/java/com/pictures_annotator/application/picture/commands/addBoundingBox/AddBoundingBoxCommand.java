package com.pictures_annotator.application.picture.commands.addBoundingBox;

public record AddBoundingBoxCommand(
        Integer pictureId,
        int x,
        int y,
        int width,
        int height,
        String label
) {}