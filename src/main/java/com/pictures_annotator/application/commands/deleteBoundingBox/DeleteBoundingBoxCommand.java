package com.pictures_annotator.application.commands.deleteBoundingBox;

public record DeleteBoundingBoxCommand(
   Integer pictureId,
   Integer boundingBoxId
) {}
