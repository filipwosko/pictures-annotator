package com.pictures_annotator.application.picture.commands.deleteBoundingBox;

public record DeleteBoundingBoxCommand(
   Integer pictureId,
   Integer boundingBoxId
) {}
