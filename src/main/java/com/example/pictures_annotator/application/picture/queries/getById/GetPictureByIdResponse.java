package com.example.pictures_annotator.application.picture.queries.getById;

import com.example.pictures_annotator.domain.models.BoundingBox;

import java.util.List;

public record GetPictureByIdResponse(
        Integer id,
        byte[] data,
        int width,
        int height,
        List<BoundingBox> boundingBoxes
) {};
