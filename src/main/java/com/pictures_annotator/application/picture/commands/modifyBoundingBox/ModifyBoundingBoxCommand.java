package com.pictures_annotator.application.picture.commands.modifyBoundingBox;

public record ModifyBoundingBoxCommand(
    Integer id,
    Integer pictureId,
    int x,
    int y,
    int width,
    int height,
    String label
) {}
