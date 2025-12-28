package com.pictures_annotator.application.picture.queries.dto;

import java.util.List;

public record PictureDto(
        Integer id,
        byte[] data,
        int width,
        int height,
        List<BoundingBoxDto> boundingBoxes
) {}
